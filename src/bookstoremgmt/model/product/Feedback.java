package bookstoremgmt.model.product;

/**
 * Feedback class represents customer feedback for products in the bookstore
 * management system.
 * It can be extended to include properties such as rating, comments, and
 * customer information.
 * 
 * @author Nguyen Tran Duc Anh
 */
public class Feedback {
    private String feedbackId; // Unique identifier for the feedback
    private String productId; // Identifier for the product associated with this feedback
    private String customerId; // Identifier for the customer who provided the feedback
    private String comment; // Customer's comment about the product
    private int rating; // Customer's rating for the product (e.g., 1 to 5)
    private String date; // Date when the feedback was provided

    /**
     * Default constructor initializes the feedback with default values
     */
    public Feedback() {
    }

    /**
     * Parameterized constructor to initialize all properties of the feedback
     */
    public Feedback(String feedbackId, String productId, String customerId, String comment, int rating,
            String date) {
        this.feedbackId = feedbackId;
        this.productId = productId;
        this.customerId = customerId;
        this.comment = comment;
        this.rating = rating;
        this.date = date;
    }

    // Getters and Setters
    public String getFeedbackId() {
        return feedbackId;
    }

    public void setFeedbackId(String feedbackId) {
        this.feedbackId = feedbackId;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
