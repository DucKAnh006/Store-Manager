package storemanagement.repository.product;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import storemanagement.model.product.Product;

/**
 * @author Nguyen Tran Duc Anh
 */
@Repository
public interface ProductRepository extends JpaRepository<Product, String> {
}
