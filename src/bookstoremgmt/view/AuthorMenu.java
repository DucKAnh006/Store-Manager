/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bookstoremgmt.view;

import java.util.List;

import bookstoremgmt.model.Author;
import bookstoremgmt.service.impl.AuthorServiceImpl;

public class AuthorMenu extends BaseMenu {
    AuthorServiceImpl authorsService = new AuthorServiceImpl();

    @Override
    public void display() {
        int choice;
        do {
            clearScreen();
            printLine();
            printMenuHeader();
            printMenuOptions();
            printSeparator();
            choice = getIntInput("Enter your choice number: ", 0, getMenuOptions().length);
            if (choice != 0) {
                handleMenuChoice(choice);
            }
        } while (choice != 0);
    }

    @Override
    protected String getMenuTitle() {
        return "Author Menu";
    }

    @Override
    protected String[] getMenuOptions() {
        return new String[] {
                "Add author",
                "Update author",
                "Delete author",
                "Search author",
                "Show all author",
        };
    }

    // ===== HANDLE MENU CHOICES =====
    protected void handleMenuChoice(int choice) {
        switch (choice) {
            case 1: // ADD AUTHOR
                System.out.println("ADD AUTHOR:");
                String nameAdd = getStringInput("Enter author name: ");
                String phoneAdd = getOptionalStringInput("Enter phone (or leave empty): ");

                Author newAuthor = new Author();
                newAuthor.setName(nameAdd);
                newAuthor.setPhoneNumber(phoneAdd);

                boolean added = authorsService.add(newAuthor);
                if (added) {
                    showSuccess("Author added successfully. New ID: " + newAuthor.getId());
                } else {
                    showError("Failed to add author. Check input or storage.");
                }
                pauseScreen();
                break;

            case 2: // UPDATE AUTHOR
                System.out.println("UPDATE AUTHOR:");
                String idUpdate = getStringInput("Enter author ID to update: ");
                Author existing = authorsService.getById(idUpdate);

                if (existing == null) {
                    showError("Author with ID '" + idUpdate + "' not found.");
                    pauseScreen();
                    break;
                }

                showInfo("Current Info:");
                System.out.println(existing.toString());

                String nameUpd;
                while (true) {
                    nameUpd = getOptionalStringInput("Enter new name (leave empty to keep current): ");
                    // If empty, it's valid (will keep old name)
                    if (nameUpd.isEmpty()) {
                        break;
                    }
                    // If not empty, must have at least 2 words
                    if (nameUpd.trim().split("\\s+").length >= 2) {
                        break;
                    }
                    showError("Invalid name format. Name must have at least 2 words (e.g., 'First Last').");
                }
                String phoneUpd = getOptionalStringInput("Enter new phone (leave empty to keep current): ");

                Author upd = new Author();
                upd.setId(existing.getId());
                upd.setName(nameUpd.isEmpty() ? existing.getName() : nameUpd);
                upd.setPhoneNumber(phoneUpd.isEmpty() ? existing.getPhoneNumber() : phoneUpd);

                boolean updated = authorsService.update(upd);
                if (updated) {
                    showSuccess("Author updated successfully (ID: " + upd.getId() + ").");
                } else {
                    showError("Failed to update author.");
                }
                pauseScreen();
                break;

            case 3: // DELETE AUTHOR
                System.out.println("DELETE AUTHOR:");
                List<Author> all = authorsService.getAll();
                displayAuthors(all);

                String idDelete = getStringInput("Enter author ID to delete: ");
                if (!authorsService.exists(idDelete)) {
                    showError("Author with ID '" + idDelete + "' not found.");
                } else {
                    boolean confirmed = getConfirmation("Are you sure you want to delete author '" + idDelete + "'?");
                    if (confirmed) {
                        boolean deleted = authorsService.delete(idDelete);
                        if (deleted) {
                            showSuccess("Author deleted successfully (ID: " + idDelete + ").");
                        } else {
                            showError("Failed to delete author.");
                        }
                    } else {
                        showInfo("Delete cancelled.");
                    }
                }
                pauseScreen();
                break;

            case 4: // SEARCH AUTHOR
                System.out.println("SEARCH AUTHOR:");
                String nameSearch = getStringInput("Enter name to search: ");
                List<Author> found = authorsService.findByName(nameSearch);

                if (found.isEmpty()) {
                    showError("No authors found with name containing: " + nameSearch);
                } else {
                    displayAuthors(found);
                }
                pauseScreen();
                break;

            case 5: // SHOW ALL AUTHORS
                System.out.println("ALL AUTHORS:");
                displayAuthors(authorsService.getAll());
                pauseScreen();
                break;

            case 0:
                break;

            default:
                showError("Invalid option. Please try again (0–5).");
                break;
        }
    }

    // ===== HELPER DISPLAY TABLE FOR AUTHORS =====
    protected void displayAuthors(List<Author> listAuthors) {
        if (listAuthors == null || listAuthors.isEmpty()) {
            System.out.println("No authors found.");
            return;
        }

        // Header
        System.out.printf("%-10s | %-25s | %-12s | %-10s%n",
                "AuthorID", "Name", "Phone", "Book Count");
        System.out.println("------------------------------------------------------------------");

        // Rows
        for (Author a : listAuthors) {
            System.out.printf(a.toString());
        }

        System.out.println();
    }

}
