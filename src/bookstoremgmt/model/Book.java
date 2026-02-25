/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bookstoremgmt.model;

/**
 *
 * @author FA25_PRO192_SE2002_Group4
 */
public class Book {
    // Core attributes
    private String id;
    private String title;
    private Author author;
    private double price;
    private int stockQuantity;

    // Additional attributes
    private String category;
    private String publisher;
    private int yearPublished;
    private String language;
    private String description;

    public Book() {
    }

    // Essential constructor
    public Book(String id, String title, Author author, double price, int stockQuantity) {
        this.id = id;
        this.title = title;
        this.author = author;
        setPrice(price);
        setStockQuantity(stockQuantity);
    }

    // Full constructor
    public Book(String id, String title, Author author, double price, int stockQuantity,
            String category, String publisher, int yearPublished,
            String language, String description) {
        this(id, title, author, price, stockQuantity);
        this.category = category;
        this.publisher = publisher;
        this.yearPublished = yearPublished;
        this.language = language;
        this.description = description;
    }

    // Getters
    public String getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public Author getAuthor() {
        return author;
    }

    public double getPrice() {
        return price;
    }

    public int getStockQuantity() {
        return stockQuantity;
    }

    public String getCategory() {
        return category;
    }

    public String getPublisher() {
        return publisher;
    }

    public int getYearPublished() {
        return yearPublished;
    }

    public String getLanguage() {
        return language;
    }

    public String getDescription() {
        return description;
    }

    // Setters with validation
    public void setId(String id) {
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setAuthor(Author author) {
        this.author = author;
    }

    public void setPrice(double price) {
        if (price < 0) {
            throw new IllegalArgumentException("Price cannot be negative");
        }
        this.price = price;
    }

    public void setStockQuantity(int stockQuantity) {
        if (stockQuantity < 0) {
            throw new IllegalArgumentException("Stock quantity cannot be negative");
        }
        this.stockQuantity = stockQuantity;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public void setYearPublished(int yearPublished) {
        this.yearPublished = yearPublished;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    // Business methods
    public boolean isAvailable() {
        return stockQuantity > 0;
    }

    public void increaseStock(int quantity) {
        if (quantity <= 0) {
            throw new IllegalArgumentException("Quantity must be positive");
        }
        this.stockQuantity += quantity;
    }

    public boolean decreaseStock(int quantity) {
        if (quantity <= 0) {
            throw new IllegalArgumentException("Quantity must be positive");
        }
        if (this.stockQuantity < quantity) {
            return false;
        }
        this.stockQuantity -= quantity;
        return true;
    }

    @Override
    public String toString() {
        return "Book{" +
                "id='" + id + '\'' +
                ", title='" + title + '\'' +
                ", author=" + (author != null ? author.getName() : "N/A") +
                ", price=" + price +
                ", stock=" + stockQuantity +
                ", category='" + category + '\'' +
                '}';
    }
}