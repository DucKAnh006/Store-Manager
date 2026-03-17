package bookstoremgmt.repository.product;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import bookstoremgmt.model.product.Book;
import bookstoremgmt.util.DatabaseConnection;

/**
 * BookRepository class handles all database operations related to Book entities, including adding, deleting, and updating books in the database. It ensures that operations on both the BM_Product and BM_Books tables are performed atomically to maintain data integrity.
 * @author Nguyen Tran Duc Anh
 */
public class BookRepository {
    private DatabaseConnection dataConnection = new DatabaseConnection(); // Initialize the DatabaseConnection object
    private String sqlInsert = "INSERT INTO BM_Books (product_id, author_id, publisher, status, year_published, language, description) VALUES (?, ?, ?, ?, ?, ?, ?)"; // Prepare the SQL statement for inserting a book into the BM_Books table
    private String sqlDelete = "DELETE FROM BM_Books WHERE product_id = ?"; // Prepare the SQL statement for deleting a book from the BM_Books table by its ID
    private String sqlUpdate = "UPDATE BM_Books SET author_id = ?, publisher = ?, year_published = ?, language = ?, description = ?, status = ? WHERE product_id = ?"; // Prepare the SQL statement for updating a book's specific details in the BM_Books table

    public BookRepository() {
    }

    /**
     * Adds a book to the database. This method ensures that the book is added from both the BM_Books and BM_Product tables. If either addition fails, an exception is thrown to indicate the failure.
     * @param book
     * @throws SQLException
     */
    public void addBook(Book book) throws SQLException {
        // Use try-with-resources to ensure that the database connection is properly closed after the operation is completed, even if an exception occurs
        try (Connection connection = dataConnection.getConnection()) {
            connection.setAutoCommit(false); // Start transaction

            try {
                insertBookToProductTable(book, connection); // Insert into BM_Product table
                insertBookToBookTable(book, connection); // Insert into BM_Books table
            } catch (SQLException e) {
                connection.rollback(); // Rollback transaction if any SQLException occurs during insertion
                throw e; // Rethrow the exception to be handled by the caller
            }

            connection.commit(); // Commit transaction if both insertions succeed
        } catch (SQLException e) {
            throw new SQLException("Failed to add book with ID: " + book.getId(), e); // Throw an exception with the book ID for better debugging
        }
    }

    /**
     * Adds multiple books to the database in a single transaction. If any insertion fails, the entire transaction is rolled back to maintain data integrity.
     * @param books
     * @throws SQLException
     */
    public void addBooks(List<Book> books) throws SQLException {
        // Use try-with-resources to ensure that the database connection is properly closed after the operation is completed, even if an exception occurs
        try (Connection connection = dataConnection.getConnection()) {
            connection.setAutoCommit(false); // Start transaction

            try {
                insertBookToProductTable(books, connection); // Insert multiple books into BM_Product table
                insertBookToBookTable(books, connection); // Insert multiple books into BM_Books table
            } catch (SQLException e) {
                connection.rollback(); // Rollback transaction if any SQLException occurs during insertion
                throw e; // Rethrow the exception to be handled by the caller
            }

            connection.commit(); // Commit transaction if both insertions succeed
        } catch (SQLException e) {
            throw new SQLException("Failed to add books.", e); // Throw a generic exception for multiple books, as it may be difficult to identify which specific book caused the failure
        }
    }

    /**
     * Deletes a book from the database by its ID. This method ensures that the book is deleted from both the BM_Product and BM_Books tables. If either deletion fails, an exception is thrown to indicate the failure.
     * @param bookId
     * @throws SQLException
     */
    public void deleteBook(String bookId) throws SQLException {
        try (Connection connection = dataConnection.getConnection()) {
            connection.setAutoCommit(false); // Start transaction

            try {
                deleteBookFromBookTable(bookId, connection); // Delete from BM_Books table
                deleteBookFromProductTable(bookId, connection); // Delete from BM_Product table
            } catch (SQLException e) {
                connection.rollback(); // Rollback transaction if any SQLException occurs during deletion
                throw e; // Rethrow the exception to be handled by the caller
            }

            connection.commit(); // Commit transaction if both deletions succeed
        } catch (SQLException e) {
            throw new SQLException("Failed to delete book with ID: " + bookId, e); // Throw an exception with the book ID for better debugging
        }
    }
 
    /**
     * Deletes multiple books from the database by their IDs. This method ensures that all specified books are deleted from both the BM_Product and BM_Books tables. If any deletion fails, an exception is thrown to indicate the failure.
     * @param bookIds
     * @throws SQLException
     */
    public void deleteBooks(List<String> bookIds) throws SQLException {
        // Use try-with-resources to ensure that the database connection is properly closed after the operation is completed, even if an exception occurs
        try (Connection connection = dataConnection.getConnection()) {
            connection.setAutoCommit(false); // Start transaction

            try {
                deleteBookFromBookTable(bookIds, connection); // Delete multiple books from BM_Books table
                deleteBookFromProductTable(bookIds, connection); // Delete multiple books from BM_Product table
            } catch (SQLException e) {
                connection.rollback(); // Rollback transaction if any SQLException occurs during deletion
                throw e; // Rethrow the exception to be handled by the caller
            }

            connection.commit(); // Commit transaction if both deletions succeed
        } catch (SQLException e) {
            throw new SQLException("Failed to delete books.", e); // Throw an exception for better debugging
        }
    }

    /**
     * Updates the details of an existing book in the database. This method ensures that the book's information is updated in both the BM_Product and BM_Books tables. If either update fails, an exception is thrown to indicate the failure.
     * @param book
     * @throws SQLException
     */
    public void updateBook(Book book) throws SQLException {
        // Use try-with-resources to ensure that the database connection is properly closed after the operation is completed, even if an exception occurs
        try (Connection connection = dataConnection.getConnection()) {
            connection.setAutoCommit(false); // Start transaction
            
            try {
                updateBookInProductTable(book, connection); // Update book details in BM_Product table
                updateBookInBookTable(book, connection); // Update book details in BM_Books table
            } catch (SQLException e) {
                connection.rollback(); // Rollback transaction if any SQLException occurs during update
                throw e; // Rethrow the exception to be handled by the caller
            }

            connection.commit(); // Commit transaction if both updates succeed
        } catch (SQLException e) {
            throw new SQLException("Failed to update book with ID: " + book.getId(), e); // Throw an exception with the book ID for better debugging
        }
    }

    /**
     * Inserts a book into the BM_Product table. This method prepares an SQL statement to insert the book's details into the BM_Product table. If the insertion fails, an exception is thrown to indicate the failure.
     * @param book
     * @return
     * @throws SQLException
     */
    private void insertBookToProductTable(Book book, Connection connection) throws SQLException {
        // Prepare the SQL statement for inserting a book into the BM_Product table
        String sql = "INSERT INTO BM_Product (product_id, name, price, stock_quantity, category, product_type, total_sales, total_star_ratings, number_of_ratings, average_rating, discount, supplier_id) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        // Using try-with-resources to automatically close the PreparedStatement and prevent memory leaks, even if an exception occurs.
        try (PreparedStatement query = connection.prepareStatement(sql)) {
            // Set the parameters for the SQL query using the book's details
            query.setString(1, book.getId());
            query.setString(2, book.getName());
            query.setDouble(3, book.getPrice());
            query.setInt(4, book.getStockQuantity());
            query.setString(5, book.getCategory());
            query.setInt(6, book.getProductType());
            query.setInt(7, book.getTotalSales());
            query.setInt(8, book.getTotalStarRatings());
            query.setInt(9, book.getNumberOfRatings());
            query.setDouble(10, book.getAverageRating());
            query.setDouble(11, book.getDiscount());
            query.setString(12, book.getSupplier().getId());

            int rowsAffected = query.executeUpdate(); // Execute the SQL query and get the number of rows affected
            // Execute the SQL query and check if the insertion was successful. If the insertion fails, throw an exception for better debugging
            if (rowsAffected == 0) {
                throw new SQLException("Failed to insert product details into database."); // Throw an exception for better debugging
            }
        } catch (SQLException e) {
            throw new SQLException("Failed to insert book with ID: " + book.getId(), e); // Throw an exception with the book ID for better debugging
        }
    }

    /**
     * Inserts multiple books into the BM_Product table. This method prepares an SQL statement to insert the details of each book in the provided list into the BM_Product table. If any insertion fails, an exception is thrown to indicate the failure, along with the ID of the book that caused the failure for better debugging.
     * @param books
     * @return
     * @throws SQLException
     */
    private void insertBookToProductTable(List<Book> books, Connection connection) throws SQLException {
        // Prepare the SQL statement for inserting multiple books into the BM_Product table
        String sql = "INSERT INTO BM_Product (product_id, name, price, stock_quantity, category, product_type, total_sales, total_star_ratings, number_of_ratings, average_rating, discount, supplier_id) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        // Using try-with-resources to automatically close the PreparedStatement and prevent memory leaks, even if an exception occurs.
        try (PreparedStatement query = connection.prepareStatement(sql)) {
            // Loop through the list of books and set the parameters for each book's details in the SQL query
            for (Book book : books) {
                query.setString(1, book.getId());
                query.setString(2, book.getName());
                query.setDouble(3, book.getPrice());
                query.setInt(4, book.getStockQuantity());
                query.setString(5, book.getCategory());
                query.setInt(6, book.getProductType());
                query.setInt(7, book.getTotalSales());
                query.setInt(8, book.getTotalStarRatings());
                query.setInt(9, book.getNumberOfRatings());
                query.setDouble(10, book.getAverageRating());
                query.setDouble(11, book.getDiscount());
                query.setString(12, book.getSupplier().getId());

                query.addBatch(); // Add the SQL statement to the batch for execution
            }

            int[] rowsAffected = query.executeBatch(); // Execute the batch of SQL statements and get the number of rows affected for each statement
            // Loop through the results of the batch execution and check if any insertion failed. If any insertion fails, throw an exception for better debugging
            for (int result : rowsAffected) {
                if (result == 0) {
                    throw new SQLException("Failed to insert product details into database."); // Throw an exception for better debugging
                }
            }
        } catch (SQLException e) {
            throw new SQLException("Failed to insert books.", e); // Throw an exception for better debugging
        }
    }

    /**
     * Inserts a book into the BM_Books table. This method prepares an SQL statement to insert the book's specific details into the BM_Books table. If the insertion fails, an exception is thrown to indicate the failure, along with the ID of the book that caused the failure for better debugging.
     * @param book
     * @return
     * @throws SQLException
     */
    private void insertBookToBookTable(Book book, Connection connection) throws SQLException {
        // Using try-with-resources to automatically close the PreparedStatement and prevent memory leaks, even if an exception occurs.
        try (PreparedStatement query = connection.prepareStatement(sqlInsert)) {
            // Set the parameters for the SQL query using the book's specific details
            query.setString(1, book.getId());
            query.setString(2, book.getAuthor().getId());
            query.setString(3, book.getPublisher());
            query.setInt(4, book.getStatus());
            query.setInt(5, book.getYearPublished());
            query.setString(6, book.getLanguage());
            query.setString(7, book.getDescription());

            int rowsAffected = query.executeUpdate(); // Execute the SQL query and get the number of rows affected
            // Execute the SQL query and check if the insertion was successful. If the insertion fails, throw an exception for better debugging
            if (rowsAffected == 0) {
                throw new SQLException("Failed to insert book details into database."); // Throw an exception for better debugging
            }
        } catch (SQLException e) {
            throw new SQLException("Failed to insert book with ID: " + book.getId(), e); // Throw an exception with the book ID for better debugging
        }
    }

    /**
     * Inserts multiple books into the BM_Books table. This method prepares an SQL statement to insert the specific details of each book in the provided list into the BM_Books table. If any insertion fails, an exception is thrown to indicate the failure, along with the ID of the book that caused the failure for better debugging.
     * @param books
     * @return
     * @throws SQLException
     */
    private void insertBookToBookTable(List<Book> books, Connection connection) throws SQLException {
        // Using try-with-resources to automatically close the PreparedStatement and prevent memory leaks, even if an exception occurs.
        try (PreparedStatement query = connection.prepareStatement(sqlInsert)) {
            // Loop through the list of books and set the parameters for each book's specific details in the SQL query
            for (Book book : books) {
                query.setString(1, book.getId());
                query.setString(2, book.getAuthor().getId());
                query.setString(3, book.getPublisher());
                query.setInt(4, book.getStatus());
                query.setInt(5, book.getYearPublished());
                query.setString(6, book.getLanguage());
                query.setString(7, book.getDescription());

                query.addBatch(); // Add the SQL statement to the batch for execution
            }

            int[] rowsAffected = query.executeBatch(); // Execute the batch of SQL statements and get the number of rows affected for each statement
            // Loop through the results of the batch execution and check if any insertion failed. If any insertion fails, throw an exception for better debugging
            for (int result : rowsAffected) {
                if (result == 0) {
                    throw new SQLException("Failed to insert product details into database."); // Throw an exception for better debugging
                }
            }
        } catch (SQLException e) {
            throw new SQLException("Failed to insert books.", e); // Throw an exception for better debugging
        }
    }

    /**
     * Deletes a book from the BM_Product table by its ID. This method prepares an SQL statement to delete the book's entry from the BM_Product table based on the provided book ID. If the deletion fails, an exception is thrown to indicate the failure, along with the ID of the book that caused the failure for better debugging.
     * @param bookId
     * @return
     * @throws SQLException
     */
    private void deleteBookFromProductTable(String bookId, Connection connection) throws SQLException {
        // Prepare the SQL statement for deleting a book from the BM_Product table by its ID
        String sql = "DELETE FROM BM_Product WHERE product_id = ?";
        // Using try-with-resources to automatically close the PreparedStatement and prevent memory leaks, even if an exception occurs.
        try (PreparedStatement query = connection.prepareStatement(sql)) {
            query.setString(1, bookId); // Set the book ID as a parameter for the SQL query
            query.executeUpdate(); // Execute the SQL query and get the number of rows affected
        } catch (SQLException e) {
            throw new SQLException("Failed to delete book with ID: " + bookId, e); // Throw an exception with the book ID for better debugging
        }
    }

    /**
     * Deletes multiple books from the BM_Product table by their IDs. This method prepares an SQL statement to delete the entries of multiple books from the BM_Product table based on the provided list of book IDs. If any deletion fails, an exception is thrown to indicate the failure, along with the ID of the book that caused the failure for better debugging.
     * @param bookIds
     * @return
     * @throws SQLException
     */
    private void deleteBookFromProductTable(List<String> bookIds, Connection connection) throws SQLException {
        // Prepare the SQL statement for deleting multiple books from the BM_Product table by their IDs
        String sql = "DELETE FROM BM_Product WHERE product_id = ?";
       // Using try-with-resources to automatically close the PreparedStatement and prevent memory leaks, even if an exception occurs.
        try (PreparedStatement query = connection.prepareStatement(sql)) {
            // Loop through the list of book IDs and set the book ID as a parameter for each deletion in the SQL query
            for (String bookId : bookIds) {
                query.setString(1, bookId);

                query.addBatch(); // Add the SQL statement to the batch for execution
            }

            int[] rowsAffected = query.executeBatch(); // Execute the batch of SQL statements and get the number of rows affected for each statement    
            // Loop through the results of the batch execution and check if any deletion failed. If any deletion fails, throw an exception for better debugging
            for (int result : rowsAffected) {
                if (result == 0) {
                    throw new SQLException("Failed to delete product from BM_Product table."); // Throw an exception for better debugging
                }
            }
        } catch (SQLException e) {
            throw new SQLException("Failed to delete books.", e); // Throw an exception for better debugging
        }
    }

    /**
     * Deletes a book from the BM_Books table by its ID. This method prepares an SQL statement to delete the book's entry from the BM_Books table based on the provided book ID. If the deletion fails, an exception is thrown to indicate the failure, along with the ID of the book that caused the failure for better debugging.
     * @param bookId
     * @return
     * @throws SQLException
     */
    private void deleteBookFromBookTable(String bookId, Connection connection) throws SQLException {
        // Using try-with-resources to automatically close the PreparedStatement and prevent memory leaks, even if an exception occurs.
        try (PreparedStatement query = connection.prepareStatement(sqlDelete)) {
            // Set the book ID as a parameter for the SQL query 
            query.setString(1, bookId);

            query.executeUpdate(); // Execute the SQL query and get the number of rows affected
        } catch (SQLException e) {
            throw new SQLException("Failed to delete book with ID: " + bookId, e); // Throw an exception with the book ID for better debugging
        }
    }

    /**
     * Deletes multiple books from the BM_Books table by their IDs. This method prepares an SQL statement to delete the entries of multiple books from the BM_Books table based on the provided list of book IDs. If any deletion fails, an exception is thrown to indicate the failure, along with the ID of the book that caused the failure for better debugging.
     * @param bookIds
     * @return
     * @throws SQLException
     */
    private void deleteBookFromBookTable(List<String> bookIds, Connection connection) throws SQLException {
        // Using try-with-resources to automatically close the PreparedStatement and prevent memory leaks, even if an exception occurs.
        try (PreparedStatement query = connection.prepareStatement(sqlDelete)) {
            // Loop through the list of book IDs and set the book ID as a parameter for each deletion in the SQL query
            for (String bookId : bookIds) {
                query.setString(1, bookId);

                query.addBatch(); // Add the SQL statement to the batch for execution
            }

            int[] rowsAffected = query.executeBatch(); // Execute the batch of SQL statements and get the number of rows affected for each statement
            // Loop through the results of the batch execution and check if any deletion failed. If any deletion fails, throw an exception for better debugging
            for (int result : rowsAffected) {
                if (result == 0) {
                    throw new SQLException("Failed to delete product from BM_Books table."); // Throw a generic exception for multiple books, as it may be difficult to identify which specific book caused the failure
                }
            }
        } catch (SQLException e) {
            throw new SQLException("Failed to delete books.", e); // Throw an exception for better debugging
        }
    }

    /**
     * Updates the details of an existing book in the BM_Product table. This method prepares an SQL statement to update the book's information in the BM_Product table based on the provided Book object. If the update fails, an exception is thrown to indicate the failure, along with the ID of the book that caused the failure for better debugging.
     * @param book
     * @return
     * @throws SQLException
     */
    private void updateBookInProductTable(Book book, Connection connection) throws SQLException {
        // Prepare the SQL statement for updating a book's details in the BM_Product table
        String sql = "UPDATE BM_Product SET name = ?, price = ?, stock_quantity = ?, category = ?, total_sales = ?, total_star_ratings = ?, number_of_ratings = ?, average_rating = ?, discount = ?, supplier_id = ? WHERE product_id = ?";
        // Using try-with-resources to automatically close the PreparedStatement and prevent memory leaks, even if an exception occurs.
        try (PreparedStatement query = connection.prepareStatement(sql)) {
            // Set the parameters for the SQL query using the book's updated details
            query.setString(1, book.getName());
            query.setDouble(2, book.getPrice());
            query.setInt(3, book.getStockQuantity());
            query.setString(4, book.getCategory());
            query.setInt(5, book.getTotalSales());
            query.setInt(6, book.getTotalStarRatings());
            query.setInt(7, book.getNumberOfRatings());
            query.setDouble(8, book.getAverageRating());
            query.setDouble(9, book.getDiscount());
            query.setString(10, book.getSupplier().getId());
            query.setString(11, book.getId());

            int rowsAffected = query.executeUpdate(); // Execute the SQL query and get the number of rows affected
            // Execute the SQL query and check if the update was successful. If the update fails, throw an exception for better debugging
            if (rowsAffected == 0) {
                throw new SQLException("Failed to update product details in database."); // Throw an exception for better debugging
            }
        } catch (SQLException e) {
            throw new SQLException("Failed to update book with ID: " + book.getId(), e); // Throw an exception with the book ID for better debugging
        }
    }

    /**
     * Updates the specific details of an existing book in the BM_Books table. This method prepares an SQL statement to update the book's specific information in the BM_Books table based on the provided Book object. If the update fails, an exception is thrown to indicate the failure, along with the ID of the book that caused the failure for better debugging.
     * @param book
     * @return
     * @throws SQLException
     */
    private void updateBookInBookTable(Book book, Connection connection) throws SQLException {
        // Using try-with-resources to automatically close the PreparedStatement and prevent memory leaks, even if an exception occurs.
        try (PreparedStatement query = connection.prepareStatement(sqlUpdate)) {
            // Set the parameters for the SQL query using the book's updated specific details
            query.setString(1, book.getAuthor().getId());
            query.setString(2, book.getPublisher());
            query.setInt(3, book.getYearPublished());
            query.setString(4, book.getLanguage());
            query.setString(5, book.getDescription());
            query.setInt(6, book.getStatus());
            query.setString(7, book.getId());

            int rowsAffected = query.executeUpdate(); // Execute the SQL query and get the number of rows affected
            // Execute the SQL query and check if the update was successful. If the update fails, throw an exception for better debugging
            if (rowsAffected == 0) {
                throw new SQLException("Failed to update book details in database."); // Throw an exception for better debugging
            }
        } catch (SQLException e) {
            throw new SQLException("Failed to update book with ID: " + book.getId(), e); // Throw an exception with the book ID for better debugging
        }
    }
}