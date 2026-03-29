package storemanagement.model.product;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import storemanagement.model.catalog.Supplier;

/**
 * Abstract base class representing a generic product in the bookstore.
 * Contains common attributes shared by Books, Combos, and Stationery.
 *
 * @author Nguyen Tran Duc Anh
 */
@Entity
@Table(name = "BM_Product")
@Inheritance(strategy = InheritanceType.JOINED)
@Data
@NoArgsConstructor
@AllArgsConstructor
public abstract class Product {

    /**
     * Enum representing the type of a product.
     */
    public enum ProductType {
        BOOK, STATIONERY, COMBO
    }

    @Id
    @Column(name = "product_id")
    private String id; // Unique identifier for the product

    @Column(name = "name")
    private String name; // Name of the product

    @Column(name = "price")
    private double price; // Price of the product

    @Column(name = "stock_quantity")
    private int stockQuantity; // Quantity of the product available in stock

    @Column(name = "category")
    private String category; // Category of the product (e.g., Fiction, Non-Fiction, Combo, Stationery)

    @Enumerated(EnumType.ORDINAL)
    @Column(name = "product_type")
    private ProductType productType; // Type of product

    @Column(name = "total_sales")
    private int totalSales; // Total number of sales for the product

    @Column(name = "total_star_ratings")
    private int totalStarRatings; // Total star ratings accumulated for the product

    @Column(name = "number_of_ratings")
    private int numberOfRatings; // Number of ratings received for the product

    @Column(name = "average_rating")
    private double averageRating; // Average rating calculated from totalStarRatings and numberOfRatings

    @Column(name = "discount")
    private int discount; // Discount percentage for the product

    @ManyToOne
    @JoinColumn(name = "supplier_id")
    private Supplier supplier; // ID of the supplier providing the product
}