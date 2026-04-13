package storemanagement.repository.account;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import storemanagement.model.account.Customer;

/**
 * Repository interface for Customer entity, providing CRUD operations and database interactions.
 * 
 * @author Nguyen Tran Duc Anh
 */
@Repository
public interface CustomerRepository extends JpaRepository<Customer, String> {

}
