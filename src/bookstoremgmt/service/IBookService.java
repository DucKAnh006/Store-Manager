package bookstoremgmt.service;

import java.util.List;

/**
 *
 * @author Nguyen Tran Duc Anh
 */
import bookstoremgmt.model.Book;

public interface IBookService extends IService<Book> {
    int checkBookQuantity(Book book);
    String generateBookId(Book book);
    List<Book> searchByTitle(String title);
    List<Book> searchByAuthorName(String authorName);
    boolean invalidBook (String id);
    void decreaseStock(String bookId, int quantity) throws Exception;
    void increaseStock(String bookId, int quantity) throws Exception;
}
