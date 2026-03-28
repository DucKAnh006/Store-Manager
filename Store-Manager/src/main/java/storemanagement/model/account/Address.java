package storemanagement.model.account;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.AllArgsConstructor;

import storemanagement.model.account.Customer;

/**
 * Customer class represents a customer account in the bookstore management
 * system. It extends the Accounts class and has additional properties such as order
 * 
 * @author Nguyen Tran Duc Anh
 */
@Entity
@Table(name = "BM_Address")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Address {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "address_id")
    private Integer id; // Unique identifier for the customer's address

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_id")
    private Customer customer; // Customer's Id

    @Column (name = "address")
    private String address; // Customer's customer's address

}
