package storemanagement.model.order;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;


import storemanagement.model.product.*;

/**
 * CartItem class represents an item in the shopping cart. It contains a product, quantity, total price, and selection status.
 * It provides methods to calculate total price based on the product's price and discount, and to
 * update the total price whenever the product or quantity changes.
 * @author Nguyen Tran Duc Anh
 */
@Entity 
@Table (name = "BM_CartItem")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CartItem {

    @Id
    @Column (name = "cart_item_id")
    private String cartItemId; // Unique identifier for the cart item

    @ManyToOne
    @JoinColumn (name = "product_id")
    private Product product; // The product associated with this cart item

    @ManyToOne
    @JoinColumn (name = "cart_id")
    private Cart cart; // The cart to which this item belongs

    @Column (name = "quantity")
    private int quantity; // Quantity of the product in the cart

    @Column (name = "total_price")
    private double totalPrice; // Total price for this cart item (calculated as product price * quantity * (1 - discount))

    @Column (name = "selected")
    private boolean selected; // Indicates whether this cart item is selected for checkout

    /**
     * Private method to ensure consistency of total price. It calculates the total price based on the current product, quantity, price, and discount.
     */
    private void updateTotalPrice() {
        if (product != null) {
            // Use the price and discount stored in the cart item for consistency, rather than fetching from the product which may change
            this.totalPrice = product.getPrice() * quantity * (1 - product.getDiscount() / 100.0);
        } else {
            this.totalPrice = 0;
        }
    }
}
