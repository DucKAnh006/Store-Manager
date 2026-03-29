package storemanagement.repository.catalog;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import storemanagement.model.catalog.Supplier;

@Repository
public interface SupplierRepository extends JpaRepository<Supplier, String> {
}