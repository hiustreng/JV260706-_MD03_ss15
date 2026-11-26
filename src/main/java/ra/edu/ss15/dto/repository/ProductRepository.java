package ra.edu.ss15.dto.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ra.edu.ss15.dto.entity.Product;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
}
