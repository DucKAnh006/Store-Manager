package storemanagement.repository.product;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import storemanagement.model.product.Book;

@Repository
public interface BookRepository extends JpaRepository<Book, String> {
}