package bookstoremgmt.model.account;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import bookstoremgmt.model.order.Order;

/**
 * Customer class represents a customer account in the bookstore management
 * system. It extends the Accounts class and has additional properties such as order
 * 
 * @author Nguyen Tran Duc Anh
 */
public class Customer extends Accounts {
    private final List<Order> orderHistory; // List to store customer's order history
    private String phoneNumber; // Customer's phone number
    private String email; // Customer's email address
    private String id; // Unique identifier for the customer
    private List<String> addresses; // List to store customer's addresses
    private int loyaltyPoints;  // Loyalty points accumulated by the customer

    /**
     * Default constructor initializes the order history and loyalty points
     */
    public Customer() {
        super();
        this.orderHistory = new ArrayList<>();
        this.loyaltyPoints = 0;
    }

    /**
     * Parameterized constructor to initialize all properties of the customer
     */
    public Customer(String id, String name, String phoneNumber, int loyaltyPoints, String email, String userId,
            String password, List<String> addresses) {
        super(name, userId, password); // Assuming password is not set for customers
        this.id = id;
        this.phoneNumber = phoneNumber;
        this.loyaltyPoints = loyaltyPoints;
        this.email = email;
        this.addresses = new ArrayList<>();
        this.orderHistory = new ArrayList<>();

    }

    // Getters and Setters
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getLoyaltyPoints() {
        return loyaltyPoints;
    }

    public void setLoyaltyPoints(int loyaltyPoints) {
        this.loyaltyPoints = loyaltyPoints;
    }

    public List<String> getAddresses() {
        return Collections.unmodifiableList(addresses);
    }

    // Business methods

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
     * Remove an address from the customer's address list
     * @param address
     */
    public void removeAddress(String address) {
        if (this.addresses != null) {
            this.addresses.remove(address);
        }
    }

    /**
     * Get number of orders
     * @return the number of orders in the customer's order history
     */
    public int getOrderCount() {
        return orderHistory.size();
    }

    /**
     * Add a new address to the customer's address list
     * @param address
     */
    public void addAddress(String address) {
        if (this.addresses == null) {
            this.addresses = new ArrayList<>();
        }
        this.addresses.add(address);
    }

    /**
     * Add an order to the customer's order history
     */
    public List<Order> getOrderHistory() {
        return Collections.unmodifiableList(orderHistory);
    }

}