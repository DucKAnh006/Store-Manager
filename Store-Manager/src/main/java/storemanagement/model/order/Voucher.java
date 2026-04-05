package storemanagement.model.order;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.time.LocalDate;

/**
 * Voucher class represents a discount voucher that can be applied to an order. It may contain properties such as voucher code, discount amount or percentage, and expiration date. This class can be expanded in the future to include methods for validating the voucher and calculating the discount on an order.
 * @author Nguyen Tran Duc Anh
 */
@Entity
@Table (name = "BM_Voucher")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Voucher {

    @Id
    @Column(name = "code")
    private String code; // Unique code for the voucher

    @Column(name = "is_percentage")
    private boolean isPercentage; // Indicates whether the discount is a percentage or a fixed amount

    @Column(name = "discount_value")
    private double discountValue; // The value of the discount (either percentage or fixed amount)

    @Column(name = "minimum_order_amount")
    private double minOrderValue; // Minimum order value required to use the voucher

    @Column(name = "expiration_date")
    private LocalDate expirationDate; // Expiration date of the voucher
}
