package am.itspace.sweetbakerystorecommon.repository;

import am.itspace.sweetbakerystorecommon.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

public interface CategoryRepository extends JpaRepository<Category, Integer> {
    Optional<Category> findByName(String name);

    long count();

}
