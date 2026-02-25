/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bookstoremgmt.repository;

import java.io.*;
import java.util.*;

import bookstoremgmt.model.Author;

/**
 *
 * @author FA25_PRO192_SE2002_Group4
 */
public class AuthorRepository {
    private static final String FILE_AUTHOR = "src" + File.separator + "resource" + File.separator + "data"
            + File.separator + "author.txt";

    // Changed return type to boolean to signal success/failure
    public boolean saveAuthors(List<Author> authors) {

        // Null check: Check if the input list is null
        if (authors == null) {
            System.err.println("Author list is null. Cannot save.");
            return false; // Save failed
        }

        authors.sort(Comparator.comparing(Author::getId));

        File file = new File(FILE_AUTHOR);
        File parentDir = file.getParentFile();
        if (parentDir != null && !parentDir.exists()) {
            parentDir.mkdirs();
        }

        try (BufferedWriter wr = new BufferedWriter(new FileWriter(FILE_AUTHOR))) {
            for (Author a : authors) {
                // Null check: Check for null Author object in the list
                if (a == null) {
                    System.err.println("Skipping null Author object.");
                    continue; // Skip null object
                }

                // Null check: Check if bookID list is null before joining
                List<String> bookID = a.getBooksID();
                String books = ""; // Default value
                if (bookID != null) {
                    books = String.join(",", bookID);
                }

                // Add null checks for main fields to prevent NPE
                String id = (a.getId() != null) ? a.getId() : "";
                String name = (a.getName() != null) ? a.getName() : "";
                String phone = (a.getPhoneNumber() != null) ? a.getPhoneNumber() : "";

                wr.write(id
                        + "|" + name
                        + "|" + phone
                        + "|" + books);
                wr.newLine();
            }
        } catch (IOException e) {
            System.err.println("Error writing author file: " + e.getMessage());
            return false; // Save failed due to IO error
        }

        return true; // Save successful
    }

    public List<Author> loadAuthors() {
        List<Author> authors = new ArrayList<>();
        File file = new File(FILE_AUTHOR);

        // Check if file exists before reading
        if (!file.exists()) {
            System.err.println("Author file not found. Returning empty list: " + FILE_AUTHOR);
            return authors; // Return empty list
        }

        try (BufferedReader rd = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = rd.readLine()) != null) {
                line = line.trim();
                if (line.isEmpty())
                    continue;

                String[] parts = line.split("\\|");

                // Require at least 3 parts (ID, Name, Phone)
                if (parts.length < 3) {
                    System.err.println("Skipping malformed line: " + line);
                    continue;
                }

                // Check for null/empty on critical fields (ID, Name)
                if (parts[0] == null || parts[0].trim().isEmpty() ||
                        parts[1] == null || parts[1].trim().isEmpty()) {
                    System.err.println("Skipping author with missing ID or Name: " + line);
                    continue;
                }

                // Safe assignment, allow empty phone
                String id = parts[0];
                String name = parts[1];
                String phone = (parts[2] == null) ? "" : parts[2];

                Author author = new Author(id, name, phone);

                // Process books (if they exist)
                if (parts.length == 4 && parts[3] != null && !parts[3].isEmpty()) {
                    String[] books = parts[3].split(",");
                    for (String b : books) {
                        if (b != null && !b.trim().isEmpty()) { // Skip empty book ID
                            author.addBookID(b.trim());
                        }
                    }
                }
                authors.add(author);
            }
        } catch (IOException e) {
            System.err.println("Error load author file: " + e.getMessage());
        }
        return authors;
    }
}