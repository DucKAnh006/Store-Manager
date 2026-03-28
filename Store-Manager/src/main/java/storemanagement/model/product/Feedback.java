package storemanagement.model.product;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.time.LocalDate;

import storemanagement.model.account.Customer;
/**
 * Feedback class represents customer feedback for products in the bookstore
 * management system.
 * It can be extended to include properties such as rating, comments, and
 * customer information.
 * 
 * @author Nguyen Tran Duc Anh
 */
@Entity
@Table(name = "BM_Feedback")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Feedback {

    @Id
    @Column (name = "feedback_id")
    private String feedbackId; // Unique identifier for the feedback

    @ManyToOne
    @JoinColumn (name = "product_id")
    private Product product; // Identifier for the product associated with this feedback

    @ManyToOne
    @JoinColumn (name = "customer_id")
    private Customer customer; // Identifier for the customer who provided the feedback

    @Column (name = "comment_text")
    private String comment; // Customer's comment about the product

    @Column (name = "rating")
    private int rating; // Customer's rating for the product (e.g., 1 to 5)

    @Column (name = "comment_date")
    private LocalDate date; // Date when the feedback was provided
}
