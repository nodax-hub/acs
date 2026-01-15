package org.example.jewelry_spring.service;

import org.example.jewelry_spring.model.Category;
import org.example.jewelry_spring.repository.CategoryRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CategoryService {

    private final CategoryRepository categoryRepository;

    public List<Category> findAll() {
        return categoryRepository.findAll();
    }

    public Category getById(Long id) {
        return categoryRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Категория не найдена: id=" + id));
    }

    @Transactional
    public Category create(Category category) {
        category.setId(null);
        return categoryRepository.save(category);
    }

    @Transactional
    public Category update(Long id, Category updated) {
        Category existing = getById(id);
        existing.setName(updated.getName());
        return existing;
    }

    @Transactional
    public void delete(Long id) {
        if (!categoryRepository.existsById(id)) {
            throw new EntityNotFoundException("Категория не найдена: id=" + id);
        }
        categoryRepository.deleteById(id);
    }
}