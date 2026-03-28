package storemanagement.model.catalog;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

/**
 * Author class represents an author in the bookstore management system. It has properties such as id and name.
 * @author Nguyen Tran Duc Anh
 */
@Entity
@Table(name = "BM_Author")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Author {

    @Id
    @Column (name = "author_id")
    private String id; // Unique identifier for the author

    @Column (name = "name")
    private String name; // Name of the author
}
