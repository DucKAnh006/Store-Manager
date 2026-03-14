package bookstoremgmt.model.product;

import bookstoremgmt.model.catalog.Author;
import bookstoremgmt.model.catalog.Supplier;

/**
 * Book class represents a book product in the bookstore management system. It
 * extends the Product class and has additional properties such as author,
 * publisher, year published, language, and description.
 * 
 * @author Nguyen Tran Duc Anh
 */
public class Book extends Product {
    private Author author; // Author of the book
    private String publisher; // Publisher of the book
    private int yearPublished; // Year the book was published
    private String language; // Language of the book
    private String description; // Description of the book
    private BookStatus status; // Status of the product (e.g., RUMOUR, AVAILABLE, OUT_OF_STOCK, DISCONTINUED)

    /**
     * Enum representing the status of a book product.
     */
    public enum BookStatus {
        RUMOUR, AVAILABLE, OUT_OF_STOCK, DISCONTINUED
    }

    /**
     * Default constructor for Book. Initializes with default values.
     */
    public Book() {
        super(0);
    }

    /**
     * Constructor for creating a Book with essential attributes.
     */
    public Book(String id, String title, Author author, double price, int stockQuantity) {
        super(id, title, price, stockQuantity, 0);
        this.author = author;
    }

    /**
     * Full constructor for creating a Book with all attributes.
     */
    public Book(String id, String title, Author author, double price, int stockQuantity,
            String category, int status, String publisher, int yearPublished,
            String language, String description, int discount, int totalSales, int totalStarRatings,
            int numberOfRatings, double averageRating, Supplier supplier) {
        super(id, title, price, stockQuantity, category, 0, discount, totalSales, totalStarRatings,
                numberOfRatings, averageRating, supplier);
        this.author = author;
        this.publisher = publisher;
        this.yearPublished = yearPublished;
        this.language = language;
        this.description = description;
        setStatus(status);
    }

    // Getters and setters
    public Author getAuthor() {
        return author;
    }

    public void setAuthor(Author author) {
        this.author = author;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public int getYearPublished() {
        return yearPublished;
    }

    public void setYearPublished(int yearPublished) {
        this.yearPublished = yearPublished;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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
}