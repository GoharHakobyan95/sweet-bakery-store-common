package am.itspace.sweetbakerystorecommon.repository;

import am.itspace.sweetbakerystorecommon.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Integer> {
    long count();
    @Query(value = "SELECT SUM(p.price * uo.count) FROM user_order uo\n" +
            "JOIN product p ON uo.product_id = p.id", nativeQuery = true)
    double totalSale();

    @Query("SELECT p FROM Product p WHERE CONCAT(p.name, p.category.name,p.description) LIKE %?1%")
    List<Product> findAll(String productList);
}
