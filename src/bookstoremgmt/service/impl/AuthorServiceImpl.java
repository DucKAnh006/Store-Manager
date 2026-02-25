package bookstoremgmt.service.impl;

import java.util.ArrayList;
import java.util.List;
import bookstoremgmt.model.Author;
import bookstoremgmt.repository.AuthorRepository;
import bookstoremgmt.service.IService;

/**
 *
 * @author Nguyen Tran Duc Anh
 */

public class AuthorServiceImpl implements IService<Author> {
    private final List<Author> authors = new ArrayList<>();
    private final AuthorRepository repository = new AuthorRepository();

    public AuthorServiceImpl() {
        List<Author> loaded = repository.loadAuthors();
        if (loaded != null) {
            this.authors.addAll(loaded);
        }
    }

    @Override
    public boolean add(Author entity) {
        if (entity == null || entity.getName() == null || entity.getName().trim().isEmpty()) {
            System.err.println("Invalid author (null or no name).");
            return false;
        }

        try {
            String id = generationID(entity.getName(), authors);
            entity.setId(id);

            // add to list
            authors.add(entity);

            // persist
            repository.saveAuthors(authors);

            System.out.println("Author added: " + entity.getId() + " - " + entity.getName());
            return true;

        } catch (IllegalArgumentException | IllegalStateException e) {
            System.err.println("Unable to create ID: " + e.getMessage());
            return false;
        }
    }

    @Override
    public List<Author> getAll() {
        return new ArrayList<>(authors);
    }

    @Override
    public Author getById(String id) {
        if (id != null && !id.trim().isEmpty()) {
            for (Author a : authors) {
                if (a.getId().equals(id)) {
                    return a;
                }
            }
        }
        return null;
    }

    @Override
    public boolean update(Author entity) {
        if (entity != null) {
            Author tmp = getById(entity.getId());
            if (tmp != null) {
                tmp.setName(entity.getName());
                tmp.setPhoneNumber(entity.getPhoneNumber());

                // persist
                repository.saveAuthors(authors);
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean delete(String id) {
        Author tmp = getById(id);
        if (tmp != null) {
            // remove the author from the list (was incorrectly removing a book id before)
            boolean removed = authors.remove(tmp);
            if (removed) {
                // persist
                repository.saveAuthors(authors);
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean exists(String id) {
        return getById(id) != null;
    }

    @Override
    public long count() {
        return (long) authors.size();
    }

    public List<Author> findByName(String name) {
        List<Author> result = new ArrayList<>();
        if (name == null)
            return result;

        String keyword = name.toLowerCase();
        for (Author a : authors) {
            if (a.getName() != null && a.getName().toLowerCase().contains(keyword)) {
                result.add(a);
            }
        }
        return result;
    }

    public String showAllAuthorBooksID(String authorId) {
        Author author = getById(authorId);
        if (author == null) {
            return "Author not found!";
        }
        StringBuilder result = new StringBuilder();

        result.append("Author: ").append(author.getName()).append("\nBooks:\n");

        List<String> listIDBook = author.getBooksID();
        for (int i = 0; i < listIDBook.size(); i++) {
            if (i % 10 == 0)
                result.append("| ");
            result.append(listIDBook.get(i)).append(" ");
            if ((i + 1) % 10 == 0) {
                result.append("|\n");
            }
        }

        if (listIDBook.size() % 10 != 0) {
            result.append("|");
        }
        return result.toString();
    }

    // Support AddBook by DucAnh
    public void saveOrUpdateAuthor(String name, String phone, String bookId) {
        // 1. Load the current list of authors from file
        List<Author> authors = repository.loadAuthors();

        // 2. Check if the author already exists (match by name and phone number)
        Author existingAuthor = null;
        for (Author a : authors) {
            if (a.getName().equalsIgnoreCase(name.trim()) && a.getPhoneNumber().equals(phone.trim())) {
                existingAuthor = a;
                break;
            }
        }

        // 3. If the author already exists → just add the new BookID
        if (existingAuthor != null) {
            if (!existingAuthor.getBooksID().contains(bookId)) {
                existingAuthor.addBookID(bookId);
                System.out.println("Added BookID " + bookId + " to author: " + existingAuthor.getName());
            } else {
                System.out.println("BookID " + bookId + " already exists for author: " + existingAuthor.getName());
            }
        }
        // 4. If not found → create a new author and assign BookID
        else {
            String newId = generationID(name, authors); // Auto-generate ID based on name
            Author newAuthor = new Author(newId, name.trim(), phone.trim());
            newAuthor.addBookID(bookId);
            authors.add(newAuthor);
            System.out.println("Created new author: " + name + " with BookID: " + bookId);
        }

        // 5. Save the updated list back to file
        repository.saveAuthors(authors);
    }

    // --- helper ---
    private String generationID(String name, List<Author> authors) {
        String[] parts = name.trim().split("\\s+");

        if (parts.length < 2) {
            throw new IllegalArgumentException("Name must contain at least first and last name.");
        }

        String firstLetter = parts[0].substring(0, 1).toUpperCase();
        String lastLetter = parts[parts.length - 1].substring(0, 1).toUpperCase();

        String prefix = firstLetter + lastLetter;

        int count = 0;
        for (Author a : authors) {
            if (a.getId() != null && a.getId().startsWith(prefix)) {
                count++;
            }
        }

        if (count >= 999) {
            throw new IllegalStateException("Maximum ID limit (999) reached for prefix: " + prefix);
        }

        return String.format("%s%03d", prefix, count + 1);
    }
}
