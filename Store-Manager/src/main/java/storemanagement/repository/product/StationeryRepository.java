package storemanagement.repository.product;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import storemanagement.model.product.Stationery;

/**
 * Repository interface for managing Stationery entities in the database.
 * 
 * @author Nguyen Tran Duc Anh
 */
@Repository
public interface StationeryRepository  extends JpaRepository<Stationery, String> {
    
}