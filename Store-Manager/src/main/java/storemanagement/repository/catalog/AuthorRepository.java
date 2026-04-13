package storemanagement.repository.catalog;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import storemanagement.model.catalog.Author;

/**
 * Repository interface for managing Author entities in the database.
 *
 * @author Nguyen Tran Duc Anh
 */
@Repository
public interface AuthorRepository extends JpaRepository<Author, String> {
}