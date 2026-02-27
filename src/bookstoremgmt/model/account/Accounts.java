package bookstoremgmt.model.account;

/**
 * 
 * @author Nguyen Tran Duc Anh
 */
public abstract class Accounts {
    private String name; // Name of the account holder
    private String userId; // Unique user ID for login
    private String password; // Password for authentication

    /**
     * Default constructor for Accounts. Initializes an empty account.
     */
    public Accounts() {
    }

    /**
     * Constructor for Accounts with userId and password. Name can be set later.
     */
    public Accounts(String userId, String password) {
        this.userId = userId;
        this.password = password;
    }

    /**
     * Constructor for Accounts with all fields. Useful for creating a complete
     * account in one step.
     */
    public Accounts(String name, String userId, String password) {
        this.name = name;
        this.userId = userId;
        this.password = password;
    }

    // Getters and Setters
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
