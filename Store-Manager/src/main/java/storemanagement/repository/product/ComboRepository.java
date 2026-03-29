package storemanagement.repository.product;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import storemanagement.model.product.Combo;

@Repository
public interface ComboRepository extends JpaRepository<Combo, String> {
}