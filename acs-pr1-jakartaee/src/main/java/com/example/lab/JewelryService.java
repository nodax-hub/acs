package com.example.lab;

import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import java.util.List;

@Stateless
public class JewelryService {

    @PersistenceContext(unitName = "labPU")
    private EntityManager em;

    // Найти все украшения
    public List<Jewelry> findAll() {
        return em.createQuery(
                "select b from Jewelry b join fetch b.category order by b.id",
                Jewelry.class
        ).getResultList();
    }

    // Найти украшение по ID
    public Jewelry find(Long id) {
        return em.find(Jewelry.class, id);
    }

    // Добавить новую украшение
    public void create(Jewelry b) {
        em.persist(b);
    }

    // Обновить данные украшения
    public Jewelry update(Jewelry b) {
        return em.merge(b);
    }

    // Удалить украшение
    public void delete(Long id) {
        Jewelry b = em.find(Jewelry.class, id);
        if (b != null) {
            em.remove(b);
        }
    }
}
