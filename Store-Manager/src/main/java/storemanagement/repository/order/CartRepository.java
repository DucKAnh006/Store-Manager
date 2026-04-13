package storemanagement.repository.order;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import storemanagement.model.order.Cart;

/**
 * Repository interface for managing Cart entities in the database.
 * 
 * @author Nguyen Tran Duc Anh
 */
@Repository
public interface CartRepository extends JpaRepository<Cart, String> {

}
