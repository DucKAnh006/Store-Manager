package bookstoremgmt.service.impl;

import bookstoremgmt.model.Customer;
import java.util.List;
import java.util.ArrayList;
import java.util.Collections;
import bookstoremgmt.repository.CustomerRepository;
import bookstoremgmt.service.IService;
import bookstoremgmt.model.Order;

/**
 *
 * @author Nguyen Tran Duc Anh
 */

public class CustomerServiceImpl implements IService<Customer> {
    private OrderServiceImpl orderService;
    private boolean orderHistoryHydrated = false; // danh dau da nap lich su don hang chua
    private final CustomerRepository repo = new CustomerRepository(); // de goi CustomerRepository gon hon
                                                                      // repo.readCustomers thay vi
                                                                      // CustomerRepository.readCustomers
    List<Customer> customers = Collections.synchronizedList(new ArrayList<>(repo.readCustomers())); // nap danh sach
                                                                                                    // customers tu file

    private OrderServiceImpl getOrderService() { // tra ve instance OrderServiceImpl duoc tao 1 lan
        if (orderService == null) {
            orderService = new OrderServiceImpl(); // tao duy nhat 1 lan
        }
        return orderService;
    }

    private void hydrateOrderHistory() { // nap lich su don hang cho tung customer (chi thuc hien 1 lan)
        if (orderHistoryHydrated)
            return;
        OrderServiceImpl os = getOrderService();
        for (Customer c : customers) {
            List<Order> orders = os.getOrdersByCustomerId(c.getId()); // lay tat ca order theo id customer
            for (Order order : orders) {
                if (order != null) {
                    // tranh them trung don hang bang cach kiem tra orderId
                    boolean exists = c.getOrderHistory().stream()
                            .anyMatch(existing -> existing.getOrderId().equals(order.getOrderId()));
                    if (!exists)
                        c.addOrder(order); // them vao lich su neu chua ton tai
                }
            }
        }
        orderHistoryHydrated = true; // danh dau da nap
    }

    @Override
    public boolean add(Customer customer) {
        try {
            if (exists(customer.getId())) {
                System.err.println("Customer ID already exists: " + customer.getId());
                return false;
            } // kiem tra trung ID
            customers.add(customer); // them customer moi vao danh sach
            repo.saveCustomers(customers); // luu lai danh sach customers vao file
            return true;

        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("Add customer not success");
            return false;
        }

    }

    @Override
    public List<Customer> getAll() {
        hydrateOrderHistory(); // ensure filled before returning
        return customers;

    }

    @Override
    public Customer getById(String id) {
        hydrateOrderHistory();
        List<Customer> fullCustomers = getAll(); // trả về danh sách customers đầy đủ (có lịch sử đơn hàng)
        for (Customer customer : fullCustomers) {
            if (customer.getId().equals(id)) {
                return customer;
                // kiểm tra từng phần tử trong danh sách và trả về customer có id trùng với id
                // cần tìm
            }
        }
        return null; // nếu không tìm thấy customer có id trùng, trả về null
    }

    @Override //
    public boolean update(Customer newCustomer) {
        try {
            for (int i = 0; i < customers.size(); i++) {
                if (customers.get(i).getId().equals(newCustomer.getId())) {
                    customers.set(i, newCustomer);
                    // khi tim thay customer co id trung, thay the customer do bang customer moi
                    repo.saveCustomers(customers);
                    // luu lai danh sach customers vao file sau do tra ve true ket thuc ham
                    return true;
                }
            }
            return false;
            // neu khong tim thay customer co id trung, tra ve false
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("Update not success");
            return false;
        }
    }

    @Override
    public boolean delete(String id) {
        try {
            // nap danh sach customers tu file
            for (int i = 0; i < customers.size(); i++) {
                if (customers.get(i).getId().equals(id)) {
                    // khi tim thay customer co id trung, xoa customer do khoi danh sach
                    customers.remove(i);
                    repo.saveCustomers(customers);
                    // luu lai danh sach customers vao file sau do tra ve true ket thuc ham
                    return true;
                }
            }
            return false;
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("Delete not success");
            return false;
        }
    }

    @Override
    public boolean exists(String id) {
        for (Customer customer : customers) {
            if (customer.getId().equals(id)) {
                // kiem tra tung phan tu trong danh sach va tra ve true neu tim thay id trung
                // voi id can kiem tra (co id trung chung to id do ton tai)
                return true;
            }
        }
        return false;
    }

    @Override
    public long count() {
        return customers.size();
        // dem
    }

}
