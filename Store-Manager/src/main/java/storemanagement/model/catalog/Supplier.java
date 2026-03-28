package storemanagement.model.catalog;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

/**
 * Supplier class represents a supplier in the bookstore management system. It
 * has properties such as id and name.
 * * @author Nguyen Tran Duc Anh
 */
@Entity
@Table(name = "BM_Supplier")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Supplier {
    
    @Id
    @Column(name = "supplier_id")
    private String id; // Unique identifier for the supplier

    @Column(name = "name")
    private String name; // Name of the supplier
}
