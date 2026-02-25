/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bookstoremgmt;

import bookstoremgmt.view.MainMenu;

/**
 * Main entry point for the Bookstore Management System
 * This application provides a console-based interface for managing
 * books, authors, customers, and orders in a bookstore.
 * 
 * @author FA25_PRO192_SE2002_Group4
 */
public class Bookstoremgmt {

    /**
     * Main method - application entry point
     * Initializes and displays the main menu
     * 
     * @param args the command line arguments (not used)
     */
    public static void main(String[] args) {
        try {
            // Create and display the main menu
            MainMenu mainMenu = new MainMenu();
            mainMenu.display();
            
        } catch (Exception e) {
            System.err.println("FATAL ERROR: Application encountered an unexpected error.");
            System.err.println("Error details: " + e.getMessage());
            e.printStackTrace();
            System.exit(1);
        }
    }
}
