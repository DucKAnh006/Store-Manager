package bookstoremgmt.repository.product;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import bookstoremgmt.model.product.Combo;
import bookstoremgmt.util.DatabaseConnection;

/**
 * ComboRepository class handles all database operations related to Combo entities, including adding, deleting, and updating combo in the database. It ensures that operations on both the BM_Product and BM_Combo tables are performed atomically to maintain data integrity.
 * @author Nguyen Tran Duc Anh
 */
public class ComboRepository {
    private DatabaseConnection dataConnection = new DatabaseConnection(); // Initialize the DatabaseConnection object
    private String sqlInsert = "INSERT INTO BM_Combo (product_id) VALUES (?)"; // Prepare the SQL statement for inserting a combo into the BM_Combo table
    private String sqlDelete = "DELETE FROM BM_Combo WHERE product_id = ?"; // Prepare the SQL statement for deleting a combo from the BM_Combo table by its ID

    public ComboRepository() {
    }

    /**
     * Adds a combo to the database. This method ensures that the combo is added from both the BM_Product and BM_Combo tables. If either addition fails, an exception is thrown to indicate the failure.
     * @param combo
     * @throws SQLException
     */
    public void addCombo(Combo combo) throws SQLException {
        // Use try-with-resources to ensure that the database connection is properly closed after the operation is completed, even if an exception occurs
        try (Connection connection = dataConnection.getConnection()) {
            connection.setAutoCommit(false); // Start transaction

            try {
                insertComboToProductTable(combo, connection); // Insert into BM_Product table
                insertComboToComboTable(combo, connection); // Insert into BM_Combo table
            } catch (SQLException e) {
                connection.rollback(); // Rollback transaction if any SQLException occurs during insertion
                throw e; // Rethrow the exception to be handled by the caller
            }

            connection.commit(); // Commit transaction if both insertions succeed
        } catch (SQLException e) {
            throw new SQLException("Failed to add combo with ID: " + combo.getId(), e); // Throw an exception with the combo ID for better debugging
        }
    }

    /**
     * Deletes a combo from the database by its ID. This method ensures that the combo is deleted from both the BM_Product and BM_Combo tables. If either deletion fails, an exception is thrown to indicate the failure.
     * @param comboId
     * @throws SQLException
     */
    public void deleteCombo(String comboId) throws SQLException {
        try (Connection connection = dataConnection.getConnection()) {
            connection.setAutoCommit(false); // Start transaction

            try {
                deleteComboFromComboTable(comboId, connection); // Delete from BM_Combo table
                deleteComboFromProductTable(comboId, connection); // Delete from BM_Product table
            } catch (SQLException e) {
                connection.rollback(); // Rollback transaction if any SQLException occurs during deletion
                throw e; // Rethrow the exception to be handled by the caller
            }

            connection.commit(); // Commit transaction if both deletions succeed
        } catch (SQLException e) {
            throw new SQLException("Failed to delete combo with ID: " + comboId, e); // Throw an exception with the combo ID for better debugging
        }
    }

    /**
     * Updates the details of an existing combo in the database. This method ensures that the combo's information is updated in both the BM_Product and BM_Combo tables. If either update fails, an exception is thrown to indicate the failure.
     * @param combo
     * @throws SQLException
     */
    public void updateCombo(Combo combo) throws SQLException {
        // Use try-with-resources to ensure that the database connection is properly closed after the operation is completed, even if an exception occurs
        try (Connection connection = dataConnection.getConnection()) {
            connection.setAutoCommit(false); // Start transaction
            
            try {
                updateComboInProductTable(combo, connection); // Update combo details in BM_Product table
                
            } catch (SQLException e) {
                connection.rollback(); // Rollback transaction if any SQLException occurs during update
                throw e; // Rethrow the exception to be handled by the caller
            }

            connection.commit(); // Commit transaction if both updates succeed
        } catch (SQLException e) {
            throw new SQLException("Failed to update combo with ID: " + combo.getId(), e); // Throw an exception with the combo ID for better debugging
        }
    }

    /**
     * Inserts a combo into the BM_Product table. This method prepares an SQL statement to insert the combo's details into the BM_Product table. If the insertion fails, an exception is thrown to indicate the failure.
     * @param combo
     * @return
     * @throws SQLException
     */
    private void insertComboToProductTable(Combo combo, Connection connection) throws SQLException {
        // Prepare the SQL statement for inserting a combo into the BM_Product table
        String sql = "INSERT INTO BM_Product (product_id, name, price, stock_quantity, category, status, total_sales, total_star_ratings, number_of_ratings, average_rating, discount, supplier_id) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        // Using try-with-resources to automatically close the PreparedStatement and prevent memory leaks, even if an exception occurs.
        try (PreparedStatement query = connection.prepareStatement(sql)) {
            // Set the parameters for the SQL query using the combo's details
            query.setString(1, combo.getId());
            query.setString(2, combo.getName());
            query.setDouble(3, combo.getPrice());
            query.setInt(4, combo.getStockQuantity());
            query.setString(5, combo.getCategory());
            query.setInt(6, combo.getStatus());
            query.setInt(7, combo.getTotalSales());
            query.setInt(8, combo.getTotalStarRatings());
            query.setInt(9, combo.getNumberOfRatings());
            query.setDouble(10, combo.getAverageRating());
            query.setDouble(11, combo.getDiscount());
            query.setString(12, combo.getSupplier().getId());

            int rowsAffected = query.executeUpdate(); // Execute the SQL query and get the number of rows affected
            // Execute the SQL query and check if the insertion was successful. If the insertion fails, throw an exception for better debugging
            if (rowsAffected == 0) {
                throw new SQLException("Failed to insert product details into database."); // Throw an exception for better debugging
            }
        } catch (SQLException e) {
            throw new SQLException("Failed to insert combo with ID: " + combo.getId(), e); // Throw an exception with the combo ID for better debugging
        }
    }

    /**
     * Inserts a combo into the BM_Combo table. This method prepares an SQL statement to insert the combo's specific details into the BM_Combo table. If the insertion fails, an exception is thrown to indicate the failure, along with the ID of the combo that caused the failure for better debugging.
     * @param combo
     * @return
     * @throws SQLException
     */
    private void insertComboToComboTable(Combo combo, Connection connection) throws SQLException {
        // Using try-with-resources to automatically close the PreparedStatement and prevent memory leaks, even if an exception occurs.
        try (PreparedStatement query = connection.prepareStatement(sqlInsert)) {
            // Set the parameters for the SQL query using the combo's specific details
            query.setString(1, combo.getId());
            int rowsAffected = query.executeUpdate(); // Execute the SQL query and get the number of rows affected
            // Execute the SQL query and check if the insertion was successful. If the insertion fails, throw an exception for better debugging
            if (rowsAffected == 0) {
                throw new SQLException("Failed to insert combo details into database."); // Throw an exception for better debugging
            }
        } catch (SQLException e) {
            throw new SQLException("Failed to insert combo with ID: " + combo.getId(), e); // Throw an exception with the combo ID for better debugging
        }
    }

    /**
     * Deletes a combo from the BM_Product table by its ID. This method prepares an SQL statement to delete the combo's entry from the BM_Product table based on the provided combo ID. If the deletion fails, an exception is thrown to indicate the failure, along with the ID of the combo that caused the failure for better debugging.
     * @param comboId
     * @return
     * @throws SQLException
     */
    private void deleteComboFromProductTable(String comboId, Connection connection) throws SQLException {
        // Prepare the SQL statement for deleting a combo from the BM_Product table by its ID
        String sql = "DELETE FROM BM_Product WHERE product_id = ?";
        // Using try-with-resources to automatically close the PreparedStatement and prevent memory leaks, even if an exception occurs.
        try (PreparedStatement query = connection.prepareStatement(sql)) {
            query.setString(1, comboId); // Set the combo ID as a parameter for the SQL query
            query.executeUpdate(); // Execute the SQL query and get the number of rows affected
        } catch (SQLException e) {
            throw new SQLException("Failed to delete combo with ID: " + comboId, e); // Throw an exception with the combo ID for better debugging
        }
    }

    /**
     * Deletes a combo from the BM_Combo table by its ID. This method prepares an SQL statement to delete the combo's entry from the BM_Combo table based on the provided combo ID. If the deletion fails, an exception is thrown to indicate the failure, along with the ID of the combo that caused the failure for better debugging.
     * @param comboId
     * @return
     * @throws SQLException
     */
    private void deleteComboFromComboTable(String comboId, Connection connection) throws SQLException {
        // Using try-with-resources to automatically close the PreparedStatement and prevent memory leaks, even if an exception occurs.
        try (PreparedStatement query = connection.prepareStatement(sqlDelete)) {
            // Set the combo ID as a parameter for the SQL query 
            query.setString(1, comboId);

            query.executeUpdate(); // Execute the SQL query and get the number of rows affected
        } catch (SQLException e) {
            throw new SQLException("Failed to delete combo with ID: " + comboId, e); // Throw an exception with the combo ID for better debugging
        }
    }

    /**
     * Updates the details of an existing combo in the BM_Product table. This method prepares an SQL statement to update the combo's information in the BM_Product table based on the provided combo object. If the update fails, an exception is thrown to indicate the failure, along with the ID of the combo that caused the failure for better debugging.
     * @param combo
     * @return
     * @throws SQLException
     */
    private void updateComboInProductTable(Combo combo, Connection connection) throws SQLException {
        // Prepare the SQL statement for updating a combo's details in the BM_Product table
        String sql = "UPDATE BM_Product SET name = ?, price = ?, stock_quantity = ?, category = ?, status = ?, total_sales = ?, total_star_ratings = ?, number_of_ratings = ?, average_rating = ?, discount = ?, supplier_id = ? WHERE product_id = ?";
        // Using try-with-resources to automatically close the PreparedStatement and prevent memory leaks, even if an exception occurs.
        try (PreparedStatement query = connection.prepareStatement(sql)) {
            // Set the parameters for the SQL query using the combo's updated details
            query.setString(1, combo.getName());
            query.setDouble(2, combo.getPrice());
            query.setInt(3, combo.getStockQuantity());
            query.setString(4, combo.getCategory());
            query.setInt(5, combo.getStatus());
            query.setInt(6, combo.getTotalSales());
            query.setInt(7, combo.getTotalStarRatings());
            query.setInt(8, combo.getNumberOfRatings());
            query.setDouble(9, combo.getAverageRating());
            query.setDouble(10, combo.getDiscount());
            query.setString(11, combo.getSupplier().getId());
            query.setString(12, combo.getId());

            int rowsAffected = query.executeUpdate(); // Execute the SQL query and get the number of rows affected
            // Execute the SQL query and check if the update was successful. If the update fails, throw an exception for better debugging
            if (rowsAffected == 0) {
                throw new SQLException("Failed to update product details in database."); // Throw an exception for better debugging
            }
        } catch (SQLException e) {
            throw new SQLException("Failed to update combo with ID: " + combo.getId(), e); // Throw an exception with the combo ID for better debugging
        }
    }

}