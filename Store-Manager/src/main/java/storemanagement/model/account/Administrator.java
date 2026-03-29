package storemanagement.model.account;

/**
 * Administrator class represents an administrator account in the bookstore management system.
 * This class can be used to manage administrator accounts and their privileges in the system.
 * @author Nguyen Tran Duc Anh
 */
public class Administrator {
    private boolean isAdmin = true; // Flag to indicate that this account is an administrator
    private String userId; // Unique user ID for login
    private String password; // Password for authentication

    /**
     * Default constructor initializes the administrator account
     */
    public Administrator() {
    }

    /**
     * Parameterized constructor to initialize the administrator account with user ID and password
     */
    public Administrator(String userId, String password) {
        this.userId = userId;
        this.password = password;
    }

    /**
     * Check if the account is an administrator
     * @return true if the account is an administrator, false otherwise
     */
    public boolean isAdmin() {
        return isAdmin;
    }
}
