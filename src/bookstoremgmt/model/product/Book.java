package bookstoremgmt.model.product;

import bookstoremgmt.model.catalog.Author;

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

    /**
     * Default constructor for Book. Initializes with default values.
     */
    public Book() {
    }

    /**
     * Constructor for creating a Book with essential attributes.
     */
    public Book(String id, String title, Author author, double price, int stockQuantity) {
        super(id, title, price, stockQuantity);
        this.author = author;
    }

    /**
     * Full constructor for creating a Book with all attributes.
     */
    public Book(String id, String title, Author author, double price, int stockQuantity,
            String category, String publisher, int yearPublished,
            String language, String description, int status, int Discount, int totalSales, int totalStarRatings,
            int numberOfRatings, double averageRating) {
        super(id, title, price, stockQuantity, category, status, Discount, totalSales, totalStarRatings,
                numberOfRatings, averageRating);
        this.author = author;
        this.publisher = publisher;
        this.yearPublished = yearPublished;
        this.language = language;
        this.description = description;
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
}