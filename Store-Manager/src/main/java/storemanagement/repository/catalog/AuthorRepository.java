package storemanagement.repository.catalog;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import storemanagement.model.catalog.Author;

@Repository
public interface AuthorRepository extends JpaRepository<Author, String> {
}