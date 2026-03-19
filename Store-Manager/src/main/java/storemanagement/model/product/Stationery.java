package storemanagement.model.product;

import storemanagement.model.catalog.Supplier;

/**
 * Represents non-book items like pens, rulers, toys, and souvenirs.
 * Extends the abstract Product class.
 *
 * @author Nguyen Tran Duc Anh
 */
public class Stationery extends Product {
    private String manufacturer; // Manufacturer of the stationery item
    private String material; // Material of the stationery item (e.g., plastic, metal, wood)

    /**
     * Default constructor for Stationery. Initializes with default values.
     */
    public Stationery() {
        super(1);
    }

    /**
     * Constructor for creating a Stationery item with essential attributes.
     */
    public Stationery(String id, String name, double price, int stockQuantity) {
        super(id, name, price, stockQuantity, 1);
    }

    /**
     * Full constructor for creating a Stationery item with all attributes.
     */
    public Stationery(String id, String name, double price, int stockQuantity,
            String category, int discount, int totalSales, int totalStarRatings,
            int numberOfRatings, double averageRating, String manufacturer, String material, Supplier supplier) {
        super(id, name, price, stockQuantity, category, 1, discount, totalSales, totalStarRatings,
                numberOfRatings, averageRating, supplier);
        this.manufacturer = manufacturer;
        this.material = material;
    }

    // Getters and setters
    public String getManufacturer() {
        return manufacturer;
    }

    public void setManufacturer(String manufacturer) {
        this.manufacturer = manufacturer;
    }

    public String getMaterial() {
        return material;
    }

    public void setMaterial(String material) {
        this.material = material;
    }
}