package storemanagement.repository.product;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import storemanagement.model.product.Combo;

/**
 * Repository interface for managing Combo entities in the database.
 *
 * @author Nguyen Tran Duc Anh
 */
@Repository
public interface ComboRepository extends JpaRepository<Combo, String> {
}