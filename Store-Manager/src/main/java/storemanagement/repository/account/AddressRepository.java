package storemanagement.repository.account;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import storemanagement.model.account.Address;

/**
 * Repository interface for managing Customer's Address entities in the database.
 * 
 * @author Nguyen Tran Duc Anh
 */
@Repository
public interface AddressRepository extends JpaRepository<Address, Integer>{

}
