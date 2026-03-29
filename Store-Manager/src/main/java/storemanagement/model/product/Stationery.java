package storemanagement.model.product;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;

/**
 * Represents non-book items like pens, rulers, toys, and souvenirs.
 * Extends the abstract Product class.
 *
 * @author Nguyen Tran Duc Anh
 */
@Entity
@Table(name = "BM_Stationery")
@PrimaryKeyJoinColumn(name = "product_id")
@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Stationery extends Product {
    
    @Column(name = "manufacturer")
    private String manufacturer; // Manufacturer of the stationery item

    @Column(name = "material")
    private String material; // Material of the stationery item (e.g., plastic, metal, wood)
}