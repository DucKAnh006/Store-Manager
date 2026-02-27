package bookstoremgmt.model.order;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents a shopping cart holding various products and their quantities.
 *
 * @author Nguyen Tran Duc Anh
 */
public class Cart {
    private String customerId; // Unique identifier for the customer associated with this cart
    private final List<CartItem> cartItems; // List to store items in the cart, each item includes product details and quantity

    /**
     * Constructor initializes an empty cart.
     */
    public Cart() {
        // Initialize with an empty list to prevent NullPointerException
        this.cartItems = new ArrayList<>();
    }

    // Getters and Setters
    public List<CartItem> getCartItems() {
        return cartItems;
    }

    public void setCartItems(List<CartItem> cartItems) {
        this.cartItems.clear();
        if (cartItems != null) {
            this.cartItems.addAll(cartItems);
        }
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    // Business methods

    /**
     * Adds an item to the cart. If the item already exists, it updates the quantity.
     * @param item
     */
    public void addItem(CartItem item) {
        if (item != null) {
            cartItems.add(item);
        }
    }

    /**
     * Removes an item from the cart.
     */
    public boolean removeItem(CartItem item) {
        return cartItems.remove(item);
    }

    /**
     * Calculates the total cost of the cart by summing up the total price of each selected cart item.
     * * @return the total cost of the cart 
     */
    public double calculateCartTotal() {
        double total = 0.0;
        // Loop through the cart items
        for (CartItem cartItem : cartItems) {
            // Only include items that are selected for checkout
            if (!cartItem.isSelected()){
                continue;
            }
            total += cartItem.getTotalPrice(); // Use the total price from CartItem which already accounts for quantity and discount
        }
        return total;
    }

    /**
     * Clears all items from the cart, effectively resetting it to an empty state.
     */
    public void clearCart() {
        cartItems.clear();
    }
}