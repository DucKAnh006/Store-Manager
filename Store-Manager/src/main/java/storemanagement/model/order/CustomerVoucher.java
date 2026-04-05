package storemanagement.model.order;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

/**
 * CustomerVoucher class represents the association between a customer and a
 * voucher. It can be used to track which vouchers have been assigned to which
 * customers, and whether they have been used or not. This class can be expanded
 * in the future to include properties such as usage status, date of assignment,
 * and methods for validating the voucher for a specific customer.
 * 
 * @author Nguyen Tran Duc Anh
 */
@Entity
@Table (name = "BM_UsedVoucher")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CustomerVoucher {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id; // Unique identifier for the customer-voucher association

    @ManyToOne
    @JoinColumn(name = "customer_id")
    private String customerId; // Identifier for the customer

    @ManyToOne
    @JoinColumn(name = "code")
    private String voucherCode; // Code of the voucher assigned to the customer

    @Column (name = "is_used")
    private boolean isUsed; // Indicates whether the voucher has been used by the customer
}
