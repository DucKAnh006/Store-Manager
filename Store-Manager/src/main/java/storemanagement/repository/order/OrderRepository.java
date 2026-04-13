package storemanagement.repository.order;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import storemanagement.model.order.Order;


/**
 * Repository interface for managing Order entities in the database.
 * 
 * @author Nguyen Tran Duc Anh
 */
@Repository
public interface OrderRepository extends JpaRepository<Order, String> {

}
