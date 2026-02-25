package bookstoremgmt.service.impl;

import bookstoremgmt.model.Book;
import bookstoremgmt.model.Customer;
import bookstoremgmt.model.Order;
import bookstoremgmt.model.OrderDetail;
import bookstoremgmt.model.Order.OrderStatus;
import bookstoremgmt.repository.OrderRepository;
import bookstoremgmt.repository.OrderDetailRepository;
import bookstoremgmt.service.IBookService;
import bookstoremgmt.service.IOrderService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 *
 * @author FA25_PRO192_SE2002_Group4
 */

public class OrderServiceImpl implements IOrderService {

    // Cac thanh phan phu thuoc
    private final OrderRepository orderRepository;
    private final OrderDetailRepository orderDetailRepository;
    private final BookServiceImpl bookService;
    private CustomerServiceImpl customerService;

    // Bo nho dem du lieu
    private Map<String, Order> ordersMap;
    private boolean isDataLoaded = false;

    public OrderServiceImpl() {
        this.orderRepository = new OrderRepository();
        this.orderDetailRepository = new OrderDetailRepository();
        this.bookService = new BookServiceImpl();
        this.ordersMap = new HashMap<>();

    }

    private CustomerServiceImpl getCustomerService() {
        if (customerService == null) {
            customerService = new CustomerServiceImpl();
        }
        return customerService;
    }

    private void ensureDataLoaded() {
        if (!isDataLoaded) {
            loadData();
        }
    }

    private void loadData() {
        if (isDataLoaded) return;
        this.ordersMap = orderRepository.loadOrders();

        Map<String, List<OrderDetail>> orderDetaisMap = orderDetailRepository.loadOrderDetails();

        // Hydrate: Ket hop
        for (Order order : this.ordersMap.values()) {

            // Skip hydrating customer history to avoid CustomerService -> OrderService loop
            List<OrderDetail> details = orderDetaisMap.get(order.getOrderId());
            if (details != null) {
                for (OrderDetail od : details) {
                    // Lay dummy book (chi co ID)
                    String bookId = od.getBook().getId();
                    // Lay Book that tu BookService
                    Book realBook = bookService.getById(bookId);

                    if (realBook != null) {
                        // Gan Book that vao OrderDetail (that the dummy book)
                        od.setBook(realBook);
                        // Them OrderDetail (da duoc hydrate) vao Order
                        order.addOrderDetail(od);
                    } else {
                        System.err.println(
                                "Warning: Book not found with ID: " + bookId + " for Order: " + order.getOrderId());
                    }
                }
            }
        }
        isDataLoaded = true;
        System.out.println("OrderService: Load data successfully");
    }

    private void saveData() {
        // 1. Luu thong tin Order chinh (Repo tu dong lay customerId)
        orderRepository.saveOrders(this.ordersMap);

        // 2. Du lieu chuan bi cho OrderDetail
        // Tach List<OrderDetail> tu moi Order ra thanh mot Map rieng
        Map<String, List<OrderDetail>> allOrderDetailsMap = new HashMap<>();
        for (Order order : this.ordersMap.values()) {
            allOrderDetailsMap.put(order.getOrderId(), order.getOrderDetails());
        }

        // 3. Luu thong tin OrderDetail (Repo tu dong lay BookId tu OrderDetail)
        orderDetailRepository.saveOrderDetails(allOrderDetailsMap);
    }

    // Ham tao ho tro - Tao id ngau nhien cho order
    private String generateNewId() {
        return "ORD-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase();
    }

    // Tao don hang moi, bao gom kiem tra ton kho va tru kho.
    @Override
    public Order createOrder(String customerId, List<OrderDetail> orderDetails) throws Exception {
        ensureDataLoaded();
        // 1. Kiem tra Customer
        // Goi ham getById de lay Customer that
        Customer customer = getCustomerService().getById(customerId); // lazy access
        if (customer == null) {
            throw new Exception("Customer do not exist.");
        }
        if (orderDetails == null) {
            throw new Exception("Order do not have any order details.");
        }

        // 2. Kiem tra ton kho
        // Dam bao tat ca san pham deu du hang truoc khi kho bat ky san pham nao
        for (OrderDetail orderDetail : orderDetails) {
            Book book = bookService.getById(orderDetail.getBook().getId());
            if (book == null) {
                throw new Exception("Book " + orderDetail.getBook().getId() + " do not exist.");
            }
            if (book.getStockQuantity() < orderDetail.getQuantity()) {
                throw new Exception("Not enough stock for book: " + book.getTitle() +
                        ". Remaining: " + book.getStockQuantity());
            }
        }

        // 3. Tat ta deu hop le -> Tao don hang
        Order order = new Order();
        order.setOrderId(generateNewId());
        order.setCustomerId(customerId);
        order.setStatus(Order.OrderStatus.CONFIRMED);

        // 4. Tru kho va them chi tiet don hang
        for (OrderDetail orderDetail : orderDetails) {
            Book realBook = bookService.getById(orderDetail.getBook().getId());
            orderDetail.setBook(realBook);
            order.addOrderDetail(orderDetail);

            bookService.decreaseStock(realBook.getId(), orderDetail.getQuantity());
        }

        // 5. Them vao lich su cua Customer
        customer.addOrder(order);

        // 6. Luu vao cache va file
        this.ordersMap.put(order.getOrderId(), order);
        saveData();

        // Goi ham saveBooks de cap nhat lai file sach
        bookService.saveBooks();

        return order;
    }

    // Tim don hang theo orderId
    @Override
    public Order getOrderById(String orderId) {
        ensureDataLoaded();
        return this.ordersMap.get(orderId);
    }

    // Dua ra tat ca don hang
    @Override
    public List<Order> getAllOrders() {
        ensureDataLoaded();
        return new ArrayList<>(this.ordersMap.values());
    }

    // Loc cac don hang theo customerId
    @Override
    public List<Order> getOrdersByCustomerId(String customerId) {
        ensureDataLoaded();
        // Su dung Stream API de loc - Gom nhac thong tin
        return this.ordersMap.values().stream()
                .filter(order -> Objects.equals(order.getCustomerId(), customerId))
                .collect(Collectors.toList());
    }

    // Loc cac don hang theo trang thai PENDING
    @Override
    public List<Order> getPendingOrders() {
        ensureDataLoaded();
        return this.ordersMap.values().stream().filter(order -> order.getStatus() == Order.OrderStatus.PENDING)
                .collect(Collectors.toList());
    }

    // NHIEM VU QUAN TRONG: CAP NHAT TRANG THAI
    @Override
    public void updateOrderStatus(String orderId, OrderStatus newStatus) throws Exception {
        ensureDataLoaded();
        Order order = getOrderById(orderId);
        if (order == null) {
            throw new Exception("Order not found");
        }

        Order.OrderStatus oldStatus = order.getStatus();
        if (oldStatus == newStatus) {
            return;
        }

        // NHIEM VU 1: HUY DON HANG (HOAN KHO) - Validate all, then apply, then save once
        if (newStatus == Order.OrderStatus.CANCELLED && oldStatus != Order.OrderStatus.CANCELLED) {
            // 1) Validate
            List<Exception> errors = new ArrayList<>();
            for (OrderDetail od : order.getOrderDetails()) {
                Book b = bookService.getById(od.getBook().getId());
                if (b == null) {
                    errors.add(new Exception("Book not found: " + od.getBook().getId()));
                }
            }
            if (!errors.isEmpty()) {
                throw new Exception("Cannot restore stock: " + errors);
            }

            // 2) Apply all operations
            for (OrderDetail od : order.getOrderDetails()) {
                bookService.increaseStock(od.getBook().getId(), od.getQuantity());
            }

            // 3) Save once
            bookService.saveBooks();
        }

        // NHIEM VU 2: KHOI PHUC DON HANG DA HUY (KIEM TRA KHO & TRU KHO LAI)
        if (oldStatus == Order.OrderStatus.CANCELLED && newStatus != Order.OrderStatus.CANCELLED) {
            for (OrderDetail orderDetail : order.getOrderDetails()) {
                Book book = bookService.getById(orderDetail.getBook().getId());
                if (book.getStockQuantity() < orderDetail.getQuantity()) {
                    throw new Exception("Not enough stock to restore order for book: "
                            + book.getTitle() + ". Remaining: " + book.getStockQuantity());
                }
            }

            for (OrderDetail orderDetail : order.getOrderDetails()) {
                bookService.decreaseStock(orderDetail.getBook().getId(), orderDetail.getQuantity());
            }
            // Goi ham saveBooks de cap nhat lai file sach
            bookService.saveBooks();
        }

        order.setStatus(newStatus);
        saveData();
    }

    // NHIEM VU: SUA GIO HANG DANG PENDING
    // 1. Them mot sach vao don hang cho (PENDING)
    @Override
    public Order addBookToOrder(String orderId, String bookId, int quantity) throws Exception {
        ensureDataLoaded();
        Order order = getOrderById(orderId);
        if (order == null) {
            throw new Exception("Order not found");
        }
        if (order.getStatus() != Order.OrderStatus.PENDING) {
            throw new Exception("Only PENDING orders can be modified.");
        }

        Book book = bookService.getById(bookId);
        if (book == null) {
            throw new Exception("Book not found");
        }
        if (book.getStockQuantity() < quantity) {
            throw new Exception("Not enough stock");
        }

        OrderDetail existingOrderDetail = order.getOrderDetails().stream()
                .filter(d -> d.getBook().getId().equals(bookId)).findFirst().orElse(null);

        if (existingOrderDetail != null) {
            return updateBookQuantityInOrder(orderId, bookId, existingOrderDetail.getQuantity() + quantity);
        } else {
            bookService.decreaseStock(bookId, quantity);
            OrderDetail newDetail = new OrderDetail(book, quantity);
            order.addOrderDetail(newDetail);
        }

        saveData();

        // Goi ham saveBooks de cap nhat lai file sach
        bookService.saveBooks();
        return order;
    }

    // NHIEM VU: SUA GIO HANG DANG PENDING
    // 2. Xoa mot sach khoi don hang PENDING (phai hoan kho)
    @Override
    public Order removeBookFromOrder(String orderId, String bookId) throws Exception {
        ensureDataLoaded();
        Order order = getOrderById(orderId);
        if (order == null) {
            throw new Exception("Order not found");
        }
        if (order.getStatus() != Order.OrderStatus.PENDING) {
            throw new Exception("Only PENDING orders can be modified.");
        }

        OrderDetail orderDetailToRemove = order.getOrderDetails().stream()
                .filter(d -> d.getBook().getId().equals(bookId)).findFirst().orElse(null);

        if (orderDetailToRemove != null) {
            bookService.increaseStock(bookId, orderDetailToRemove.getQuantity());
            order.removeOrderDetail(orderDetailToRemove);

            saveData();
            // Goi ham saveBooks de cap nhat lai file sach
        bookService.saveBooks();
        } else {
            throw new Exception("Book not found in this order");
        }
        return order;
    }

    // NHIEM VU: SUA GIO HANG DANG PENDING
    // 3. Cap nhat so luong cua mot sach (xu ly kho)
    @Override
    public Order updateBookQuantityInOrder(String orderId, String bookId, int newQuantity) throws Exception {
        ensureDataLoaded();
        Order order = getOrderById(orderId);
        if (order == null) {
            throw new Exception("Order not found");
        }
        if (order.getStatus() != Order.OrderStatus.PENDING) {
            throw new Exception("Only PENDING orders can be modified.");
        }

        if (newQuantity <= 0) {
            return removeBookFromOrder(orderId, bookId);
        }

        OrderDetail orderDetailToUpdate = order.getOrderDetails().stream()
                .filter(d -> d.getBook().getId().equals(bookId)).findFirst().orElse(null);

        if (orderDetailToUpdate == null) {
            throw new Exception("Book not found in this order. Use addBookToOrder instead.");
        }

        int oldQuantity = orderDetailToUpdate.getQuantity();
        int diff = newQuantity - oldQuantity;

        if (diff > 0) {
            Book book = bookService.getById(bookId);
            if (book.getStockQuantity() < diff) {
                throw new Exception("Not enough stock to increase quantity");
            }
            bookService.decreaseStock(bookId, diff);
        } else if (diff < 0) {
            bookService.increaseStock(bookId, -diff);
        }

        orderDetailToUpdate.setQuantity(newQuantity);
        saveData();
        // Goi ham saveBooks de cap nhat lai file sach
        bookService.saveBooks();
        return order;
    }
}