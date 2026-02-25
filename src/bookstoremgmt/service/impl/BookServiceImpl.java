/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bookstoremgmt.service.impl;

import bookstoremgmt.model.Author;
/**
 *
 * @author FA25_PRO192_SE2002_Group4
 */
import bookstoremgmt.model.Book;
import bookstoremgmt.repository.BookRepository;
import bookstoremgmt.service.IBookService;
import java.util.List;
import java.util.Map;
import java.util.ArrayList;
import java.util.HashMap;

public class BookServiceImpl implements IBookService {
    List<Book> bookList = new ArrayList<>();
    private Map<String, Book> bookMap = new HashMap<>();
    private final BookRepository bookRepository = new BookRepository();

    // constructor call loadBooks only once. we do not have to load every time when we use method in this class
    public BookServiceImpl() {
        this.bookList = bookRepository.loadBooks();
        for (Book book : bookList) {
            bookMap.put(book.getId(), book);
        }
    }
 
    /**
     * 
     * Store book read in a list and a map to use
     */ 
    public void loadBooks() {
        bookMap.clear();
        this.bookList = bookRepository.loadBooks();
        for (Book b : bookList) {
            bookMap.put(b.getId(), b);
        }
    }

    /**
     * save book in to file after edit
     * 
     */ 
    public boolean saveBooks() {
        return bookRepository.saveBook(bookList);
    }

    @Override
    public List<Book> getAll() {
        return this.bookList;
    }

    /**
     * 
     * @param id
     * @return obj that contain id
     */
    @Override
    public Book getById(String id) {
        if (exists(id)) {
            return bookMap.get(id);
        }
        return null;
    }

    /**
     * 
     * @param book to save information to file
     * @return true if save successfully
     */
    @Override
    public boolean add(Book book) {
        if (book .getId() == null ||
        book.getTitle() == null ||
        book.getCategory() == null||
        book.getPublisher() == null ||
        book.getLanguage() == null||
        book.getDescription() == null) return false;
        this.bookList.add(book);
        bookMap.put(book.getId(), book);
        return saveBooks();
    }

    // method to find new book's author by author ID
    public Author bookAuthor(List<Author> authorList, String authorId) {
        Author author = new Author();
        for (Author au : authorList) {
            if (au.getId().toLowerCase().contains(authorId.toLowerCase())) {
                author = au;
                break;
            }
        }
        return author;
    }

    @Override
    public boolean update(Book book) {
        return false;
    }

    /**
     * 
     * @param id to delete book
     * @return true if save successfully
     */
    @Override
    public boolean delete(String id) {
        bookMap.remove(id);
        bookList.removeIf(b -> b.getId().equalsIgnoreCase(id));
        return saveBooks();
    }

    /**
     * @param id to compare with other book id in list
     * @return true if that id exist
     */
    @Override
    public boolean exists(String id) {
        if (bookMap.containsKey(id)) {
            return true;
        }
        return false;
    }

    /**
     * 
     * @return number of book in shop
     */
    @Override
    public long count() {
        return bookList.size();
    }

    // Implement IbookService methods
    /**
     * @param book to check it quantity
     * @return its quantity
     */
    @Override
    public int checkBookQuantity(Book book) {
        return book.getStockQuantity();
    }

    /**
     * 
     * @param id to check if it is a valid book
     * @return true if it is valid
     */
    @Override
    public boolean invalidBook(String id) {
        for (Book book : bookList) {
            if (book.getId().equalsIgnoreCase(id))
                return false;
        }
        System.out.println("Invalid book's ID");
        return true;
    }

    /**
     * 
     * @param book when adding to generate id to store
     */
    @Override
    public String generateBookId(Book book) {
        String bookId = "";
        Author author = book.getAuthor();
        int biggest = 0;
        for (Book b : bookList) {
            String token[] = b.getId().split("-");
            if (Integer.parseInt(token[1]) > biggest)
                biggest = Integer.parseInt(token[1]);
        }
        bookId += author.getId() + "-" + String.format("%03d", biggest + 1);
        return bookId;
    }

    /**
     * 
     * @param title to compare with all book in list and store in a list
     * @return list that include all book which name contain String title
     */
    @Override
    public List<Book> searchByTitle(String title) {
        List<Book> foundBooks = new ArrayList<>();
        for (Book b : bookList) {
            if (b.getTitle().toLowerCase().contains(title.toLowerCase())) {
                foundBooks.add(b);
            }
        }
        return foundBooks;
    }

    /**
     * 
     * @param autnorName to compare with all book in list and store in a list
     * @return list that include all book which author' name contain String
     */
    @Override
    public List<Book> searchByAuthorName(String authorName) {
        List<Book> foundBooks = new ArrayList<>();
        for (Book b : bookList) {
            if (b.getAuthor().getName().toLowerCase().contains(authorName.toLowerCase())) {
                foundBooks.add(b);
            }
        }
        return foundBooks;
    }

    /**
     * Check and update book quantity before sell
     * 
     * @param bookId to find that book
     * @param quantity to check book's quantity before sell
     */
    @Override
    public void decreaseStock(String bookId, int quantity) throws Exception {
        Book book = getById(bookId);
        if (!book.decreaseStock(quantity)) {
            throw new Exception("Not enough stock");
        }
        saveBooks();
    }
    
    /**
     * Update book quantity when import book to sell
     * 
     * @param bookId to find that book
     * @param quantity to update book quantity
     */
    @Override
    public void increaseStock(String bookId, int quantity) throws Exception {
        Book book = getById(bookId);
        book.increaseStock(quantity);
        saveBooks();
    }
}
