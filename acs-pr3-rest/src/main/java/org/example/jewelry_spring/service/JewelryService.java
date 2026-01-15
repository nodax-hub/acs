package org.example.jewelry_spring.service;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.example.jewelry_spring.model.Category;
import org.example.jewelry_spring.model.Jewelry;
import org.example.jewelry_spring.repository.JewelryRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class JewelryService {

    private final JewelryRepository jewelryRepository;

    public List<Jewelry> findAllWithCategory() {
        return jewelryRepository.findAllWithCategory();
    }

    public List<Jewelry> findAllByCategoryIdWithCategory(Long categoryId) {
        return jewelryRepository.findAllByCategoryIdWithCategory(categoryId);
    }

    public Jewelry getByIdWithCategory(Long id) {
        return jewelryRepository.findByIdWithCategory(id)
                .orElseThrow(() -> new EntityNotFoundException("Jewelry not found: id=" + id));
    }

    @Transactional
    public Jewelry create(Jewelry jewelry) {
        jewelry.setId(null);
        return jewelryRepository.save(jewelry);
    }

    @Transactional
    public Jewelry update(Long id, Jewelry updated) {
        Jewelry existing = jewelryRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Jewelry not found: id=" + id));

        existing.setTitle(updated.getTitle());
        existing.setPrice(updated.getPrice());
        existing.setMaterial(updated.getMaterial());
        existing.setCategory(updated.getCategory()); // может быть null

        return existing;
    }

    @Transactional
    public void delete(Long id) {
        jewelryRepository.deleteById(id);
    }
}