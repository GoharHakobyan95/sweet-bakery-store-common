package am.itspace.sweetbakerystorecommon.service;

import am.itspace.sweetbakerystorecommon.entity.Category;
import am.itspace.sweetbakerystorecommon.entity.FavoriteProduct;
import am.itspace.sweetbakerystorecommon.entity.Product;
import am.itspace.sweetbakerystorecommon.entity.User;
import am.itspace.sweetbakerystorecommon.repository.CategoryRepository;
import am.itspace.sweetbakerystorecommon.repository.FavoriteProductRepository;
import am.itspace.sweetbakerystorecommon.repository.ProductRepository;
import am.itspace.sweetbakerystorecommon.security.CurrentUser;
import lombok.RequiredArgsConstructor;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;
    private final FavoriteProductRepository favoriteProductRepository;
    @Value("${sweet.bakery.store.images.folder}")
    private String folderPath;


    public Page<Product> findPaginated(Pageable pageable) {
        Page<Product> productPages = productRepository.findAll(pageable);
        return new PageImpl<>(productPages.getContent(), pageable, productPages.getSize());
    }

    public byte[] getProductImage(String fileName) throws IOException {
        InputStream inputStream = new FileInputStream(folderPath + File.separator + fileName);
        return IOUtils.toByteArray(inputStream);
    }

    public void deleteById(int id) {
        productRepository.deleteById(id);
    }

    public Optional<Product> findById(int id) {
        return productRepository.findById(id);
    }

    public Product saveProduct(Product product, MultipartFile file, User user) throws IOException {
        if (!file.isEmpty() && file.getSize() > 0) {
            String filename = System.currentTimeMillis() + "_" + file.getOriginalFilename();
            File newFile = new File(folderPath + File.separator + filename);
            file.transferTo(newFile);
            product.setProductPic(filename);
        }
        product.setUser(user);
        Category category = categoryRepository.getReferenceById(product.getCategory().getId());
        product.setCategory(category);
        productRepository.save(product);

        return productRepository.save(product);
    }


    public Long getCountOfProducts() {
        return productRepository.count();
    }

    public Double getAmount() {
        return productRepository.totalSale();
    }

    public void addFavoriteProduct(User user,
                                   @RequestParam("productId") Integer productId) {
        Optional<Product> optionalProduct = productRepository.findById(productId);
        optionalProduct.ifPresent(product -> {
            Optional<FavoriteProduct> favoriteProduct = favoriteProductRepository
                    .findByUserAndProduct(user, product);
            if (favoriteProduct.isEmpty()) {
                FavoriteProduct favProduct = new FavoriteProduct();
                favProduct.setUser(user);
                favProduct.setCreateAt(new Date());
                favProduct.setProduct(product);
                favoriteProductRepository.save(favProduct);
            }
        });

    }

    public List<Product> getAllProducts(String productList) {
        if (productList != null && !productList.equals(" ")) {
            return productRepository.findAll(productList);
        }
        return productRepository.findAll();
    }

    public List<Product> getProductList() {
        return productRepository.findAll();
    }


    public void deleteProductById(int id, CurrentUser currentUser) {
        Optional<Product> byId = productRepository.findById(id);
        if (byId.isPresent() && currentUser.getUser().getId() == byId.get().getUser().getId()) {
            productRepository.deleteById(id);
        }
        ResponseEntity.notFound().build();
    }


}

