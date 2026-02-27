package bookstoremgmt.model.catalog;

/**
 * Author class represents an author in the bookstore management system. It has properties such as id and name.
 * @author Nguyen Tran Duc Anh
 */
public class Author {
    private String id; // Unique identifier for the author
    private String name; // Name of the author

    /**
     * Default constructor for Author class
     */
    public Author() {
    }

    /**
     * Parameterized constructor to initialize Author with id and name
     */
    public Author(String id, String name) {
        this.id = id;
        this.name = name;
    }

    // Getters and Setters
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
