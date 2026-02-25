package bookstoremgmt.view;

import java.util.Scanner;

/**
 * Base class for all menu views in the bookstore application
 * Provides common functionality for console-based menu interaction
 * 
 * @author Nguyen Tran Duc Anh
 */
public abstract class BaseMenu {

    // Constants
    protected static final int SEPARATOR_LENGTH = 60;
    protected static final int CLEAR_SCREEN_LINES = 50;
    protected static final String SEPARATOR_CHAR = "=";
    protected static final String LINE_CHAR = "-";

    protected static final String SUCCESS_PREFIX = "SUCCESS: ";
    protected static final String ERROR_PREFIX = "ERROR: ";
    protected static final String INFO_PREFIX = "INFO: ";

    /**
     * Shared Scanner instance for all menus.
     * NOTE: This Scanner is intentionally NOT closed as it wraps System.in.
     * Closing it would make System.in unavailable for the rest of the application.
     * The Scanner will be cleaned up when the JVM terminates.
     */
    protected static final Scanner scanner = new Scanner(System.in);

    /**
     * Display the menu and handle user interaction
     */
    public abstract void display();

    /**
     * Get the menu title
     * 
     * @return The title of the menu
     */
    protected abstract String getMenuTitle();

    /**
     * Get menu options as an array of strings
     * 
     * @return Array of menu option descriptions
     */
    protected abstract String[] getMenuOptions();

    /**
     * Handle the user's menu selection
     * 
     * @param choice The user's choice
     */
    protected abstract void handleMenuChoice(int choice);

    /**
     * Print the menu header with title
     */
    protected void printMenuHeader() {
        printSeparator();
        System.out.println("  " + getMenuTitle());
        printSeparator();
    }

    /**
     * Print all menu options
     */
    protected void printMenuOptions() {
        String[] options = getMenuOptions();
        for (int i = 0; i < options.length; i++) {
            System.out.println("  " + (i + 1) + ". " + options[i]);
        }
        System.out.println("  0. Exit");
        printSeparator();
    }

    /**
     * Print a separator line
     */
    protected void printSeparator() {
        System.out.println(repeatString(SEPARATOR_CHAR, SEPARATOR_LENGTH));
    }

    /**
     * Print a simple line
     */
    protected void printLine() {
        System.out.println(repeatString(LINE_CHAR, SEPARATOR_LENGTH));
    }

    /**
     * Repeat a string n times (JDK 8 compatible alternative to String.repeat())
     * 
     * @param str   The string to repeat
     * @param count The number of times to repeat
     * @return The repeated string
     */
    private String repeatString(String str, int count) {
        StringBuilder sb = new StringBuilder(str.length() * count);
        for (int i = 0; i < count; i++) {
            sb.append(str);
        }
        return sb.toString();
    }

    /**
     * Get integer input from user with validation and range checking
     * 
     * @param prompt The prompt to display
     * @param min    Minimum allowed value (inclusive)
     * @param max    Maximum allowed value (inclusive)
     * @return Valid integer input within range
     */
    protected int getIntInput(String prompt, int min, int max) {
        while (true) {
            try {
                System.out.print(prompt);
                String input = scanner.nextLine().trim();
                int value = Integer.parseInt(input);

                if (value < min || value > max) {
                    System.out.println("Please enter a number between " + min + " and " + max + ".");
                    continue;
                }
                return value;
            } catch (NumberFormatException e) {
                System.out.println("Invalid input! Please enter a valid number.");
            }
        }
    }

    /**
     * Get integer input from user with validation (no range limit)
     * 
     * @param prompt The prompt to display
     * @return Valid integer input
     */
    protected int getIntInput(String prompt) {
        return getIntInput(prompt, Integer.MIN_VALUE, Integer.MAX_VALUE);
    }

    /**
     * Get positive double input from user with validation
     * Useful for prices, etc.
     * 
     * @param prompt The prompt to display
     * @return Valid positive double input
     */
    protected double getPositiveDoubleInput(String prompt) {
        while (true) {
            try {
                System.out.print(prompt);
                String input = scanner.nextLine().trim();
                double value = Double.parseDouble(input);

                if (value <= 0) {
                    System.out.println("Value must be greater than 0!");
                    continue;
                }
                return value;
            } catch (NumberFormatException e) {
                System.out.println("Invalid input! Please enter a valid number.");
            }
        }
    }

    /**
     * Get string input from user with validation
     * 
     * @param prompt The prompt to display
     * @return Non-empty string input
     */
    protected String getStringInput(String prompt) {
        while (true) {
            System.out.print(prompt);
            String input = scanner.nextLine().trim();
            if (!input.isEmpty()) {
                return input;
            }
            System.out.println("Input cannot be empty! Please try again.");
        }
    }

    /**
     * Get optional string input (can be empty)
     * 
     * @param prompt The prompt to display
     * @return String input (may be empty)
     */
    protected String getOptionalStringInput(String prompt) {
        System.out.print(prompt);
        return scanner.nextLine().trim();
    }

    /**
     * Get yes/no confirmation from user
     * 
     * @param prompt The prompt to display
     * @return true if user confirms (Y/yes), false if user denies (N/no)
     */
    protected boolean getConfirmation(String prompt) {
        while (true) {
            System.out.print(prompt + " (Y/N): ");
            String input = scanner.nextLine().trim().toLowerCase();
            
            if (input.equals("y") || input.equals("yes")) {
                return true;
            }

            if (input.equals("n") || input.equals("no")) {
                return false;
            }

            System.out.println("Invalid input! Please enter Y (yes) or N (no).");
        }
    }

    /**
     * Display success message
     * 
     * @param message The message to display
     */
    protected void showSuccess(String message) {
        System.out.println(SUCCESS_PREFIX + message);
    }

    /**
     * Display error message
     * 
     * @param message The message to display
     */
    protected void showError(String message) {
        System.out.println(ERROR_PREFIX + message);
    }

    /**
     * Display info message
     * 
     * @param message The message to display
     */
    protected void showInfo(String message) {
        System.out.println(INFO_PREFIX + message);
    }

    /**
     * Pause and wait for user to press Enter
     */
    protected void pauseScreen() {
        System.out.print("\nPress Enter to continue...");
        scanner.nextLine();
    }

    /**
     * Clear screen (simulate by printing empty lines)
     */
    protected void clearScreen() {
        for (int i = 0; i < CLEAR_SCREEN_LINES; i++) {
            System.out.println();
        }
    }
}
