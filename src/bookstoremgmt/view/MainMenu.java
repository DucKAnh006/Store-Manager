package bookstoremgmt.view;

import javax.swing.*;

/**
 * Main Menu class for the Bookstore Management Application
 * Provides navigation to all major functional areas
 * 
 * @author Nguyen Tran Duc Anh
 */
public class MainMenu extends BaseMenu {

    // Sub-menu instances
    private final BookMenu bookMenu;
    private final AuthorMenu authorMenu;
    private final CustomerMenu customerMenu;
    private final OrderMenu orderMenu;

    /**
     * Constructor - initializes all sub-menus
     */
    public MainMenu() {
        this.bookMenu = new BookMenu();
        this.authorMenu = new AuthorMenu();
        this.customerMenu = new CustomerMenu();
        this.orderMenu = new OrderMenu();
    }

    @Override
    public void display() {
        boolean running = true;

        while (running) {
            try {
                clearScreen();
                printWelcomeBanner();
                printMenuHeader();
                printMenuOptions();

                int choice = getIntInput("Enter your choice: ", 0, getMenuOptions().length);

                if (choice == 0) {
                    running = false;
                    printGoodbyeMessage();
                } else {
                    handleMenuChoice(choice);
                }
            } catch (Exception e) {
                showError("An unexpected error occurred: " + e.getMessage());
                pauseScreen();
            }
        }
    }

    @Override
    protected String getMenuTitle() {
        return "BOOKSTORE MANAGEMENT APPLICATION - MAIN MENU";
    }

    @Override
    protected String[] getMenuOptions() {
        return new String[] {
                "Manage Books",
                "Manage Authors",
                "Manage Customers",
                "Manage Orders"
        };
    }

    @Override
    protected void handleMenuChoice(int choice) {
        switch (choice) {
            case 1: // Manage Books
                bookMenu.display();
                break;

            case 2: // Manage Authors
                authorMenu.display();
                break;

            case 3: // Manage Customers
                customerMenu.display();
                break;

            case 4: // Manage Orders
                orderMenu.display();
                break;

            default:
                // Should never reach here due to input validation
                // Keeping for defensive programming and debugging
                showError("Invalid option: " + choice + ". Please report this bug.");
                pauseScreen();
                break;
        }
    }

    /**
     * Print welcome banner
     */
    private void printWelcomeBanner() {
        System.out.println("  =========================================================");
        System.out.println("  |        WELCOME TO BOOKSTORE MANAGEMENT SYSTEM         |");
        System.out.println("  |              Group 4 - PRO192 Assignment              |");
        System.out.println("  =========================================================");
        System.out.println();
        System.out.println();
    }

    /**
     * Print goodbye message when exiting
     */
    private void printGoodbyeMessage() {
        clearScreen();
        System.out.println("  Thank you for using Bookstore Management System!");
        System.out.println("  Goodbye!");
    }
}
