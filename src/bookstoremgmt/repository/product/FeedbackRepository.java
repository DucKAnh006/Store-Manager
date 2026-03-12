package bookstoremgmt.repository.product;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Date;

import bookstoremgmt.model.product.Feedback;
import bookstoremgmt.util.DatabaseConnection;

/**
 * FeedbackRepository class handles all database operations related to Feedback entities.
 * @author Nguyen Tran Duc Anh
 */
public class FeedbackRepository {
    private DatabaseConnection dataConnection = new DatabaseConnection();
    private String sqlInsert = "INSERT INTO BM_Feedback (feedback_id , product_id, customer_id, rating, comment_text, comment_date) VALUES (?, ?, ?, ?, ?, ?)"; // Prepare the SQL statement for inserting a feedback into the BM_Feedback table
    private String sqlDelete = "DELETE FROM BM_Feedback WHERE feedback_id = ?"; // Prepare the SQL statement for deleting a feedback from the BM_Feedback table by its ID
    private String sqlUpdate = "UPDATE BM_Feedback SET comment = ?, rating = ?, date = ? WHERE feedback_id = ?"; // Prepare the SQL statement for updating a feedback's specific details in the BM_Feedback table

    public FeedbackRepository() {

    }

    /**
     * Adds a new feedback to the database.
     * @param feedback The feedback object containing the feedback details to be added.
     * @throws SQLException if a database access error occurs or the SQL statement is invalid.
     */
    public void addFeedback(Feedback feedback) throws SQLException {
        // Use try-with-resources to ensure that the database connection is properly closed after the operation is completed, even if an exception occurs
        try (Connection connection = dataConnection.getConnection()) {
            connection.setAutoCommit(false);// Start transaction

            // Set the parameters for the SQL query using the feedback's details
            try (PreparedStatement query = connection.prepareStatement(sqlInsert)){
                Date sqlDate = Date.valueOf(feedback.getDate());
                query.setString(1, feedback.getFeedbackId());
                query.setString(2, feedback.getProductId());
                query.setString(3, feedback.getCustomerId());
                query.setInt(4, feedback.getRating());
                query.setString(5, feedback.getComment());
                query.setDate(6, sqlDate);

                int rowsAffected = query.executeUpdate(); // Execute the SQL query and get the number of rows affected
                // Execute the SQL query and check if the insertion was successful. If the insertion fails, throw an exception with the book ID for better debugging
                if (rowsAffected == 0) {
                    throw new SQLException("Failed to insert feedback details into database."); // Throw an exception with the book ID for better debugging
                }
            } catch (SQLException e) {
                throw new SQLException("Failed to add feedback details.", e);  // Throw an exception for better debugging
            }

            connection.commit(); // Commit transaction if the insertion succeeds
        } catch (SQLException e) {
            throw new SQLException("Failed to add feedback.", e);  // Throw an exception for better debugging
        }
    }

    /**
     * Deletes a feedback from the database.
     * @param feedback The feedback object containing the feedback details to be deleted.
     * @throws SQLException if a database access error occurs or the SQL statement is invalid.
     */
    public void deleteFeedBack(Feedback feedback) throws SQLException {
        // Use try-with-resources to ensure that the database connection is properly closed after the operation is completed, even if an exception occurs
        try (Connection connection = dataConnection.getConnection()) {
            connection.setAutoCommit(false); // start transaction

            // Use try-with-resources to automatically close the PreparedStatement and prevent memory leaks, even if an exception occurs.
            try (PreparedStatement query = connection.prepareStatement(sqlDelete)) {
                query.setString(1, feedback.getFeedbackId()); // Set the feedback ID as a parameter for the SQL query
                query.executeUpdate(); // Execute the SQL query
            } catch (SQLException e) {
                throw new SQLException("Failed to delete feedback details.", e); // Throw an exception for better debugging
            }

            connection.commit(); // Commit transaction if both deletions succeed
        } catch (SQLException e) {
            throw new SQLException("Failed to delete feedback.", e); // Throw an exception for better debugging
        }
    }

    /**
     * Updates a feedback in the database.
     * @param feedback The feedback object containing the feedback details to be updated.
     * @throws SQLException if a database access error occurs or the SQL statement is invalid.
     */
    public void updateFeedback(Feedback feedback) throws SQLException {
        // Use try-with-resources to ensure that the database connection is properly closed after the operation is completed, even if an exception occurs
        try (Connection connection = dataConnection.getConnection()) {
            connection.setAutoCommit(false); // start transaction

            // Use try-with-resources to automatically close the PreparedStatement and prevent memory leaks, even if an exception occurs.
            try (PreparedStatement query = connection.prepareStatement(sqlUpdate)) {
                query.setString(1, feedback.getComment());
                query.setInt(2, feedback.getRating());
                query.setDate(3, Date.valueOf(feedback.getDate()));
                query.setString(4, feedback.getFeedbackId());

                int rowsAffected = query.executeUpdate();  // Execute the SQL query and get the number of rows affected
                // Execute the SQL query and check if the insertion was successful. If the insertion fails, throw an exception for better debugging
                if (rowsAffected == 0) {
                    throw new SQLException("No feedback found to update."); // Throw an exception for better debugging
                }
            } catch (SQLException e) {
                throw new SQLException("Failed to update feedback details.", e); // Throw an exception for better debugging
            }
        } catch (SQLException e) {
            throw new SQLException("Failed to update feedback.", e); // Throw an exception for better debugging
        }
    }
}
