package bookstoremgmt.model;

/**
 *
 * @author Nguyen Tran Duc Anh
 */
public class OrderDetail {
    private Book book;
    private int quantity;
    private double totalPrice;

    public OrderDetail() {
    }

    public OrderDetail(Book book, int quantity) {
        this.book = book;
        this.quantity = quantity;
        updateTotalPrice(); // Calculate total price
    }

    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
        updateTotalPrice(); // Update total price when book changes
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
        updateTotalPrice(); // Update total price when quantity changes
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    // Get current book price
    public double getPrice() {
        return book != null ? book.getPrice() : 0;
    }

    // Private method to ensure consistency of total price
    private void updateTotalPrice() {
        if (book != null) {
            this.totalPrice = book.getPrice() * quantity;
        } else {
            this.totalPrice = 0;
        }
    }

    @Override
    public String toString() {
        return "OrderDetail{" +
                "book=" + (book != null ? book.getTitle() : "null") +
                ", quantity=" + quantity +
                ", totalPrice=" + totalPrice +
                '}';
    }
}
