package storemanagement.model.account;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

/**
 * 
 * @author Nguyen Tran Duc Anh
 */
@Entity
@Table(name = "BM_Account")
@Inheritance(strategy = InheritanceType.JOINED)
@Data
@NoArgsConstructor
@AllArgsConstructor
public abstract class Accounts {

    @Id
    @Column (name = "account_id")
    private String id; // Unique identifier for the account

    @Column (name = "name")
    private String name; // Name of the account holder

    @Column (name = "account", unique = true)
    private String account; // Unique user ID for login

    @Column (name = "password")
    private String password; // Password for authentication

}
