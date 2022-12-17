package am.itspace.sweetbakerystorecommon.service;

import am.itspace.sweetbakerystorecommon.dto.BasketDto;
import am.itspace.sweetbakerystorecommon.dto.BasketRequest;
import am.itspace.sweetbakerystorecommon.entity.Category;
import am.itspace.sweetbakerystorecommon.entity.FavoriteProduct;
import am.itspace.sweetbakerystorecommon.entity.Product;
import am.itspace.sweetbakerystorecommon.entity.User;
import am.itspace.sweetbakerystorecommon.repository.CategoryRepository;
import am.itspace.sweetbakerystorecommon.repository.FavoriteProductRepository;
import am.itspace.sweetbakerystorecommon.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
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

    @Resource(name = "basketDto")
    private BasketDto basketDto;


    public Page<Product> findPaginated(Pageable pageable) {
        return productRepository.findAll(pageable);
    }

    public byte[] getProductImage(String fileName) throws IOException {
        InputStream inputStream = new FileInputStream(folderPath + File.separator + fileName);
        return IOUtils.toByteArray(inputStream);
    }

    public void deleteById(int id) {
        productRepository.deleteById(id);
    }


    public void save(Product product, MultipartFile file, User user) throws IOException {
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
    }

    public Optional<Product> findById(int id) {
        return productRepository.findById(id);
    }

    public Long getCountOfProducts() {
        return productRepository.count();
    }

    public Double getAmount() {
        return productRepository.totalSale();
    }


    public void addBasket(User user, int productId, BasketRequest basketRequest) {
        Optional<Product> byProductId = productRepository.findById(productId);
        byProductId.ifPresent(product -> product.setId(productId));
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

    public void save(Product product,  User user) {
        Optional<Product> editedProduct = productRepository.findById(product.getId());
        if (editedProduct.isPresent()) {
            product.setUser(user);
            productRepository.save(product);
        }
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
}

