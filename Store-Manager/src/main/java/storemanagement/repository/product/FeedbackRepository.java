package storemanagement.repository.product;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import storemanagement.model.product.Feedback;

/**
 * @author Nguyen Tran Duc Anh
 */
@Repository
public interface FeedbackRepository extends JpaRepository<Feedback, String>{
    
}