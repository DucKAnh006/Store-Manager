package bookstoremgmt.repository;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.nio.file.Path;
import java.nio.file.Files;
import bookstoremgmt.model.Book;
import bookstoremgmt.service.impl.AuthorServiceImpl;
import bookstoremgmt.model.Author;

/**
 *
 * @author FA25_PRO192_SE2002_Group4
 */
public class BookRepository {
    private AuthorServiceImpl authorService = new AuthorServiceImpl();
    private final String FILE_BOOK = "src/resource/data/book.txt";

    /**
     * Check file path before save or load information
     * 
     * @return true if file path is valid
     */
    public boolean filePathCheck() {
        Path bookPath = Paths.get(FILE_BOOK);
        return Files.exists(bookPath);

    }
    
    /**
     * Save book after changes book information
     * 
     * @param book
     * @return true if it is successfully
     */
    public boolean saveBook(List<Book> book) {
        if (!filePathCheck()) return false;
        try (BufferedWriter wr = new BufferedWriter(new FileWriter(FILE_BOOK))) {
            for (Book b : book) {
                if (b == null) {
                    continue;
                }
                wr.write(b.getId() + "|" + b.getTitle() + "|" +
                        b.getPrice() + "|" + b.getStockQuantity() + "|" +
                        b.getCategory() + "|" + b.getPublisher() + "|" +
                        b.getYearPublished() + "|" + b.getLanguage() + "|" +
                        b.getDescription());
                wr.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    /**
     * Read information from file to use
     * 
     * @return List include all book
     */
    public List<Book> loadBooks() {
        // check file path
        if (!filePathCheck()) {
            System.out.println("Books data file not found!");
            return null;
        } else {
            // create a list to store and return
            List<Book> book = new ArrayList<>();
            try (BufferedReader rd = new BufferedReader(new FileReader(FILE_BOOK))) {
                String line;
                while ((line = rd.readLine()) != null) {
                    String[] parts = line.split("\\|");
                       try {
                            String[] authorIdParts = parts[0].split("-");
                            // id format: AuthorID-BookId. Thus if authorIdParts that bookId is invalid
                            if (authorIdParts.length < 2) break;

                            boolean authorFound = false;
                            Author authorPosition = null;
                            for (Author authorTmp : authorService.getAll()) {
                                if (authorTmp.getId().equals(authorIdParts[0])) {
                                    authorPosition = authorTmp;
                                    authorFound = true;
                                    break;
                                }
                            }

                            // if book's author was found the program will load data to create an object
                            if (authorFound){
                                book.add(new Book(parts[0], parts[1],
                                    new Author(authorPosition.getId(),
                                            authorPosition.getName(),
                                            authorPosition.getPhoneNumber()),
                                    Double.parseDouble(parts[2]),
                                    Integer.parseInt(parts[3]),
                                    parts[4],
                                    parts[5],
                                    Integer.parseInt(parts[6]),
                                    parts[7],
                                    parts[8]));
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            return book;
        }
        }
        
}
