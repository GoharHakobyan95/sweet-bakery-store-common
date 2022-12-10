package am.itspace.sweetbakerystorecommon.service;

import am.itspace.sweetbakerystorecommon.entity.Category;
import am.itspace.sweetbakerystorecommon.entity.User;
import am.itspace.sweetbakerystorecommon.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CategoryService {

    private final CategoryRepository categoryRepository;

    public Page<Category> findPaginated(Pageable pageable) {

        return categoryRepository.findAll(pageable);
    }

    public Optional<Category> findByName(String name) {
        return categoryRepository.findByName(name);

    }

    public void deleteById(int id) {
        categoryRepository.deleteById(id);
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
}
