package bookstoremgmt.model.order;

import bookstoremgmt.model.product.*;

/**
 * CartItem class represents an item in the shopping cart. It contains a product, quantity, total price, and selection status.
 * It provides methods to calculate total price based on the product's price and discount, and to
 * update the total price whenever the product or quantity changes.
 * @author Nguyen Tran Duc Anh
 */
public class CartItem {
    private Product product; // The product associated with this cart item
    private int quantity; // Quantity of the product in the cart
    private double totalPrice; // Total price for this cart item (calculated as product price * quantity * (1 - discount))
    private boolean selected; // Indicates whether this cart item is selected for checkout

    /**
     * Default constructor initializes the cart item with default values. Total price is calculated based on the product and quantity.
     */
    public CartItem() {
    }

    /**
     * Parameterized constructor to initialize the cart item with a product and quantity. Total price is calculated based on the product and quantity.
     */
    public CartItem(Product product, int quantity) {
        this.product = product;
        this.quantity = quantity;
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

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    /**
     * Private method to ensure consistency of total price. It calculates the total price based on the current product, quantity, price, and discount.
     */
    private void updateTotalPrice() {
        if (product != null) {
            // Use the price and discount stored in the cart item for consistency, rather than fetching from the product which may change
            this.totalPrice = product.getPrice() * quantity * (1 - product.getDiscount() / 100.0);
        } else {
            this.totalPrice = 0;
        }
    }
}
