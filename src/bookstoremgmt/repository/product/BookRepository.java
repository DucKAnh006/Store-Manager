package bookstoremgmt.repository.product;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLDataException;
import java.sql.SQLException;
import java.util.List;

import bookstoremgmt.model.product.Book;
import bookstoremgmt.util.DatabaseConnection;

public class BookRepository {
    private DatabaseConnection dataConnection = new DatabaseConnection();
    private Connection connection;

    public void connect() throws SQLException {
        if (connection == null || connection.isClosed()) {
            connection = dataConnection.getConnection();
        }
    }

    public void addBook(Book book) throws SQLException {
        connection.setAutoCommit(false); // Start transaction

        boolean bookInsertedToProductTable = insertBookToProductTable(book);
        boolean bookInsertedToBookTable = insertBookToBookTable(book);

        if (!bookInsertedToProductTable || !bookInsertedToBookTable) {
            connection.rollback(); // Rollback transaction if any insertion fails
            throw new SQLDataException("Failed to add book with ID: " + book.getId());
        }

        connection.commit(); // Commit transaction if both insertions succeed
    }

    public void addBooks(List<Book> books) throws SQLException {
        connection.setAutoCommit(false); // Start transaction

        boolean booksInsertedToProductTable = insertBookToProductTable(books);
        boolean booksInsertedToBookTable = insertBookToBookTable(books);

        if (!booksInsertedToProductTable || !booksInsertedToBookTable) {
            connection.rollback(); // Rollback transaction if any insertion fails
            throw new SQLDataException("Failed to add books.");
        }

        connection.commit(); // Commit transaction if both insertions succeed
    }

    public void deleteBook(String bookId) throws SQLException {
        boolean bookDeletedFromProductTable = deleteBookFromProductTable(bookId);
        boolean bookDeletedFromBookTable = deleteBookFromBookTable(bookId);

        if (!bookDeletedFromProductTable || !bookDeletedFromBookTable) {
            throw new SQLDataException("Failed to delete book with ID: " + bookId);
        }
    }

    public void deleteBooks(List<String> bookIds) throws SQLException {
        boolean bookDeletedFromProductTable = deleteBookFromProductTable(bookIds);
        boolean bookDeletedFromBookTable = deleteBookFromBookTable(bookIds);

        if (!bookDeletedFromProductTable || !bookDeletedFromBookTable) {
            throw new SQLDataException("Failed to delete books.");
        }
    }

    public void updateBook(Book book) throws SQLException {
        boolean bookUpdatedInProductTable = updateBookInProductTable(book);
        boolean bookUpdatedInBookTable = updateBookInBookTable(book);

        if (!bookUpdatedInProductTable || !bookUpdatedInBookTable) {
            throw new SQLDataException("Failed to update book with ID: " + book.getId());
        }
    }

    private boolean insertBookToProductTable(Book book) throws SQLException {
        String sql = "INSERT INTO BM_Product (product_id, name, price, stock_quantity, category, status, total_sales, total_star_ratings, number_of_ratings, average_rating, discount, supplier_id) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        PreparedStatement query = connection.prepareStatement(sql);
        query.setString(1, book.getId());
        query.setString(2, book.getName());
        query.setDouble(3, book.getPrice());
        query.setInt(4, book.getStockQuantity());
        query.setString(5, book.getCategory());
        query.setInt(6, book.getStatus());
        query.setInt(7, book.getTotalSales());
        query.setInt(8, book.getTotalStarRatings());
        query.setInt(9, book.getNumberOfRatings());
        query.setDouble(10, book.getAverageRating());
        query.setDouble(11, book.getDiscount());
        query.setString(12, book.getSupplier().getId());

        if (query.executeUpdate() == 0) {
            throw new SQLDataException("Failed to insert product details into database.");
        } else {
            return query.executeUpdate() > 0;
        }
    }

    private boolean insertBookToProductTable(List<Book> books) throws SQLException {
        String sql = "INSERT INTO BM_Product (product_id, name, price, stock_quantity, category, status, total_sales, total_star_ratings, number_of_ratings, average_rating, discount, supplier_id) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        PreparedStatement query = connection.prepareStatement(sql);
        for (Book book : books) {
            query.setString(1, book.getId());
            query.setString(2, book.getName());
            query.setDouble(3, book.getPrice());
            query.setInt(4, book.getStockQuantity());
            query.setString(5, book.getCategory());
            query.setInt(6, book.getStatus());
            query.setInt(7, book.getTotalSales());
            query.setInt(8, book.getTotalStarRatings());
            query.setInt(9, book.getNumberOfRatings());
            query.setDouble(10, book.getAverageRating());
            query.setDouble(11, book.getDiscount());
            query.setString(12, book.getSupplier().getId());

            if (query.executeUpdate() == 0) {
                throw new SQLDataException(
                        "Failed to insert product details into database for book with ID: " + book.getId());
            }
        }
        return true;
    }

    private boolean insertBookToBookTable(Book book) throws SQLException {
        String sql = "INSERT INTO BM_Book (product_id, author_id, publisher, year_published, language, description) VALUES (?, ?, ?, ?, ?, ?)";
        PreparedStatement query = connection.prepareStatement(sql);
        query.setString(1, book.getId());
        query.setString(2, book.getAuthor().getId());
        query.setString(3, book.getPublisher());
        query.setInt(4, book.getYearPublished());
        query.setString(5, book.getLanguage());
        query.setString(6, book.getDescription());

        if (query.executeUpdate() == 0) {
            throw new SQLDataException("Failed to insert book details into database.");
        } else {
            return query.executeUpdate() > 0;
        }
    }

    private boolean insertBookToBookTable(List<Book> books) throws SQLException {
        String sql = "INSERT INTO BM_Book (product_id, author_id, publisher, year_published, language, description) VALUES (?, ?, ?, ?, ?, ?)";
        PreparedStatement query = connection.prepareStatement(sql);
        for (Book book : books) {
            query.setString(1, book.getId());
            query.setString(2, book.getAuthor().getId());
            query.setString(3, book.getPublisher());
            query.setInt(4, book.getYearPublished());
            query.setString(5, book.getLanguage());
            query.setString(6, book.getDescription());

            if (query.executeUpdate() == 0) {
                throw new SQLDataException("Failed to insert book details into database.");
            }
        }
        return true;
    }

    private boolean deleteBookFromProductTable(String bookId) throws SQLException {
        String sql = "DELETE FROM BM_Product WHERE product_id = ?";
        PreparedStatement query = connection.prepareStatement(sql);
        query.setString(1, bookId);
        return query.executeUpdate() > 0;
    }

    private boolean deleteBookFromProductTable(List<String> bookIds) throws SQLException {
        String sql = "DELETE FROM BM_Product WHERE product_id = ?";
        PreparedStatement query = connection.prepareStatement(sql);
        for (String bookId : bookIds) {
            query.setString(1, bookId);
            if (query.executeUpdate() == 0) {
                throw new SQLDataException("Failed to delete product with ID: " + bookId);
            }
        }
        return true;
    }

    private boolean deleteBookFromBookTable(String bookId) throws SQLException {
        String sql = "DELETE FROM BM_Book WHERE product_id = ?";
        PreparedStatement query = connection.prepareStatement(sql);
        query.setString(1, bookId);
        return query.executeUpdate() > 0;
    }

    private boolean deleteBookFromBookTable(List<String> bookIds) throws SQLException {
        String sql = "DELETE FROM BM_Book WHERE product_id = ?";
        PreparedStatement query = connection.prepareStatement(sql);
        for (String bookId : bookIds) {
            query.setString(1, bookId);
            if (query.executeUpdate() == 0) {
                throw new SQLDataException("Failed to delete book with ID: " + bookId);
            }
        }
        return true;
    }

    private boolean updateBookInProductTable(Book book) throws SQLException {
        String sql = "UPDATE BM_Product SET name = ?, price = ?, stock_quantity = ?, category = ?, status = ?, total_sales = ?, total_star_ratings = ?, number_of_ratings = ?, average_rating = ?, discount = ?, supplier_id = ? WHERE product_id = ?";
        PreparedStatement query = connection.prepareStatement(sql);
        query.setString(1, book.getName());
        query.setDouble(2, book.getPrice());
        query.setInt(3, book.getStockQuantity());
        query.setString(4, book.getCategory());
        query.setInt(5, book.getStatus());
        query.setInt(6, book.getTotalSales());
        query.setInt(7, book.getTotalStarRatings());
        query.setInt(8, book.getNumberOfRatings());
        query.setDouble(9, book.getAverageRating());
        query.setDouble(10, book.getDiscount());
        query.setString(11, book.getSupplier().getId());
        query.setString(12, book.getId());

        if (query.executeUpdate() == 0) {
            throw new SQLDataException("Failed to update product details in database.");
        } else {
            return query.executeUpdate() > 0;
        }
    }

    private boolean updateBookInBookTable(Book book) throws SQLException {
        String sql = "UPDATE BM_Book SET author_id = ?, publisher = ?, year_published = ?, language = ?, description = ? WHERE product_id = ?";
        PreparedStatement query = connection.prepareStatement(sql);
        query.setString(1, book.getAuthor().getId());
        query.setString(2, book.getPublisher());
        query.setInt(3, book.getYearPublished());
        query.setString(4, book.getLanguage());
        query.setString(5, book.getDescription());
        query.setString(6, book.getId());

        if (query.executeUpdate() == 0) {
            throw new SQLDataException("Failed to update book details in database.");
        } else {
            return query.executeUpdate() > 0;
        }
    }
}
