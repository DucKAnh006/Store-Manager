package bookstoremgmt.view;

import bookstoremgmt.model.Author;
import bookstoremgmt.model.Book;
import bookstoremgmt.service.impl.BookServiceImpl;
import bookstoremgmt.service.impl.AuthorServiceImpl;
import java.util.List;
import java.time.LocalDate;
import java.util.Scanner;

/**
 *
 * @author Nguyen Tran Duc Anh
 */
public class BookMenu extends BaseMenu {
    private final Scanner sc = new Scanner(System.in);
    private final BookServiceImpl bookService = new BookServiceImpl();
    private final AuthorServiceImpl authorService = new AuthorServiceImpl();
    LocalDate currentDate = LocalDate.now();

    @Override
    public void display() {
        int choice = 1;
        while (choice != 0) {
            clearScreen();
            printMenuHeader();
            printLine();
            printMenuOptions();
            printSeparator();
            choice = getIntInput("Enter your choice: ", 0, getMenuOptions().length);
            handleMenuChoice(choice);
        }
    }

    @Override
    public String getMenuTitle() {
        return "Book Management Menu";
    }

    @Override
    public String[] getMenuOptions() {
        return new String[] {
                "Add New Book",
                "Edit Book Information",
                "Delete Book",
                "Search Book",
                "Search Author's Book",
                "Display All Book"
        };
    }

    public String[] getMenuOptions(int i) {
        return new String[] {
                "Title",
                "Price",
                "Stock Quantity",
                "Category",
                "Publisher",
                "Year Published",
                "Language",
                "Description",
                "Author"
        };
    }

    @Override
    protected void printMenuOptions() { // done
        String[] options = getMenuOptions();
        for (int i = 0; i < options.length; i++) {
            System.out.println("  " + (i + 1) + ". " + options[i]);
        }
        System.out.println("  0. Exit");
        printSeparator();
    }

    protected void printMenuOptions(int j) { // done
        String[] options = getMenuOptions(j);
        for (int i = 0; i < options.length; i++) {
            System.out.println("  " + (i + 1) + ". " + options[i]);
        }
        System.out.println("  0. Exit");
        printSeparator();
    }

    @Override
    protected void handleMenuChoice(int choice) {
        switch (choice) {
            case 1:
                addBook();
                break;
            case 2:
                editBook();
                break;
            case 3:
                deleteBook();
                break;
            case 4:
                searchBook();
                break;
            case 5:
                searchAuthorBook();
                break;
            case 6:
                displayAllBook();
                break;
        }
    }

    /**
     * Display addBook UI and get new book information
     */
    void addBook() {
        boolean continueAdding = true;
        while (continueAdding) {
            clearScreen();
            printMenuHeader();
            printLine();
            Book book = new Book();
            System.out.println("Enter book information:");

            book.setTitle(getStringInput("Title: "));

            book.setPrice(getPositiveDoubleInput("Price: "));

            book.setStockQuantity(getIntInput("Stock Quantity: ", 0, Integer.MAX_VALUE));

            book.setCategory(getStringInput("Category: "));

            book.setPublisher(getStringInput("Book's Publisher: "));

            book.setYearPublished(getIntInput("Release Year: ", 0, currentDate.getYear())); // local time

            book.setLanguage(getStringInput("Language: "));

            book.setDescription(getStringInput("Description: "));
            String authorName = getStringInput("Author: ");
            Author author = new Author();
            if (authorService.findByName(authorName).isEmpty()) {
                if (getConfirmation("Do you want to add new author?")) {
                    authorName = getStringInput("Author's full name: ");
                    if (!authorService.findByName(authorName).isEmpty()) {
                        displayAuthor(authorService.findByName(authorName));
                        String authorId = getStringInput("Author's ID: ");
                        book.setAuthor(bookService.bookAuthor(authorService.findByName(authorName), authorId));
                        book.setId(bookService.generateBookId(book));
                    } else {
                        String authorPhoneNumber = getStringInput("Author's phonenumber: ");
                        author.setName(authorName);
                        author.setPhoneNumber(authorPhoneNumber);
                        authorService.add(author);
                        book.setAuthor(author);
                        book.setId(bookService.generateBookId(book));
                    }
                }
            } else {
                // print a list of author whose name contain input
                displayAuthor(authorService.findByName(authorName));
                String authorId = getStringInput("Author's ID: ");
                book.setAuthor(bookService.bookAuthor(authorService.findByName(authorName), authorId));
                book.setId(bookService.generateBookId(book));
            }

            if (getConfirmation("Do you want to add this book in to System?")) {
                if (bookService.add(book)) {
                    System.out.println("Book added successfully!");
                } else {
                    System.out.println("Book added unsuccessfully!");
                }
            } else {
                System.out.println("Book addition cancelled.");
            }

            continueAdding = getConfirmation("Do you want to continue adding book?");
        }

    }

    /**
     * Display editBook UI and get book's new information
     */
    void editBook() {
        String bookName;
        Book book = new Book();
        Boolean continueEditing = true;
        int searchCount = 0;
        do {
            clearScreen();
            printMenuHeader();
            printLine();
            do {
                if (searchCount >= 1) {
                    continueEditing = getConfirmation("Do you want to continnue searching?");
                }
                bookName = getStringInput("Enter Book's Title: ");
                searchCount++;
            } while (bookService.searchByTitle(bookName).isEmpty());
            displayBook(bookService.searchByTitle(bookName));
            String bookId;
            do {
                System.out.print("Enter Book ID to edit: ");
                bookId = sc.nextLine();
            } while (bookService.invalidBook(bookId));

            for (Book b : bookService.searchByTitle(bookName)) {
                if (b.getId().toLowerCase().contains(bookId.toLowerCase())) {
                    book = b;
                    int editChoice;
                    int addMenu = 1;
                    do {
                        System.out.println("Select field to edit:");
                        printMenuOptions(addMenu);
                        Author author = new Author();
                        editChoice = getIntInput("Enter your choice: ", 0, getMenuOptions(addMenu).length);
                        switch (editChoice) {
                            case 1:
                                book.setTitle(getStringInput("New Title: "));
                                break;
                            case 2:
                                book.setPrice(getPositiveDoubleInput("New Price: "));
                                break;
                            case 3:
                                book.setStockQuantity(getIntInput("New Stock Quantity: "));
                                break;
                            case 4:
                                book.setCategory(getStringInput("New Category: "));
                                break;
                            case 5:
                                book.setPublisher(getStringInput("Enter new Publisher: "));
                                break;
                            case 6:
                                book.setYearPublished(
                                        getIntInput("Enter new Year Published: ", 0, currentDate.getYear()));
                                break;
                            case 7:
                                book.setLanguage(getStringInput("Enter new Language: "));
                                break;
                            case 8:
                                book.setDescription(getStringInput("Enter new Description: "));
                                break;
                            case 9:
                                String authorName = getStringInput("New Author: ");
                                if (authorService.findByName(authorName).isEmpty()) {
                                    if (getConfirmation("Do you want to add new author?")) {
                                        authorName = getStringInput("Author's full name: ");
                                        if (!authorService.findByName(authorName).isEmpty()) {
                                            displayAuthor(authorService.findByName(authorName));
                                            String authorId = getStringInput("Author's ID: ");
                                            book.setAuthor(bookService.bookAuthor(authorService.findByName(authorName),
                                                    authorId));
                                        } else {
                                            String authorPhoneNumber = getStringInput("Author's phonenumber: ");
                                            author.setName(authorName);
                                            author.setPhoneNumber(authorPhoneNumber);
                                            authorService.add(author);
                                            book.setAuthor(author);
                                        }
                                    }
                                } else {
                                    displayAuthor(authorService.findByName(authorName));
                                    String authorId = getStringInput("Author's ID: ");
                                    book.setAuthor(
                                            bookService.bookAuthor(authorService.findByName(authorName), authorId));
                                }

                                String[] bookNum = book.getId().split("-");
                                String newBookId = book.getAuthor().getId() + "-" + bookNum[1];
                                book.setId(newBookId);
                                break;
                            case 0:
                                if (getConfirmation("Do you want to save your changes")) {
                                    b = book;
                                    bookService.saveBooks();
                                    System.out.println("Saved successfully!");
                                } else {
                                    System.out.println("Book save cancelled.");
                                }
                                break;
                        }
                    } while (editChoice != 0);
                    break;
                }
            }

            continueEditing = getConfirmation("Do you want to continue editing book?");

        } while (continueEditing);
    }

    /**
     * Display deleteBook UI and ask user's confirm to delete 
     */
    void deleteBook() {
        boolean inValidBook = true;
        String bookName = null;
        while (inValidBook) {
            clearScreen();
            printMenuHeader();
            printLine();
            bookName = getStringInput("Enter Book name to delete: ");
            if (!bookService.searchByTitle(bookName).isEmpty())
                inValidBook = false;
        }
        displayBook(bookService.searchByTitle(bookName));
        String bookId = getStringInput("Book's ID: ");
        bookService.delete(bookId);
    }

    /**
     * Display searchBook UI and search by title then display all valid result 
     */
    void searchBook() {
        String bookName;
        boolean continueSearching = true;
        while (continueSearching) {
            clearScreen();
            printMenuHeader();
            printLine();
            System.out.print("Enter Book's Title: ");
            bookName = sc.nextLine();
            displayBook(bookService.searchByTitle(bookName));
            continueSearching = getConfirmation("Do you want to conntinue searching");
        }

    }

    /**
     * Display searchAuthorBook UI and search by author name then display all valid result
     */
    void searchAuthorBook() {
        String authorName;
        boolean continueSearching = true;
        while (continueSearching) {
            clearScreen();
            printMenuHeader();
            printLine();
            System.out.print("Enter Author's Name: ");
            authorName = sc.nextLine();
            displayBook(bookService.searchByAuthorName(authorName));
            continueSearching = getConfirmation("Do you want to conntinue searching");
        }
    }

    /**
     * Display displayAllBook UI and display all book exist in system
     */
    void displayAllBook() {
        clearScreen();
        printMenuHeader();
        printLine();
        displayBook(bookService.getAll());
        sc.nextLine();
    }

    /**
     * Display displayBook UI and display all book from list
     * 
     * @param book list to display
     */
    public void displayBook(List<Book> book) {
        final String Format = "| %-8s | %-50s | %-35s |     %04d | %-8s | %-30s | %-4d | %-15s |\n";
        final String Title = "|    ID     |                        Title                       |                 Author              | Quantity | Category |           Publisher            | Year |    Language     |\n";
        final String Seperator = "|-----------|----------------------------------------------------|-------------------------------------|----------|----------|--------------------------------|------|-----------------|\n";
        final String Table = " -------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------\n";
        System.out.println(Table);
        System.out.println(Title);
        for (Book b : book) {
            System.out.println(Seperator);
            System.out.println(String.format(Format,
                    b.getId(),
                    b.getTitle(),
                    b.getAuthor().getName(),
                    b.getStockQuantity(),
                    b.getCategory(),
                    b.getPublisher(),
                    b.getYearPublished(),
                    b.getLanguage()));
        }
        System.out.println(Table);
    }  

    /**
     * Display displayAuthor UI and  and display all author from list
     * @param authorList
     */
    public void displayAuthor(List<Author> authorList) {
        final String Format = "| %-8s | %-30s | %-15s |\n";
        final String Title = "|    ID    |              Name              |   Phone Number  |\n";
        final String Seperator = "|----------|--------------------------------|-----------------|\n";
        final String Table = " -------------------------------------------------------------\n";
        System.out.println(Table);
        System.out.println(Title);
        for (Author a : authorList) {
            System.out.println(Seperator);
            System.out.println(String.format(Format,
                    a.getId(),
                    a.getName(),
                    a.getPhoneNumber()));
        }
        System.out.println(Table);
    }
}
