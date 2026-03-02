package bookstoremgmt.model.product;

import java.util.Map;
import java.util.HashMap;

/**
 * Represents a bundled collection of books sold together.
 * Extends the abstract Product class.
 *
 * @author Nguyen Tran Duc Anh
 */
public class Combo extends Product {

    // A combo contains a list of specific books
    private Map<Product, Integer> includedProducts = new HashMap<>(); // Book and its quantity in the combo

    /**
     * Constructor for creating a Combo with essential attributes.
     */
    public Combo(String id, String name, double price, int stockQuantity, Map<Product, Integer> includedProducts) {
        super(id, name, price, stockQuantity);
        this.includedProducts = includedProducts;
    }

    // Getters and setters
    public Map<Product, Integer> getIncludedProducts() {
        return includedProducts;
    }

    public void setIncludedProducts(Map<Product, Integer> includedProducts) {
        this.includedProducts = includedProducts;
    }
}