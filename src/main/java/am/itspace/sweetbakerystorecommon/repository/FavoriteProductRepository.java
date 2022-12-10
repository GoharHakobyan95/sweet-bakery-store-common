package am.itspace.sweetbakerystorecommon.repository;


import am.itspace.sweetbakerystorecommon.entity.FavoriteProduct;
import am.itspace.sweetbakerystorecommon.entity.Product;
import am.itspace.sweetbakerystorecommon.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface FavoriteProductRepository extends JpaRepository<FavoriteProduct, Integer> {
    long count();

    Optional<FavoriteProduct> findByUserAndProduct(User user, Product product);

    List<FavoriteProduct> getByUser(User user);
}
