package org.example.jewelry_spring.service;

import org.example.jewelry_spring.model.Jewelry;
import org.example.jewelry_spring.repository.JewelryRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class JewelryService {

    private final JewelryRepository jewelryRepository;

    public List<Jewelry> findAll() {
        return jewelryRepository.findAllWithCategory(); // Изменено
    }

    public Jewelry getById(Long id) {
        return jewelryRepository.findByIdWithCategory(id) // Изменено
                .orElseThrow(() -> new EntityNotFoundException("Украшение не найдено: id=" + id));
    }

    @Transactional
    public Jewelry create(Jewelry jewelry) {
        jewelry.setId(null);
        return jewelryRepository.save(jewelry);
    }

    @Transactional
    public Jewelry update(Long id, Jewelry updated) {
        Jewelry existing = getById(id);

        existing.setTitle(updated.getTitle());
        existing.setMaterial(updated.getMaterial());
        existing.setPrice(updated.getPrice());
        existing.setCategory(updated.getCategory());

        return existing;
    }

    @Transactional
    public void delete(Long id) {
        if (!jewelryRepository.existsById(id)) {
            throw new EntityNotFoundException("Украшение не найдено: id=" + id);
        }
        jewelryRepository.deleteById(id);
    }
}