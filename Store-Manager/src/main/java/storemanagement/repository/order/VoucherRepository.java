package storemanagement.repository.order;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import storemanagement.model.order.Voucher;

/**
 * Repository interface for managing Voucher entities in the database.
 *
 * @author Nguyen Tran Duc Anh
 */
@Repository
public interface VoucherRepository extends JpaRepository<Voucher, String> {

}
