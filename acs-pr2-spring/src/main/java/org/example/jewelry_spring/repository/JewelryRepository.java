package org.example.jewelry_spring.repository;

import org.example.jewelry_spring.model.Jewelry;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface JewelryRepository extends JpaRepository<Jewelry, Long> {
    @Query("select j from Jewelry j left join fetch j.category")
    List<Jewelry> findAllWithCategory();

    @Query("select j from Jewelry j left join fetch j.category where j.id = :id")
    Optional<Jewelry> findByIdWithCategory(@Param("id") Long id);
}