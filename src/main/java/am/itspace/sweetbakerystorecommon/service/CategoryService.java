package am.itspace.sweetbakerystorecommon.service;

import am.itspace.sweetbakerystorecommon.dto.categoryDto.CategoryResponseDto;
import am.itspace.sweetbakerystorecommon.entity.Category;
import am.itspace.sweetbakerystorecommon.entity.User;
import am.itspace.sweetbakerystorecommon.repository.CategoryRepository;
import am.itspace.sweetbakerystorecommon.security.CurrentUser;
import lombok.RequiredArgsConstructor;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CategoryService {

    private final CategoryRepository categoryRepository;

    public Page<Category> findPaginated(Pageable pageable) {
        Page<Category> categoryPages = categoryRepository.findAll(pageable);
        return new PageImpl<>(categoryPages.getContent(), pageable, categoryPages.getSize());
    }

    public Optional<Category> findByName(String name) {
        return categoryRepository.findByName(name);

    }

    public void deleteById(int id, User user) {
        Optional<Category> byId = categoryRepository.findById(id);
        if (byId.isPresent() && user.getId() == byId.get().getUser().getId()) {
            categoryRepository.deleteById(id);
        }
        ResponseEntity.notFound().build();
    }

    public void save(Category category, User user) throws Exception {
        category.setUser(user);
        categoryRepository.save(category);
    }

    public Optional<Category> findByIdForEdit(int id) {
        return categoryRepository.findById(id);
    }

    public List<Category> findAll() {
        return categoryRepository.findAll();
    }

    public Long getCountOfCategories() {
        return categoryRepository.count();
    }

    public Category findByCategoryId(int id) {
        Optional<Category> byId = categoryRepository.findById(id);
        return byId.orElse(null);
    }


    public Category saveCategory(Category category) {
        if(category==null){
            throw new RuntimeException("Category can't be null!");
        }
        return categoryRepository.save(category);
    }

    public Optional<Category> findById(int id) {
        return categoryRepository.findById(id);
    }


}
