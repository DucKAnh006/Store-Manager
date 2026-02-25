/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bookstoremgmt.service;

import java.util.List;

/**
 *
 * @author FA25_PRO192_SE2002_Group4
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
