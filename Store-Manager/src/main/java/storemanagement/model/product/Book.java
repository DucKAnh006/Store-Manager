package storemanagement.model.product;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import storemanagement.model.catalog.Author;

/**
 * Book class represents a book product in the bookstore management system. It
 * extends the Product class and has additional properties such as author,
 * publisher, year published, language, and description.
 * 
 * @author Nguyen Tran Duc Anh
 */
@Entity
@Table(name = "BM_Books")
@PrimaryKeyJoinColumn(name = "product_id")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Book extends Product {

    /**
     * Enum representing the status of a book product.
     */
    public enum BookStatus {
        RUMOUR, AVAILABLE, OUT_OF_STOCK, DISCONTINUED
    }

    @ManyToOne
    @JoinColumn (name = "author_id")
    private Author author; // Author of the book

    @Column (name = "publisher")
    private String publisher; // Publisher of the book

    @Column (name = "year_published")
    private int yearPublished; // Year the book was published

    @Column (name = "language") 
    private String language; // Language of the book

    @Column (name = "description")
    private String description; // Description of the book

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private BookStatus status; // Status of the product (e.g., RUMOUR, AVAILABLE, OUT_OF_STOCK, DISCONTINUED)

    
}