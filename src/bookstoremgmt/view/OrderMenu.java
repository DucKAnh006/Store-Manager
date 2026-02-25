package bookstoremgmt.view;

import bookstoremgmt.model.Book;
import bookstoremgmt.model.Customer;
import bookstoremgmt.model.Order;
import bookstoremgmt.model.OrderDetail;
import bookstoremgmt.service.impl.BookServiceImpl;
import bookstoremgmt.service.impl.OrderServiceImpl;
import bookstoremgmt.service.impl.CustomerServiceImpl;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Nguyen Tran Duc Anh
 */
public class OrderMenu extends BaseMenu {

    // Cac thanh phan phu thuoc
    private final OrderServiceImpl orderService;
    private final BookServiceImpl bookService;
    private final CustomerServiceImpl customerService;

    // No-arg constructor
    public OrderMenu() {
        this.bookService = new BookServiceImpl();
        this.customerService = new CustomerServiceImpl();
        this.orderService = new OrderServiceImpl(); // tu khoi tao phu thuoc ben trong
    }

    @Override
    public void display() {
        int choice;
        do {
            clearScreen();
            printLine();
            printMenuHeader();
            printMenuOptions();
            printSeparator();

            int maxChoice = getMenuOptions().length;
            choice = getIntInput("Enter your choice number: ", 0, maxChoice);

            if (choice != 0) {
                handleMenuChoice(choice);
                pauseScreen();
            }
        } while (choice != 0);
    }

    @Override
    protected String getMenuTitle() {
        return "Order Menu";
    }

    @Override
    protected String[] getMenuOptions() {
        return new String[] {
                "Create new order",
                "Update orders",
                "Search order details by orderId",
                "Search orders by customerId",
                "Show all orders"
        };
    }

    @Override
    protected void handleMenuChoice(int choice) {
        switch (choice) {
            case 1:
                doCreateNewOrder();
                break;
            case 2:
                doUpdateorderStatus();
                break;
            case 3:
                doSearchOrderDetailsByOrderId();
                break;
            case 4:
                doSearchOrdersByCustomerId();
                break;
            case 5:
                doShowAllOrder();
                break;
        }
    }

    // CAC HAM NHIEM VU
    // NHIEM VU 1: TAO DON HANG
    private void doCreateNewOrder() {
        clearScreen();
        printSeparator();
        System.out.println("=== Create New Order ===");
        printSeparator();

        //Buoc 1: CHON KHACH HANG
        String customerId = getStringInput("Enter the customerId: ");
        Customer customer = customerService.getById(customerId);
        if(customer == null) {
            showError("Customer not found by customerId: " + customerId);
            return;
        }
        showInfo("Chosen customer: " + customer.getName());
        printLine();

        //Buoc 2: TAO GIO HANG (Cart)
        List<OrderDetail> cart = new ArrayList<>();
        boolean addMoreBooks = false;

        do {
            String bookId = getStringInput("Enter the bookId (or 'done' to finish): ");
            if(bookId.equalsIgnoreCase("done")) {
                break;
            }

            Book book = bookService.getById(bookId);
            if(book == null) {
                showError("The book not found by bookId: " + bookId);
                continue;
            }

            if(book.getStockQuantity() == 0) {
                showError("The book '" + book.getTitle() + "' is out of stock.");
                continue;
            }

            // Kiem tra sach da co trong gio chua
            OrderDetail existingDetail = cart.stream()
                .filter(od -> od.getBook().getId().equals(bookId))
                .findFirst()
                .orElse(null);

            if(existingDetail != null) {
                showInfo("The book '" + book.getTitle() + "' is already in the cart with quantity: " + existingDetail.getQuantity());
                if(getConfirmation("Do you want to update the quantity?")) {
                    int newQuantity = getIntInput("Enter new quantity (Max " + book.getStockQuantity() + "): ", 1, book.getStockQuantity());
                    existingDetail.setQuantity(newQuantity);
                    showSuccess("Updated quantity to " + newQuantity + " for '" + book.getTitle() + "'.");
                }
                printLine();
                addMoreBooks = getConfirmation("Add more another books?");
                continue;
            }

    
            //Cap nhat so luong cua sach moi
            showInfo("Title: '" + book.getTitle() + "' | Price: " + book.getPrice() + " | Stock: " + book.getStockQuantity());

            int quantity = getIntInput("Enter quantity (Max " + book.getStockQuantity() + "): ", 1, book.getStockQuantity());

            OrderDetail orderDetail = new OrderDetail(book, quantity);
            cart.add(orderDetail);
            showSuccess("Added " + quantity + " copies of '" + book.getTitle() + "' to the cart.");
            printLine();

            addMoreBooks = getConfirmation("Add more another books?");
        } while (addMoreBooks);

        //Buoc 3: XAC NHAN DON HANG
        if(cart.isEmpty()) {
            showInfo("The cart is empty. Order creation has been canceled.");
            return;
        }

        //Hien thi chi tiet gio hang
        printCartSummary(cart);


        //Buoc 4: GOI SERVICE
        if(getConfirmation("Confirm creating this order?")) {
            try {
                Order newOrder = orderService.createOrder(customerId, cart);
                showSuccess("Order creation successfully!");
                showInfo("Order ID: " + newOrder.getOrderId());
                showInfo("Total price: " + String.format("%.2f", newOrder.getTotalAmount()));
            } catch (Exception e) {
                //Hien thi loi tu Service
                showError("Order creation failed: " + e.getMessage());
            }
        } else {
            showInfo("Order has been canceled.");
        }
    }

    // Nhiem vu 2: XEM TAT CA DON HANG
    private void doShowAllOrder() {
        List<Order> orders = orderService.getAllOrders();
        if (orders.isEmpty()) {
            showInfo("There are no orders in the system.");
            return;
        }

        printSeparator();
        System.out.println("=== All Orders ===");
        printSeparator();
        for (Order order : orders) {
            Customer customer = customerService.getById(order.getCustomerId());
            // Doan nay co the bo
            String customerName = (customer != null) ? customer.getName() : "Unknown customer";
            System.out.printf("ID: %-7s | Date: %-12s | Customer: %-15s | Total: %-10.2f | Status: %s%n",
                    order.getOrderId(), order.getOrderDate(), customerName, order.getTotalAmount(), order.getStatus());
        }
    }

    // Nhiem vu 3: XEM CHI TIET DON HANG CUA MOT DON HANG
    private void doSearchOrderDetailsByOrderId() {
        String orderId = getStringInput("Enter orderId to search order details: ");
        Order order = orderService.getOrderById(orderId);

        if (order == null) {
            showError("Order not found with ID: " + orderId);
            return;
        }

        // Ham nay toi viet o phia duoi, vi toi muon hien them khach hang nao da mua
        printOrderDetails(order);
    }

    // Nhiem vu 4: TIM DON HANG THEO KHACH HANG
    private void doSearchOrdersByCustomerId() {
        String customerId = getStringInput("Enter customerId to search orders: ");
        Customer customer = customerService.getById(customerId);
        if (customer == null) {
            showError("Customer not found with ID: " + customerId);
            return;
        }

        List<Order> orders = orderService.getOrdersByCustomerId(customerId);
        if (orders.isEmpty()) {
            showInfo("Customer: '" + customer.getName() + "' do not have any orders.");
            return;
        }

        printSeparator();
        System.out.println("List of orders of customer: " + customer.getName().toUpperCase());
        printSeparator();
        for (Order order : orders) {
            System.out.printf("ID: %-7s | Date: %-12s | Total: %-10.2f | Status: %s%n",
                    order.getOrderId(), order.getOrderDate(), order.getTotalAmount(), order.getStatus());
        }
    }

    // Nhiem vu 5: CAP NHAT TRANG THAI
    private void doUpdateorderStatus() {
        String orderId = getStringInput("Enter orderId to update status: ");
        Order order = orderService.getOrderById(orderId);

        if(order == null) {
            showError("Order not found with ID: " + orderId);
            return;
        }

        showInfo("Order: " + order.getOrderId());
        showInfo("Current status: " + order.getStatus());

        if(order.getStatus() == Order.OrderStatus.CANCELLED) {
            showInfo("Order has been cancelled.");
            return;
        }

        if(getConfirmation("Are you sure to CANCEL order?")) {
            try {
                orderService.updateOrderStatus(orderId, Order.OrderStatus.CANCELLED);
                showSuccess("Order has been successfully cancelled. Stock quantity has been restored.");
            } catch (Exception e) {
                showError("Failed to cancel order: " + e.getMessage());
            }
        } else {
            showInfo("Order cancellation has been aborted.");
        }
    }

    // CAC HAM HO TRO
    // 1. Ham in chi tiet don hang
    private void printOrderDetails(Order order) {
        Customer customer = customerService.getById(order.getCustomerId());
        // Doan nay co the bo
        String customerName = (customer != null) ? customer.getName() : "Unknown customer";

        printSeparator();
        System.out.println("=== Order Details ===");
        System.out.println("Order ID: " + order.getOrderId());
        System.out.println("Date: " + order.getOrderDate());
        System.out.println("Status: " + order.getStatus());
        System.out.println("Customer: " + customerName + " (ID: " + order.getCustomerId() + ")");
        printLine();
        System.out.println("Items:");

        for (OrderDetail orderDetail : order.getOrderDetails()) {
            Book book = orderDetail.getBook();
            System.out.printf("Book: %-20s (ID: %-8s) | Quantity: %-3d | Price: %-6.2f | Total: %-8.2f%n",
                    book.getTitle(), book.getId(), orderDetail.getQuantity(), book.getPrice(),
                    orderDetail.getTotalPrice());
        }
        printLine();
        System.out.printf("Grand Total: %.2f%n", order.getTotalAmount());
        printSeparator();
    }

    // 2. Ham in tom tat gio hang (truoc khi xac nhan)
    private void printCartSummary(List<OrderDetail> cart) {
        printLine();
        System.out.println("=== Cart Summary ===");
        double total = 0;
        for (OrderDetail orderDetail : cart) {
            System.out.printf("Book: %-20s | Quantity: %-3d | Price: %-6.2f | Total: %-8.2f%n",
                    orderDetail.getBook().getTitle(), orderDetail.getQuantity(), orderDetail.getBook().getPrice(),
                    orderDetail.getTotalPrice());
            total += orderDetail.getTotalPrice();
        }
        printLine();
        System.out.printf("Subtotal: %.2f%n", total);
    }
}
