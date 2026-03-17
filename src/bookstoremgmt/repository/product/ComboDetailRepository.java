package bookstoremgmt.repository.product;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import bookstoremgmt.util.DatabaseConnection;

/**
 * ComboDetailRepository class handles all database operations related to ComboDetail entities.
 * @author Nguyen Tran Duc Anh
 */
public class ComboDetailRepository {
    private DatabaseConnection dataConnection = new DatabaseConnection(); // Initialize the DatabaseConnection object
    private String sqlInsert = "INSERT INTO BM_ComboDetail (quantity, combo_product_id, combo_id) VALUES (?, ?, ?)"; // Prepare the SQL statement for inserting a combo detail into the BM_ComboDetail table
    private String sqlDelete = "DELETE FROM BM_ComboDetail WHERE combo_product_id = ? AND combo_id = ?"; // Prepare the SQL statement for deleting a combo detail from the BM_ComboDetail table by its ID
    private String sqlUpdate = "UPDATE BM_ComboDetail SET quantity = ? WHERE combo_product_id = ? AND combo_id = ?"; // Prepare the SQL statement for updating a combo detail's specific details in the BM_ComboDetail table

    public ComboDetailRepository() {
    }

    /**
     * Adds a combo detail to the database. This method prepares an SQL statement to insert the combo detail's specific details into the BM_ComboDetail table. If the insertion fails, an exception is thrown to indicate the failure.
     * @param quantity
     * @param productId
     * @param comboId
     * @throws SQLException
     */
    public void addComboDetail(int quantity, String productId, String comboId) throws SQLException {
        // Use try-with-resources to ensure that the database connection is properly closed after the operation is completed, even if an exception occurs
        try (Connection connection = dataConnection.getConnection()) {
            connection.setAutoCommit(false); // Start transaction

            // Using try-with-resources to automatically close the PreparedStatement and prevent memory leaks, even if an exception occurs.
            try (PreparedStatement query = connection.prepareStatement(sqlInsert)) {
                // Set the parameters for the SQL query using the combo detail's details
                query.setInt(1, quantity);
                query.setString(2, productId);
                query.setString(3, comboId);

                int rowsAffected = query.executeUpdate(); // Execute the SQL query and get the number of rows affected
                // Execute the SQL query and check if the insertion was successful. If the insertion fails, throw an exception for better debugging
                if (rowsAffected == 0) {
                    throw new SQLException("Failed to insert product's details into combo."); // Throw an exception for better debugging
                }
            } catch (SQLException e) {
                connection.rollback(); // Rollback transaction if any SQLException occurs during insertion
                throw new SQLException("Failed to insert product's details into combo.", e);
            }

            connection.commit(); // Commit transaction if the insertion succeeds
        } catch (SQLException e) {
            throw new SQLException("Failed to insert product's into combo.", e); // Throw an exception for better debugging
        }
    }

    /**
     * Deletes a combo detail from the database by its ID. This method prepares an SQL statement to delete the combo detail's entry from the BM_ComboDetail table based on the provided IDs. If the deletion fails, an exception is thrown to indicate the failure.
     * @param productId
     * @param comboId
     * @throws SQLException
     */
    public void deleteComboDetail(String productId, String comboId) throws SQLException {
        // Use try-with-resources to ensure that the database connection is properly closed after the operation is completed, even if an exception occurs
        try (Connection connection = dataConnection.getConnection()) {
            connection.setAutoCommit(false); // Start transaction

            // Using try-with-resources to automatically close the PreparedStatement and prevent memory leaks, even if an exception occurs.
            try (PreparedStatement query = connection.prepareStatement(sqlDelete)) {
                // Set the parameters for the SQL query
                query.setString(1, productId);
                query.setString(2, comboId);
                
                query.executeUpdate(); // Execute the SQL query
            } catch (SQLException e) {
                connection.rollback(); // Rollback transaction if any SQLException occurs during deletion
                throw new SQLException("Failed to delete product's details into combo.", e); // Throw an exception for better debugging
            }
            
            connection.commit(); // Commit transaction if the deletion succeeds
        } catch (SQLException e) {
            throw new SQLException("Failed to delete product details into combo.", e); // Throw an exception for better debugging
        }
    }

    /**
     * Updates the specific details of an existing combo detail in the database. This method prepares an SQL statement to update the combo detail's specific information in the BM_ComboDetail table. If the update fails, an exception is thrown to indicate the failure.
     * @param quantity
     * @param productId
     * @param comboId
     * @throws SQLException
     */
    public void updateComboDetail(int quantity, String productId, String comboId) throws SQLException {
        // Use try-with-resources to ensure that the database connection is properly closed after the operation is completed, even if an exception occurs
        try (Connection connection = dataConnection.getConnection()) {
            connection.setAutoCommit(false); // Start transaction

            // Using try-with-resources to automatically close the PreparedStatement and prevent memory leaks, even if an exception occurs.
            try (PreparedStatement query = connection.prepareStatement(sqlUpdate)) {
                // Set the parameters for the SQL query using the combo detail's updated specific details
                query.setInt(1, quantity);
                query.setString(2, productId);
                query.setString(3, comboId);
                
                int rowsAffected = query.executeUpdate(); // Execute the SQL query and get the number of rows affected
                // Execute the SQL query and check if the insertion was successful. If the insertion fails, throw an exception for better debugging
                if (rowsAffected == 0) {
                    throw new SQLException("Failed to update product's details into combo."); // Throw an exception for better debugging
                }
            } catch (SQLException e) {
                connection.rollback(); // Rollback transaction if any SQLException occurs during update
                throw new SQLException("Failed to update product's quantity in combo.", e); // Throw an exception for better debugging
            }

            connection.commit(); // Commit transaction if the update succeeds
        } catch (SQLException e) {
            throw new SQLException("Failed to update product's quantity in combo.", e); // Throw an exception for better debugging
        }
    }
}