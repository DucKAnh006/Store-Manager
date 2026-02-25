package bookstoremgmt.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 *
 * @author Nguyen Tran Duc Anh
 */
public class Author extends Person {
    private final List<String> booksID;

    public Author() {
        super();
        this.booksID = new ArrayList<>();
    }

    public Author(String id, String name, String phoneNumber) {
        super(id, name, phoneNumber);
        this.booksID = new ArrayList<>();
    }

    // Book list access
    public List<String> getBooksID() {
        return Collections.unmodifiableList(booksID);
    }

    public void addBookID(String bookID) {
        if (bookID != null && !bookID.trim().isEmpty() && !booksID.contains(bookID)) {
            booksID.add(bookID);
        }
    }

    public boolean removeBookID(String bookID) {
        return booksID.remove(bookID);
    }

    public int getBooksIDCount() {
        return booksID.size();
    }

    @Override
    public String toString() {
         return String.format("%-10s | %-25s | %-12s | %-10d \n",
            getId(), getName(), getPhoneNumber(), getBooksIDCount());
    }
}
