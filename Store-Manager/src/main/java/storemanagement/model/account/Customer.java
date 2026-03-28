package storemanagement.model.account;

import java.util.ArrayList;
import java.util.List;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

/**
 * Customer class represents a customer account in the bookstore management
 * system. It extends the Accounts class and has additional properties such as order
 * 
 * @author Nguyen Tran Duc Anh
 */
@Entity
@Table(name = "BM_Customer")
@PrimaryKeyJoinColumn(name = "customer_id")
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class Customer extends Accounts {

    @Column (name = "phone_number")
    private String phoneNumber; // Customer's phone number

    @Column (name = "email")
    private String email; // Customer's email address

    @Column (name = "loyalty_point")
    private int loyaltyPoints;  // Loyalty points accumulated by the customer

    @OneToMany(mappedBy = "customer", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Address> addresses = new ArrayList<>(); // List of customer's addresses

}