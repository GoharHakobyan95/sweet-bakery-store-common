package am.itspace.sweetbakerystorecommon.service;

import am.itspace.sweetbakerystorecommon.entity.FavoriteProduct;
import am.itspace.sweetbakerystorecommon.entity.User;
import am.itspace.sweetbakerystorecommon.repository.FavoriteProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@RequiredArgsConstructor
public class FavoriteProductService {
    private final FavoriteProductRepository favoriteProductRepository;

    public Long getCountOfFavoriteProducts() {
        return favoriteProductRepository.count();
    }

    public Page<FavoriteProduct> findPaginated(Pageable pageable) {
        return favoriteProductRepository.findAll(pageable);
    }

    public List<FavoriteProduct> getByUser(User user) {
        return favoriteProductRepository.getByUser(user);
    }
}
