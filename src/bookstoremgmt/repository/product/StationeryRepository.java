package bookstoremgmt.repository.product;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

import bookstoremgmt.model.product.Stationery;
import bookstoremgmt.util.DatabaseConnection;

/**
 * StationeryRepository class handles all database operations related to Stationery entities, including adding, deleting, and updating stationery in the database. It ensures that operations on both the BM_Product and BM_Stationery tables are performed atomically to maintain data integrity.
 * @author Nguyen Tran Duc Anh
 */
public class StationeryRepository {
    private DatabaseConnection dataConnection = new DatabaseConnection(); // Initialize the DatabaseConnection object
    private String sqlInsert = "INSERT INTO BM_Stationery (product_id, manufacturer, material) VALUES (?, ?, ?)"; // Prepare the SQL statement for inserting a stationery into the BM_Stationery table
    private String sqlDelete = "DELETE FROM BM_Stationery WHERE product_id = ?"; // Prepare the SQL statement for deleting a stationery from the BM_Stationery table by its ID
    private String sqlUpdate = "UPDATE BM_Stationery SET manufacturer = ?, material = ? WHERE product_id = ?"; // Prepare the SQL statement for updating a stationery's specific details in the BM_Stationery table

    public StationeryRepository() {
    }

    /**
     * Closes the database connection if it is open.
     * @param stationery
     * @throws SQLException
     */
    public void addStationery(Stationery stationery) throws SQLException {
        // Use try-with-resources to ensure that the database connection is properly closed after the operation is completed, even if an exception occurs
        try (Connection connection = dataConnection.getConnection()) {
            connection.setAutoCommit(false); // Start transaction

            try {
                insertStationeryToProductTable(stationery, connection); // Insert into BM_Product table
                insertStationeryToStationeryTable(stationery, connection); // Insert into BM_Stationery table
            } catch (SQLException e) {
                connection.rollback(); // Rollback transaction if any SQLException occurs during insertion
                throw e; // Rethrow the exception to be handled by the caller
            }

            connection.commit(); // Commit transaction if both insertions succeed
        } catch (SQLException e) {
            throw new SQLException("Failed to add stationery with ID: " + stationery.getId(), e); // Throw an exception with the stationery ID for better debugging
        }
    }

    /**
     * Adds multiple stationery to the database in a single transaction. If any insertion fails, the entire transaction is rolled back to maintain data integrity.
     * @param stationery
     * @throws SQLException
     */
    public void addStationerys(List<Stationery> stationery) throws SQLException {
        // Use try-with-resources to ensure that the database connection is properly closed after the operation is completed, even if an exception occurs
        try (Connection connection = dataConnection.getConnection()) {
            connection.setAutoCommit(false); // Start transaction

            try {
                insertStationeryToProductTable(stationery, connection); // Insert multiple stationery into BM_Product table
                insertStationeryToStationeryTable(stationery, connection); // Insert multiple stationery into BM_Stationery table
            } catch (SQLException e) {
                connection.rollback(); // Rollback transaction if any SQLException occurs during insertion
                throw e; // Rethrow the exception to be handled by the caller
            }

            connection.commit(); // Commit transaction if both insertions succeed
        } catch (SQLException e) {
            throw new SQLException("Failed to add stationery.", e); // Throw a generic exception for multiple stationery, as it may be difficult to identify which specific stationery caused the failure
        }
    }

    /**
     * Deletes a stationery from the database by its ID. This method ensures that the stationery is deleted from both the BM_Product and BM_Stationery tables. If either deletion fails, an exception is thrown to indicate the failure.
     * @param stationeryId
     * @throws SQLException
     */
    public void deleteStationery(String stationeryId) throws SQLException {
        try (Connection connection = dataConnection.getConnection()) {
            connection.setAutoCommit(false); // Start transaction

            try {
                deleteStationeryFromStationeryTable(stationeryId, connection); // Delete from BM_Stationery table
                deleteStationeryFromProductTable(stationeryId, connection); // Delete from BM_Product table
            } catch (SQLException e) {
                connection.rollback(); // Rollback transaction if any SQLException occurs during deletion
                throw e; // Rethrow the exception to be handled by the caller
            }

            connection.commit(); // Commit transaction if both deletions succeed
        } catch (SQLException e) {
            throw new SQLException("Failed to delete stationery with ID: " + stationeryId, e); // Throw an exception with the stationery ID for better debugging
        }
    }
 
    /**
     * Deletes multiple stationery from the database by their IDs. This method ensures that all specified stationery are deleted from both the BM_Product and BM_Stationery tables. If any deletion fails, an exception is thrown to indicate the failure.
     * @param stationeryIds
     * @throws SQLException
     */
    public void deletestationery(List<String> stationeryIds) throws SQLException { 
        // Use try-with-resources to ensure that the database connection is properly closed after the operation is completed, even if an exception occurs
        try (Connection connection = dataConnection.getConnection()) {
            connection.setAutoCommit(false); // Start transaction

            try {
                deleteStationeryFromStationeryTable(stationeryIds, connection); // Delete multiple stationery from BM_Stationery table
                deleteStationeryFromProductTable(stationeryIds, connection); // Delete multiple stationery from BM_Product table
            } catch (SQLException e) {
                connection.rollback(); // Rollback transaction if any SQLException occurs during deletion
                throw e; // Rethrow the exception to be handled by the caller
            }

            connection.commit(); // Commit transaction if both deletions succeed
        } catch (SQLException e) {
            throw new SQLException("Failed to delete stationery.", e); // Throw an exception with the stationery ID for better debugging
        }
    }

    /**
     * Updates the details of an existing stationery in the database. This method ensures that the stationery's information is updated in both the BM_Product and BM_Stationery tables. If either update fails, an exception is thrown to indicate the failure.
     * @param stationery
     * @throws SQLException
     */
    public void updateStationery(Stationery stationery) throws SQLException {
        // Use try-with-resources to ensure that the database connection is properly closed after the operation is completed, even if an exception occurs
        try (Connection connection = dataConnection.getConnection()) {
            connection.setAutoCommit(false); // Start transaction
            
            try {
                updateStationeryInProductTable(stationery, connection); // Update stationery details in BM_Product table
                updateStationeryInStationeryTable(stationery, connection); // Update stationery details in BM_Stationery table
            } catch (SQLException e) {
                connection.rollback(); // Rollback transaction if any SQLException occurs during update
                throw e; // Rethrow the exception to be handled by the caller
            }

            connection.commit(); // Commit transaction if both updates succeed
        } catch (SQLException e) {
            throw new SQLException("Failed to update stationery with ID: " + stationery.getId(), e); // Throw an exception with the stationery ID for better debugging
        }
    }

    /**
     * Inserts a stationery into the BM_Product table. This method prepares an SQL statement to insert the stationery's details into the BM_Product table. If the insertion fails, an exception is thrown to indicate the failure.
     * @param stationery
     * @return
     * @throws SQLException
     */
    private void insertStationeryToProductTable(Stationery stationery, Connection connection) throws SQLException {
        // Prepare the SQL statement for inserting a stationery into the BM_Product table
        String sql = "INSERT INTO BM_Product (product_id, name, price, stock_quantity, category, status, total_sales, total_star_ratings, number_of_ratings, average_rating, discount, supplier_id) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        // Using try-with-resources to automatically close the PreparedStatement and prevent memory leaks, even if an exception occurs.
        try (PreparedStatement query = connection.prepareStatement(sql)) {
            // Set the parameters for the SQL query using the stationery's details
            query.setString(1, stationery.getId());
            query.setString(2, stationery.getName());
            query.setDouble(3, stationery.getPrice());
            query.setInt(4, stationery.getStockQuantity());
            query.setString(5, stationery.getCategory());
            query.setInt(6, stationery.getStatus());
            query.setInt(7, stationery.getTotalSales());
            query.setInt(8, stationery.getTotalStarRatings());
            query.setInt(9, stationery.getNumberOfRatings());
            query.setDouble(10, stationery.getAverageRating());
            query.setDouble(11, stationery.getDiscount());
            query.setString(12, stationery.getSupplier().getId());

            int rowsAffected = query.executeUpdate(); // Execute the SQL query and get the number of rows affected
            // Execute the SQL query and check if the insertion was successful. If the insertion fails, throw an exception with the stationery ID for better debugging
            if (rowsAffected == 0) {
                throw new SQLException("Failed to insert product details into database."); // Throw an exception with the stationery ID for better debugging
            }
        } catch (SQLException e) {
            throw new SQLException("Failed to insert stationery with ID: " + stationery.getId(), e); // Throw an exception with the stationery ID for better debugging
        }
    }

    /**
     * Inserts multiple stationery into the BM_Product table. This method prepares an SQL statement to insert the details of each stationery in the provided list into the BM_Product table. If any insertion fails, an exception is thrown to indicate the failure, along with the ID of the stationery that caused the failure for better debugging.
     * @param stationery
     * @return
     * @throws SQLException
     */
    private void insertStationeryToProductTable(List<Stationery> stationery, Connection connection) throws SQLException {
        // Prepare the SQL statement for inserting multiple stationery into the BM_Product table
        String sql = "INSERT INTO BM_Product (product_id, name, price, stock_quantity, category, status, total_sales, total_star_ratings, number_of_ratings, average_rating, discount, supplier_id) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        // Using try-with-resources to automatically close the PreparedStatement and prevent memory leaks, even if an exception occurs.
        try (PreparedStatement query = connection.prepareStatement(sql)) {
            // Loop through the list of stationery and set the parameters for each stationery's details in the SQL query
            for (Stationery str : stationery) {
                query.setString(1, str.getId());
                query.setString(2, str.getName());
                query.setDouble(3, str.getPrice());
                query.setInt(4, str.getStockQuantity());
                query.setString(5, str.getCategory());
                query.setInt(6, str.getStatus());
                query.setInt(7, str.getTotalSales());
                query.setInt(8, str.getTotalStarRatings());
                query.setInt(9, str.getNumberOfRatings());
                query.setDouble(10, str.getAverageRating());
                query.setDouble(11, str.getDiscount());
                query.setString(12, str.getSupplier().getId());

                query.addBatch(); // Add the SQL statement to the batch for execution
            }

            int[] rowsAffected = query.executeBatch(); // Execute the batch of SQL statements and get the number of rows affected for each statement
            // Loop through the results of the batch execution and check if any insertion failed. If any
            for (int result : rowsAffected) {
                if (result == 0) {
                    throw new SQLException("Failed to insert product details into database."); // Throw an exception with the stationery ID for better debugging
                }
            }
        } catch (SQLException e) {
            throw new SQLException("Failed to insert stationery.", e); // Throw an exception with the stationery ID for better debugging
        }
    }

    /**
     * Inserts a stationery into the BM_Stationery table. This method prepares an SQL statement to insert the stationery's specific details into the BM_Stationery table. If the insertion fails, an exception is thrown to indicate the failure, along with the ID of the stationery that caused the failure for better debugging.
     * @param stationery
     * @return
     * @throws SQLException
     */
    private void insertStationeryToStationeryTable(Stationery stationery, Connection connection) throws SQLException {
        // Using try-with-resources to automatically close the PreparedStatement and prevent memory leaks, even if an exception occurs.
        try (PreparedStatement query = connection.prepareStatement(sqlInsert)) {
            // Set the parameters for the SQL query using the stationery's specific details
            query.setString(1, stationery.getId());
            query.setString(2, stationery.getManufacturer());
            query.setString(3, stationery.getMaterial());
            int rowsAffected = query.executeUpdate(); // Execute the SQL query and get the number of rows affected
            // Execute the SQL query and check if the insertion was successful. If the insertion fails, throw an exception with the stationery ID for better debugging
            if (rowsAffected == 0) {
                throw new SQLException("Failed to insert stationery details into database."); // Throw an exception with the stationery ID for better debugging
            }
        } catch (SQLException e) {
            throw new SQLException("Failed to insert stationery with ID: " + stationery.getId(), e); // Throw an exception with the stationery ID for better debugging
        }
    }

    /**
     * Inserts multiple stationery into the BM_Stationery table. This method prepares an SQL statement to insert the specific details of each stationery in the provided list into the BM_Stationery table. If any insertion fails, an exception is thrown to indicate the failure, along with the ID of the stationery that caused the failure for better debugging.
     * @param stationery
     * @return
     * @throws SQLException
     */
    private void insertStationeryToStationeryTable(List<Stationery> stationery, Connection connection) throws SQLException {
        // Using try-with-resources to automatically close the PreparedStatement and prevent memory leaks, even if an exception occurs.
        try (PreparedStatement query = connection.prepareStatement(sqlInsert)) {
            // Loop through the list of stationery and set the parameters for each stationery's specific details in the SQL query
            for (Stationery str : stationery) {
                query.setString(1, str.getId());
            query.setString(2, str.getManufacturer());
            query.setString(3, str.getMaterial());

                query.addBatch(); // Add the SQL statement to the batch for execution
            }

            int[] rowsAffected = query.executeBatch(); // Execute the batch of SQL statements and get the number of rows affected for each statement
            // Loop through the results of the batch execution and check if any insertion failed. If any insertion fails, throw an exception with the stationery ID for better debugging
            for (int result : rowsAffected) {
                if (result == 0) {
                    throw new SQLException("Failed to insert product details into database."); // Throw an exception with the stationery ID for better debugging
                }
            }
        } catch (SQLException e) {
            throw new SQLException("Failed to insert stationery.", e); // Throw an exception with the stationery ID for better debugging
        }
    }

    /**
     * Deletes a stationery from the BM_Product table by its ID. This method prepares an SQL statement to delete the stationery's entry from the BM_Product table based on the provided stationery ID. If the deletion fails, an exception is thrown to indicate the failure, along with the ID of the stationery that caused the failure for better debugging.
     * @param stationeryId
     * @return
     * @throws SQLException
     */
    private void deleteStationeryFromProductTable(String stationeryId, Connection connection) throws SQLException {
        // Prepare the SQL statement for deleting a stationery from the BM_Product table by its ID
        String sql = "DELETE FROM BM_Product WHERE product_id = ?";
        // Using try-with-resources to automatically close the PreparedStatement and prevent memory leaks, even if an exception occurs.
        try (PreparedStatement query = connection.prepareStatement(sql)) {
            query.setString(1, stationeryId); // Set the stationery ID as a parameter for the SQL query
            query.executeUpdate(); // Execute the SQL query and get the number of rows affected
        } catch (SQLException e) {
            throw new SQLException("Failed to delete stationery with ID: " + stationeryId, e); // Throw an exception with the stationery ID for better debugging
        }
    }

    /**
     * Deletes multiple stationery from the BM_Product table by their IDs. This method prepares an SQL statement to delete the entries of multiple stationery from the BM_Product table based on the provided list of stationery IDs. If any deletion fails, an exception is thrown to indicate the failure, along with the ID of the stationery that caused the failure for better debugging.
     * @param stationeryIds
     * @return
     * @throws SQLException
     */
    private void deleteStationeryFromProductTable(List<String> stationeryIds, Connection connection) throws SQLException {
        // Prepare the SQL statement for deleting multiple stationery from the BM_Product table by their IDs
        String sql = "DELETE FROM BM_Product WHERE product_id = ?";
       // Using try-with-resources to automatically close the PreparedStatement and prevent memory leaks, even if an exception occurs.
        try (PreparedStatement query = connection.prepareStatement(sql)) {
            // Loop through the list of stationery IDs and set the stationery ID as a parameter for each deletion in the SQL query
            for (String stationeryId : stationeryIds) {
                query.setString(1, stationeryId);

                query.addBatch(); // Add the SQL statement to the batch for execution
            }

            int[] rowsAffected = query.executeBatch(); // Execute the batch of SQL statements and get the number of rows affected for each statement    
            // Loop through the results of the batch execution and check if any deletion failed. If any deletion fails, throw an exception with the stationery ID for better debugging
            for (int result : rowsAffected) {
                if (result == 0) {
                    throw new SQLException("Failed to delete product from BM_Product table."); // Throw an exception with the stationery ID for better debugging
                }
            }
        } catch (SQLException e) {
            throw new SQLException("Failed to delete stationery.", e); // Throw an exception with the stationery ID for better debugging
        }
    }

    /**
     * Deletes a stationery from the BM_Stationery table by its ID. This method prepares an SQL statement to delete the stationery's entry from the BM_Stationery table based on the provided stationery ID. If the deletion fails, an exception is thrown to indicate the failure, along with the ID of the stationery that caused the failure for better debugging.
     * @param stationeryId
     * @return
     * @throws SQLException
     */
    private void deleteStationeryFromStationeryTable(String stationeryId, Connection connection) throws SQLException {
        // Using try-with-resources to automatically close the PreparedStatement and prevent memory leaks, even if an exception occurs.
        try (PreparedStatement query = connection.prepareStatement(sqlDelete)) {
            // Set the stationery ID as a parameter for the SQL query 
            query.setString(1, stationeryId);

            query.executeUpdate(); // Execute the SQL query and get the number of rows affected
        } catch (SQLException e) {
            throw new SQLException("Failed to delete stationery with ID: " + stationeryId, e); // Throw an exception with the stationery ID for better debugging
        }
    }

    /**
     * Deletes multiple stationery from the BM_Stationery table by their IDs. This method prepares an SQL statement to delete the entries of multiple stationery from the BM_Stationery table based on the provided list of stationery IDs. If any deletion fails, an exception is thrown to indicate the failure, along with the ID of the stationery that caused the failure for better debugging.
     * @param stationeryIds
     * @return
     * @throws SQLException
     */
    private void deleteStationeryFromStationeryTable(List<String> stationeryIds, Connection connection) throws SQLException {
        // Using try-with-resources to automatically close the PreparedStatement and prevent memory leaks, even if an exception occurs.
        try (PreparedStatement query = connection.prepareStatement(sqlDelete)) {
            // Loop through the list of stationery IDs and set the stationery ID as a parameter for each deletion in the SQL query
            for (String stationeryId : stationeryIds) {
                query.setString(1, stationeryId);

                query.addBatch(); // Add the SQL statement to the batch for execution
            }

            int[] rowsAffected = query.executeBatch(); // Execute the batch of SQL statements and get the number of rows affected for each statement
            // Loop through the results of the batch execution and check if any deletion failed. If any deletion fails, throw an exception with the stationery ID for better debugging
            for (int result : rowsAffected) {
                if (result == 0) {
                    throw new SQLException("Failed to delete product from BM_Stationery table."); // Throw a generic exception for multiple stationery, as it may be difficult to identify which specific stationery caused the failure
                }
            }
        } catch (SQLException e) {
            throw new SQLException("Failed to delete stationery.", e); // Throw an exception with the stationery ID for better debugging
        }
    }

    /**
     * Updates the details of an existing stationery in the BM_Product table. This method prepares an SQL statement to update the stationery's information in the BM_Product table based on the provided stationery object. If the update fails, an exception is thrown to indicate the failure, along with the ID of the stationery that caused the failure for better debugging.
     * @param stationery
     * @return
     * @throws SQLException
     */
    private void updateStationeryInProductTable(Stationery stationery, Connection connection) throws SQLException {
        // Prepare the SQL statement for updating a stationery's details in the BM_Product table
        String sql = "UPDATE BM_Product SET name = ?, price = ?, stock_quantity = ?, category = ?, status = ?, total_sales = ?, total_star_ratings = ?, number_of_ratings = ?, average_rating = ?, discount = ?, supplier_id = ? WHERE product_id = ?";
        // Using try-with-resources to automatically close the PreparedStatement and prevent memory leaks, even if an exception occurs.
        try (PreparedStatement query = connection.prepareStatement(sql)) {
            // Set the parameters for the SQL query using the stationery's updated details
            query.setString(1, stationery.getName());
            query.setDouble(2, stationery.getPrice());
            query.setInt(3, stationery.getStockQuantity());
            query.setString(4, stationery.getCategory());
            query.setInt(5, stationery.getStatus());
            query.setInt(6, stationery.getTotalSales());
            query.setInt(7, stationery.getTotalStarRatings());
            query.setInt(8, stationery.getNumberOfRatings());
            query.setDouble(9, stationery.getAverageRating());
            query.setDouble(10, stationery.getDiscount());
            query.setString(11, stationery.getSupplier().getId());
            query.setString(12, stationery.getId());

            int rowsAffected = query.executeUpdate(); // Execute the SQL query and get the number of rows affected
            // Execute the SQL query and check if the update was successful. If the update fails, throw an exception with the stationery ID for better debugging
            if (rowsAffected == 0) {
                throw new SQLException("Failed to update product details in database."); // Throw an exception with the stationery ID for better debugging
            }
        } catch (SQLException e) {
            throw new SQLException("Failed to update stationery with ID: " + stationery.getId(), e); // Throw an exception with the stationery ID for better debugging
        }
    }

    /**
     * Updates the specific details of an existing stationery in the BM_Stationery table. This method prepares an SQL statement to update the stationery's specific information in the BM_Stationery table based on the provided stationery object. If the update fails, an exception is thrown to indicate the failure, along with the ID of the stationery that caused the failure for better debugging.
     * @param stationery
     * @return
     * @throws SQLException
     */
    private void updateStationeryInStationeryTable(Stationery stationery, Connection connection) throws SQLException {
        // Using try-with-resources to automatically close the PreparedStatement and prevent memory leaks, even if an exception occurs.
        try (PreparedStatement query = connection.prepareStatement(sqlUpdate)) {
            // Set the parameters for the SQL query using the stationery's updated specific details
            query.setString(1, stationery.getManufacturer());
            query.setString(2, stationery.getMaterial());
            query.setString(3, stationery.getId());

            int rowsAffected = query.executeUpdate(); // Execute the SQL query and get the number of rows affected
            // Execute the SQL query and check if the update was successful. If the update fails, throw an exception with the stationery ID for better debugging
            if (rowsAffected == 0) {
                throw new SQLException("Failed to update stationery details in database."); // Throw an exception with the stationery ID for better debugging
            }
        } catch (SQLException e) {
            throw new SQLException("Failed to update stationery with ID: " + stationery.getId(), e); // Throw an exception with the stationery ID for better debugging
        }
    }
}