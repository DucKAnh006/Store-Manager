package bookstoremgmt.repository.product;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

import bookstoremgmt.model.product.Book;
import bookstoremgmt.model.product.Product;

public class ProductRepository {
    /**
     * Inserts a book into the BM_Product table. This method prepares an SQL statement to insert the book's details into the BM_Product table. If the insertion fails, an exception is thrown to indicate the failure.
     * @param book
     * @return
     * @throws SQLException
     */
    private void insertBookToProductTable(Product book, Connection connection) throws SQLException {
        // Prepare the SQL statement for inserting a book into the BM_Product table
        String sql = "INSERT INTO BM_Product (product_id, name, price, stock_quantity, category, status, total_sales, total_star_ratings, number_of_ratings, average_rating, discount, supplier_id) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        // Using try-with-resources to automatically close the PreparedStatement and prevent memory leaks, even if an exception occurs.
        try (PreparedStatement query = connection.prepareStatement(sql)) {
            // Set the parameters for the SQL query using the book's details
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

            int rowsAffected = query.executeUpdate(); // Execute the SQL query and get the number of rows affected
            // Execute the SQL query and check if the insertion was successful. If the insertion fails, throw an exception with the book ID for better debugging
            if (rowsAffected == 0) {
                throw new SQLException("Failed to insert product details into database."); // Throw an exception with the book ID for better debugging
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
        String sql = "INSERT INTO BM_Product (product_id, name, price, stock_quantity, category, status, total_sales, total_star_ratings, number_of_ratings, average_rating, discount, supplier_id) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        // Using try-with-resources to automatically close the PreparedStatement and prevent memory leaks, even if an exception occurs.
        try (PreparedStatement query = connection.prepareStatement(sql)) {
            // Loop through the list of books and set the parameters for each book's details in the SQL query
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

                query.addBatch(); // Add the SQL statement to the batch for execution
            }

            int[] rowsAffected = query.executeBatch(); // Execute the batch of SQL statements and get the number of rows affected for each statement
            // Loop through the results of the batch execution and check if any insertion failed. If any
            for (int result : rowsAffected) {
                if (result == 0) {
                    throw new SQLException("Failed to insert product details into database."); // Throw an exception with the book ID for better debugging
                }
            }
        } catch (SQLException e) {
            throw new SQLException("Failed to insert books.", e); // Throw an exception with the book ID for better debugging
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
            // Loop through the results of the batch execution and check if any deletion failed. If any deletion fails, throw an exception with the book ID for better debugging
            for (int result : rowsAffected) {
                if (result == 0) {
                    throw new SQLException("Failed to delete product from BM_Product table."); // Throw an exception with the book ID for better debugging
                }
            }
        } catch (SQLException e) {
            throw new SQLException("Failed to delete books.", e); // Throw an exception with the book ID for better debugging
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
        String sql = "UPDATE BM_Product SET name = ?, price = ?, stock_quantity = ?, category = ?, status = ?, total_sales = ?, total_star_ratings = ?, number_of_ratings = ?, average_rating = ?, discount = ?, supplier_id = ? WHERE product_id = ?";
        // Using try-with-resources to automatically close the PreparedStatement and prevent memory leaks, even if an exception occurs.
        try (PreparedStatement query = connection.prepareStatement(sql)) {
            // Set the parameters for the SQL query using the book's updated details
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

            int rowsAffected = query.executeUpdate(); // Execute the SQL query and get the number of rows affected
            // Execute the SQL query and check if the update was successful. If the update fails, throw an exception with the book ID for better debugging
            if (rowsAffected == 0) {
                throw new SQLException("Failed to update product details in database."); // Throw an exception with the book ID for better debugging
            }
        } catch (SQLException e) {
            throw new SQLException("Failed to update book with ID: " + book.getId(), e); // Throw an exception with the book ID for better debugging
        }
    }

}
