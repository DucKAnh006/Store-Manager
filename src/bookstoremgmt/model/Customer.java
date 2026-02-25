package bookstoremgmt.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 *
 * @author Nguyen Tran Duc Anh
 */
public class Customer extends Person {
    private final List<Order> orderHistory;

    public Customer() {
        super();
        this.orderHistory = new ArrayList<>();
    }

    public Customer(String id, String name, String phoneNumber) {
        super(id, name, phoneNumber);
        this.orderHistory = new ArrayList<>();
    }

    // Getters

    public List<Order> getOrderHistory() {
        return Collections.unmodifiableList(orderHistory);
    }

    // Business methods

    /**
     * Add order to customer's history
     * This should be called by Order or OrderService, not directly
     */
    public void addOrder(Order order) {
        if (order == null)
            return;
        String orderId = order.getOrderId();
        if (orderId == null) {
            if (!orderHistory.contains(order)) {
                orderHistory.add(order);
            }
            return;
        }
        for (Order existingOrder : orderHistory) {
            if (existingOrder != null && orderId.equals(existingOrder.getOrderId())) {
                return; // da co order nay trong lich su, khong them nua
            }
        }
        orderHistory.add(order);
    }

    /**
     * Calculate total spending across all orders
     */
    public double getTotalSpending() {
        return orderHistory.stream()
                .filter(order -> order != null)
                .mapToDouble(Order::getTotalAmount)
                .sum();
    }

    /**
     * Get number of orders
     */
    public int getOrderCount() {
        return orderHistory.size();
    }

    @Override
    public String toString() {
        return String.format(
                "ID:%-8s | Name:%-20s | Phone:%-12s | Orders:%-3d | Spending:%10.2f",
                getId(), getName(), getPhoneNumber(), getOrderCount(), getTotalSpending());

    }

    public void clearOrders() {
        orderHistory.clear();
    }
}