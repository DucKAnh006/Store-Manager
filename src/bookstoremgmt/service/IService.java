package bookstoremgmt.service;

import java.util.List;

/**
 * Generic service interface for managing entities in the bookstore
 * 
 * @author FA25_PRO192_SE2002_Group4
 * @param <T> The type of entity this service manages
 */
public interface IService<T> {
    
    /**
     * Get all entities
     * 
     * @return List of all entities (empty list if no entities found)
     */
    List<T> getAll();

    /**
     * Get entity by ID
     * 
     * @param id The ID of the entity (must not be null or empty)
     * @return The entity if found, null otherwise
     */
    T getById(String id);

    /**
     * Add a new entity
     * 
     * @param entity The entity to add (must not be null)
     * @return true if successfully added, false otherwise
     */
    boolean add(T entity);

    /**
     * Update an existing entity
     * 
     * @param entity The entity to update (must not be null)
     * @return true if successfully updated, false otherwise
     */
    boolean update(T entity);

    /**
     * Delete an entity by ID
     * 
     * @param id The ID of the entity to delete (must not be null or empty)
     * @return true if successfully deleted, false if entity not found
     */
    boolean delete(String id);

    /**
     * Check if an entity exists by ID
     * 
     * @param id The ID to check (must not be null or empty)
     * @return true if exists, false otherwise
     */
    boolean exists(String id);

    /**
     * Get count of all entities
     * 
     * @return Total number of entities
     */
    long count();

    /**
     * Search entities by keyword
     * 
     * @param keyword The keyword to search
     * @return List of matching entities (never null, empty list if no matches)
     */
    default List<T> search(String keyword) {
        return getAll(); // Default implementation, override in subinterfaces/implementations
    }

    /**
     * Clear all entities (use with caution)
     * 
     * @return true if successfully cleared
     */
    default boolean clear() {
        return false; // Default implementation, override if needed
    }
}
