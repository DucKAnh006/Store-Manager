package storemanagement.repository.account;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import storemanagement.model.account.Accounts;

/**
 * @author Nguyen Tran Duc Anh
 */
@Repository
public interface AccountRepository extends JpaRepository<Accounts, String> {

}
