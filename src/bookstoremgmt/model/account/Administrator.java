package bookstoremgmt.model.account;

/**
 * Administrator class represents an administrator account in the bookstore management system.
 * It extends the Accounts class and has an additional property to indicate that it is an administrator account.
 * This class can be used to manage administrator accounts and their privileges in the system.
 * @author Nguyen Tran Duc Anh
 */
public class Administrator extends Accounts {
    private boolean isAdmin = true; // Flag to indicate that this account is an administrator

    /**
     * Default constructor initializes the administrator account
     */
    public Administrator() {
        super();
    }

    /**
     * Parameterized constructor to initialize the administrator account with user ID and password
     */
    public Administrator(String userId, String password) {
        super(userId, password);
    }

    /**
     * Check if the account is an administrator
     * @return true if the account is an administrator, false otherwise
     */
    public boolean isAdmin() {
        return isAdmin;
    }
}
