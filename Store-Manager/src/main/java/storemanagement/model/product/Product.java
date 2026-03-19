package storemanagement.model.product;

import storemanagement.model.catalog.Supplier;

/**
 * Abstract base class representing a generic product in the bookstore.
 * Contains common attributes shared by Books, Combos, and Stationery.
 *
 * @author Nguyen Tran Duc Anh
 */
public abstract class Product {

    private String id; // Unique identifier for the product
    private String name; // Name of the product
    private double price; // Price of the product
    private int stockQuantity; // Quantity of the product available in stock
    private String category; // Category of the product (e.g., Fiction, Non-Fiction, Combo, Stationery)
    private ProductType productType; // Type of product 
    private int totalSales; // Total number of sales for the product    
    private int totalStarRatings; // Total star ratings accumulated for the product
    private int numberOfRatings; // Number of ratings received for the product
    private double averageRating; // Average rating calculated from totalStarRatings and numberOfRatings
    private int discount; // Discount percentage for the product
    private Supplier supplier; // ID of the supplier providing the product

    /**
     * Enum representing the type of a product.
     */
    public enum ProductType {
        BOOK, STATIONERY, COMBO
    }

    /**
     * Default constructor for framework compatibility.
     */
    public Product(int productType) {
        setProductType(productType);
    }

    /**
     * Constructor to initialize essential product attributes.
     */
    public Product(String id, String name, double price, int stockQuantity, int productType) {
        this.id = id;
        this.name = name;
        setPrice(price);
        setStockQuantity(stockQuantity);
        setProductType(productType);
    }

    /**
     * Full constructor to initialize all product attributes.
     */
    public Product(String id, String name, double price, int stockQuantity,
            String category, int productType, int discount, int totalSales, int totalStarRatings,
            int numberOfRatings, double averageRating, Supplier supplier) {
        this.id = id;
        this.name = name;
        setPrice(price);
        setStockQuantity(stockQuantity);
        this.category = category;
        setProductType(productType);
        this.discount = discount;
        this.totalSales = totalSales;
        this.totalStarRatings = totalStarRatings;
        this.numberOfRatings = numberOfRatings;
        this.averageRating = averageRating;
        this.supplier = supplier;
    }

    // Getters and setters for all attributes

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getStockQuantity() {
        return stockQuantity;
    }

    public void setStockQuantity(int stockQuantity) {
        this.stockQuantity = stockQuantity;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    /**
     * Get the type of the product as an integer for easier handling in UI and logic
     */
    public int getProductType() {
        switch (productType) {
            case BOOK:
                return 0;
            case STATIONERY:
                return 1;
            default:
                return 2; // Combo
        }
    }

    /**
     * Set the type of the product using an integer value
     * 0 = BOOK, 1 = STATIONERY, 2 = COMBO
     */
    public void setProductType(int productType) {
        switch (productType) {
            case 0:
                this.productType = ProductType.BOOK;
                break;
            case 1:
                this.productType = ProductType.STATIONERY;
                break;
            default:
                this.productType = ProductType.COMBO;
                break;
        }
    }

    public int getTotalSales() {
        return totalSales;
    }

    public void setTotalSales(int totalSales) {
        this.totalSales = totalSales;
    }

    public int getTotalStarRatings() {
        return totalStarRatings;
    }

    public void setTotalStarRatings(int totalStarRatings) {
        this.totalStarRatings = totalStarRatings;
    }

    public int getNumberOfRatings() {
        return numberOfRatings;
    }

    public void setNumberOfRatings(int numberOfRatings) {
        this.numberOfRatings = numberOfRatings;
    }

    public double getAverageRating() {
        return averageRating;
    }

    public void setAverageRating(double averageRating) {
        this.averageRating = averageRating;
    }

    public int getDiscount() {
        return discount;
    }

    public void setDiscount(int discount) {
        this.discount = discount;
    }

    public Supplier getSupplier() {
        return supplier;
    }

    public void setSupplier(Supplier supplier) {
        this.supplier = supplier;
    }

}