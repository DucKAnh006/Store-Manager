package bookstoremgmt.model.order;

import bookstoremgmt.model.product.*;

/**
 * OrderDetail class represents the details of an order in the bookstore management
 * @author Nguyen Tran Duc Anh
 */
public class OrderDetail {
    private Product product; // The product associated with this order detail
    private int quantity; // Quantity of the product ordered
    private double totalPrice; // Total price for this order detail (calculated as price * quantity - discount)
    private double price; // Price of the product at the time of order
    private double discount; // Discount applied to this order detail (as a percentage)

    /**
     * Default constructor initializes the order detail with default values
     */
    public OrderDetail() {
    }

    /**
     * Parameterized constructor to initialize all properties of the order detail
     */
    public OrderDetail(Product product, int quantity, double price, double discount) {
        this.product = product;
        this.quantity = quantity;
        this.price = price; 
        this.discount = discount;
        updateTotalPrice(); // Calculate total price
    }

    // Getters and Setters
    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
        updateTotalPrice(); // Update total price when product changes
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
        updateTotalPrice(); // Update total price when quantity changes
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    // Private method to ensure consistency of total price
    private void updateTotalPrice() {
        // Calculate total price based on current product, quantity, price, and discount
        if (product != null) {
            this.totalPrice = price * quantity * (1 - discount / 100.0);
        } else {
            this.totalPrice = 0;
        }
    }
}
