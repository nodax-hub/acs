package com.example.lab;

import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import java.util.List;

@Stateless
public class CategoryService {

    @PersistenceContext(unitName = "labPU")
    private EntityManager em;

    // Найти все категории
    public List<Category> findAll() {
        return em.createQuery("select a from Category a order by a.id", Category.class)
                 .getResultList();
    }

    // Найти украшение по ID
    public Category find(Long id) {
        return em.find(Category.class, id);
    }

    // Добавить нового украшение
    public void create(Category a) {
        em.persist(a);
    }

    // Обновить данные украшение
    public Category update(Category a) {
        return em.merge(a);
    }

    // Удалить украшение
    public void delete(Long id) {
        Category a = em.find(Category.class, id);
        if (a != null) {
            em.remove(a);
        }
    }
}
