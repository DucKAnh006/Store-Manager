package storemanagement.model.order;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Order class represents a customer's order in the bookstore management system. It contains details such as order ID, customer ID, order date, list of order details (items), and order status. The class provides methods to manage order details and calculate total amount and item counts.     
 * @author Nguyen Tran Duc Anh
 */
public class Order {
    private String orderId; // Unique identifier for the order
    private String customerId; // Identifier for the customer who placed the order
    private LocalDate orderDate; // Date when the order was placed
    private final List<OrderDetail> orderDetails; // List of order details (items in the order)
    private OrderStatus status; // Current status of the order (e.g., PENDING, CONFIRMED, SHIPPED, DELIVERED, CANCELLED)
    private Voucher voucher; // Optional voucher applied to the order for discounts

    // Enum for order status
    public enum OrderStatus {
        PENDING, CONFIRMED, SHIPPED, DELIVERED, CANCELLED
    }

    /**
     *  Default constructor initializes the order with default values. Order details list is initialized as an empty list, order date is set to the current date, and status is set to PENDING.
     */
    public Order() {
        this.orderDetails = new ArrayList<>();
        this.orderDate = LocalDate.now();
        this.status = OrderStatus.PENDING;
    }

    /**
     *  Parameterized constructor to initialize the order with specific values. Order details list is initialized as an empty list, and status is set to PENDING.
     */
    public Order(String orderId, String customerId) {
        this.orderId = orderId;
        this.customerId = customerId;
        this.orderDate = LocalDate.now();
        this.orderDetails = new ArrayList<>();
        this.status = OrderStatus.PENDING;
    }

    /**
     * Parameterized constructor to initialize the order with specific values for order ID, customer ID, and order date. Order details list is initialized as an empty list, and status is set to PENDING.
     */
    public Order(String orderId, String customerId, LocalDate orderDate) {
        this.orderId = orderId;
        this.customerId = customerId;
        this.orderDate = orderDate;
        this.orderDetails = new ArrayList<>();
        this.status = OrderStatus.PENDING;
    }

    // Getters and Setters

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public LocalDate getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(LocalDate orderDate) {
        this.orderDate = orderDate;
    }

    public List<OrderDetail> getOrderDetails() {
        return Collections.unmodifiableList(orderDetails);
    }

    public OrderStatus getStatus() {
        return status;
    }

    public void setStatus(OrderStatus status) {
        this.status = status;
    }

    /**
     * Add order detail to the order
     * Service layer should validate stock availability before calling this
     */
    public void addOrderDetail(OrderDetail detail) {
        // Ensure that null details are not added to the order
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

    /**
     * Clear all order details from the order. Service layer should handle business logic validation before calling this method.
     */
    public void clearOrderDetails() {
        orderDetails.clear();
    }

    /**
     * Calculate total amount for the order by summing up the total price of each order detail. It uses Java Streams to perform the calculation in a concise and efficient manner. The method also includes null checks to prevent potential NullPointerExceptions if any order details are null.
     * @return the total amount for the order
     */
    public double getTotalAmount() {
        double total = orderDetails.stream() // Use stream to calculate total amount from order details
                .filter(detail -> detail != null) // Filter out any null details to prevent NullPointerException
                .mapToDouble(OrderDetail::getTotalPrice) // Map each order detail to its total price
                .sum(); // Sum up the total prices to get the total amount for the order
        if (voucher != null && voucher.getMinOrderValue() <= total && voucher.getExpirationDate().isAfter(LocalDate.now())) {
            double discount = voucher.isPercentage() ? total * voucher.getDiscountValue() / 100 : voucher.getDiscountValue();
            total -= discount;
        }
        return total;
    }

    /**
     * Calculate total item count for the order by summing up the quantity of each order detail. It uses Java Streams to perform the calculation in a concise and efficient manner. The method also includes null checks to prevent potential NullPointerExceptions if any order details are null.
     * @return the total item count for the order
     */
    public int getTotalItemCount() {
        return orderDetails.stream() // Use stream to calculate total item count from order details
                .filter(detail -> detail != null) // Filter out any null details to prevent NullPointerException
                .mapToInt(OrderDetail::getQuantity) // Map each order detail to its quantity
                .sum(); // Sum up the quantities to get the total item count for the order
    }

    /**
     * Calculate distinct item count for the order by counting the number of order details. This method assumes that each order detail represents a distinct item, regardless of quantity. It uses Java Streams to perform the calculation in a concise and efficient manner. The method also includes null checks to prevent potential NullPointerExceptions if any order details are null.
     * @return the distinct item count for the order
     */
    public int getDistinctItemCount() {
        return orderDetails.size();
    }

    /**
     * Check if order is pending
     * @return true if the order status is PENDING, false otherwise
     */
    public boolean isPending() {
        return status == OrderStatus.PENDING;
    }

    /**
     * Check if order is confirmed
     * @return true if the order status is CONFIRMED, false otherwise
     */
    public boolean isConfirmed() {
        return status == OrderStatus.CONFIRMED;
    }

    /**
     * Check if order is cancelled
     * @return true if the order status is CANCELLED, false otherwise
     */
    public boolean isCancelled() {
        return status == OrderStatus.CANCELLED;
    }

    /**
     * Check if order is empty (no items)
     * @return true if the order has no items, false otherwise
     */
    public boolean isEmpty() {
        return orderDetails.isEmpty();
    }

}