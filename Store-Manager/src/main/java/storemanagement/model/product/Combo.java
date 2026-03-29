package storemanagement.model.product;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import java.util.ArrayList;
import java.util.List;

/**
 * Represents a bundled collection of books sold together.
 * Extends the abstract Product class.
 *
 * @author Nguyen Tran Duc Anh
 */
@Entity
@Table(name = "BM_Combo")
@PrimaryKeyJoinColumn(name = "combo_id")
@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Combo extends Product {

    @ManyToMany
    @JoinTable(
        name = "combo_product",
        joinColumns = @JoinColumn(name = "combo_id"),
        inverseJoinColumns = @JoinColumn(name = "product_id")
    )
    private List<Product> includedProducts = new ArrayList<>();
}