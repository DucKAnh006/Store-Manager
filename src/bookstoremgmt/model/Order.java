package bookstoremgmt.model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 *
 * @author Nguyen Tran Duc Anh
 */
public class Order {
    private String orderId;
    private String customerId;
    private LocalDate orderDate;
    private final List<OrderDetail> orderDetails;
    private OrderStatus status;

    // Enum for order status
    public enum OrderStatus {
        PENDING, CONFIRMED, SHIPPED, DELIVERED, CANCELLED
    }

    public Order() {
        this.orderDetails = new ArrayList<>();
        this.orderDate = LocalDate.now();
        this.status = OrderStatus.PENDING;
    }

    public Order(String orderId, String customerId) {
        this.orderId = orderId;
        this.customerId = customerId;
        this.orderDate = LocalDate.now();
        this.orderDetails = new ArrayList<>();
        this.status = OrderStatus.PENDING;
    }

    public Order(String orderId, String customerId, LocalDate orderDate) {
        this.orderId = orderId;
        this.customerId = customerId;
        this.orderDate = orderDate;
        this.orderDetails = new ArrayList<>();
        this.status = OrderStatus.PENDING;
    }

    // Getters

    public String getOrderId() {
        return orderId;
    }

    public String getCustomerId() {
        return customerId;
    }

    public LocalDate getOrderDate() {
        return orderDate;
    }

    public List<OrderDetail> getOrderDetails() {
        return Collections.unmodifiableList(orderDetails);
    }

    public OrderStatus getStatus() {
        return status;
    }

    // Setters

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public void setOrderDate(LocalDate orderDate) {
        this.orderDate = orderDate;
    }

    public void setStatus(OrderStatus status) {
        this.status = status;
    }

    /**
     * Add order detail to the order
     * Service layer should validate stock availability before calling this
     */
    public void addOrderDetail(OrderDetail detail) {
        if (detail != null) {
            orderDetails.add(detail);
        }
    }

    /**
     * Remove order detail from the order
     * Service layer should handle business logic validation
     */
    public boolean removeOrderDetail(OrderDetail detail) {
        return orderDetails.remove(detail);
    }

    public void clearOrderDetails() {
        orderDetails.clear();
    }

    // Calculate total amount (computed on demand from order details)
    public double getTotalAmount() {
        return orderDetails.stream()
                .filter(detail -> detail != null)
                .mapToDouble(OrderDetail::getTotalPrice)
                .sum();
    }

    // Get total number of items (books) in order
    public int getTotalItemCount() {
        return orderDetails.stream()
                .filter(detail -> detail != null)
                .mapToInt(OrderDetail::getQuantity)
                .sum();
    }

    // Get number of different books (distinct items)
    public int getDistinctItemCount() {
        return orderDetails.size();
    }

    // Check if order is in pending status
    public boolean isPending() {
        return status == OrderStatus.PENDING;
    }

    // Check if order is confirmed
    public boolean isConfirmed() {
        return status == OrderStatus.CONFIRMED;
    }

    // Check if order is cancelled
    public boolean isCancelled() {
        return status == OrderStatus.CANCELLED;
    }

    // Check if order is empty (no items)
    public boolean isEmpty() {
        return orderDetails.isEmpty();
    }

    @Override
    public String toString() {
        return "Order{" +
                "orderId='" + orderId + '\'' +
                ", customerId='" + customerId + '\'' +
                ", orderDate=" + orderDate +
                ", status=" + status +
                ", itemsCount=" + getDistinctItemCount() +
                ", totalAmount=" + String.format("%.2f", getTotalAmount()) +
                '}';
    }
}