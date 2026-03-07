package bookstoremgmt.model.product;

import bookstoremgmt.model.catalog.Supplier;

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
    private BookStatus status; // Status of the product (e.g., RUMOUR, AVAILABLE, OUT_OF_STOCK, DISCONTINUED)
    private int totalSales; // Total number of sales for the product    
    private int totalStarRatings; // Total star ratings accumulated for the product
    private int numberOfRatings; // Number of ratings received for the product
    private double averageRating; // Average rating calculated from totalStarRatings and numberOfRatings
    private int discount; // Discount percentage for the product
    private Supplier supplier; // ID of the supplier providing the product

    /**
     * Enum representing the status of a book product.
     */
    public enum BookStatus {
        RUMOUR, AVAILABLE, OUT_OF_STOCK, DISCONTINUED
    }

    /**
     * Default constructor for framework compatibility.
     */
    public Product() {
    }

    /**
     * Constructor to initialize essential product attributes.
     */
    public Product(String id, String name, double price, int stockQuantity) {
        this.id = id;
        this.name = name;
        setPrice(price);
        setStockQuantity(stockQuantity);
    }

    /**
     * Full constructor to initialize all product attributes.
     */
    public Product(String id, String name, double price, int stockQuantity,
            String category, int status, int Discount, int totalSales, int totalStarRatings,
            int numberOfRatings, double averageRating, Supplier supplier) {
        this.id = id;
        this.name = name;
        setPrice(price);
        setStockQuantity(stockQuantity);
        this.category = category;
        setStatus(status);
        this.discount = Discount;
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
     * Get the status of the product as an integer for easier handling in UI and logic
     */
    public int getStatus() {
        switch (status) {
            case RUMOUR:
                return 0;
            case AVAILABLE:
                return 1;
            case OUT_OF_STOCK:
                return 2;
            default:
                return 3; // DISCONTINUED
        }
    }

    /**
     * Set the status of the product using an integer value
     * 0 = RUMOUR, 1 = AVAILABLE, 2 = OUT_OF_STOCK, 3 = DISCONTINUED
     */
    public void setStatus(int status) {
        switch (status) {
            case 0:
                this.status = BookStatus.RUMOUR;
                break;
            case 1:
                this.status = BookStatus.AVAILABLE;
                break;
            case 2:
                this.status = BookStatus.OUT_OF_STOCK;
                break;
            default:
                this.status = BookStatus.DISCONTINUED;
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