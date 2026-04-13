package storemanagement.repository.account;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import storemanagement.model.account.Accounts;

/**
 * Repository interface for managing Accounts entities in the database.
 * 
 * @author Nguyen Tran Duc Anh
 */
@Repository
public interface AccountRepository extends JpaRepository<Accounts, String> {

}
