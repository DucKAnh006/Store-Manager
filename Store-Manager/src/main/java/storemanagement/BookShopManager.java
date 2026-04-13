package storemanagement;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EntityScan(basePackages = "storemanagement.model")
@EnableJpaRepositories(basePackages = "storemanagement.repository")

/**
 * @author Nguyen Tran Duc Anh
 */
public class BookShopManager {
    public static void main(String[] args) {
        System.out.println("This is an application to manage book shop!");
    }
}
