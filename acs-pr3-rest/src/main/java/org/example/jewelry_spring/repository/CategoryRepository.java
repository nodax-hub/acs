package org.example.jewelry_spring.repository;

import org.example.jewelry_spring.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Long> {
}