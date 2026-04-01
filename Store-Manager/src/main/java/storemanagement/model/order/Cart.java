package storemanagement.model.order;

import jakarta.persistence.*;
import lombok.Data;
import lombok.AllArgsConstructor;
import java.util.List;

import storemanagement.model.account.Accounts;

/**
 * Represents a shopping cart holding various products and their quantities.
 *
 * @author Nguyen Tran Duc Anh
 */
@Entity
@Table (name = "BM_Cart")
@Data
@AllArgsConstructor
public class Cart {

    @Id
    @Column (name = "cart_id")
    private String cartId; // unique identifier for the cart

    @OneToOne
    @JoinColumn (name = "account_id")
    private Accounts customer; // Unique identifier for the customer associated with this cart

    @ManyToMany
    @JoinTable(
        name = "BM_CartItem",
        joinColumns = @JoinColumn(name = "cart_id"),
        inverseJoinColumns = @JoinColumn(name = "product_id")
    )
    private final List<CartItem> cartItems; // List to store items in the cart, each item includes product details and quantity

}