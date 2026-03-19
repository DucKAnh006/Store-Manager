package storemanagement.model.product;

import java.util.ArrayList;
import java.util.List;

import storemanagement.model.catalog.Supplier;

/**
 * Represents a bundled collection of books sold together.
 * Extends the abstract Product class.
 *
 * @author Nguyen Tran Duc Anh
 */
public class Combo extends Product {

    // A combo contains a list of specific books
    private List<Product> includedProducts = new ArrayList<>(); // Product in the combo

    /**
     * Constructor for creating a Combo with essential attributes.
     */
    public Combo(String id, String name, double price, int stockQuantity,
            String category, int discount, int totalSales, int totalStarRatings,
            int numberOfRatings, double averageRating, Supplier supplier, List<Product> includedProducts) {
         super(id, name, price, stockQuantity, category, 2, discount, totalSales, totalStarRatings,
                numberOfRatings, averageRating, supplier);
        this.includedProducts = includedProducts;
    }

    // Getters and setters
    public List<Product> getIncludedProducts() {
        return includedProducts;
    }

    public void setIncludedProducts(List<Product> includedProducts) {
        this.includedProducts = includedProducts;
    }
}