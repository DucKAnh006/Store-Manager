package storemanagement.model.catalog;

/**
 * Supplier class represents a supplier in the bookstore management system. It
 * has properties such as id and name.
 * 
 * @author Nguyen Tran Duc Anh
 */
public class Supplier {
    private String id; // Unique identifier for the supplier
    private String name; // Name of the supplier

    /**
     * Default constructor for Supplier class
     */
    public Supplier() {
    }

    /**
     * Parameterized constructor to initialize Supplier with id and name
     */
    public Supplier(String id, String name) {
        this.id = id;
        this.name = name;
    }

    // Getters Setters
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id.trim();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name.trim();
    }
}
