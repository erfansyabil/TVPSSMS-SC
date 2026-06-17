package com.example.service;

import com.example.entity.Crew;
import org.springframework.stereotype.Repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import java.util.List;

@Repository
@Transactional
public class CrewDAO {

    @PersistenceContext
    private EntityManager entityManager;

    // Save or update a Crew entity
    public Crew saveOrUpdate(Crew crew) {
        if (crew.getId() == 0) {
            entityManager.persist(crew);
        } else {
            entityManager.merge(crew);
        }
        return crew;
    }

    // Retrieve all Crew entities
    public List<Crew> findAll() {
        return entityManager.createQuery("FROM Crew", Crew.class).getResultList();
    }

    // Retrieve a Crew entity by ID
    public Crew findById(int id) {
        return entityManager.find(Crew.class, id);
    }

    // Delete a Crew entity by ID
    public void deleteById(int id) {
        Crew crew = findById(id);
        if (crew != null) {
            entityManager.remove(crew);
        }
    }
    
    // Find crews by status
    public List<Crew> findByStatus(String status) {
        return entityManager.createQuery("FROM Crew WHERE status = :status", Crew.class)
                            .setParameter("status", status)
                            .getResultList();
    }

    // Update crew status
    public void updateStatus(int id, String status) {
        Crew crew = findById(id);
        if (crew != null) {
            crew.setStatus(status);
            entityManager.merge(crew);
        }
    }
    
    // Retrieve a Crew entity by username
    public Crew findByUsername(String username) {
        List<Crew> crews = entityManager.createQuery("FROM Crew WHERE username = :username", Crew.class)
                                        .setParameter("username", username)
                                        .getResultList();
        return crews.isEmpty() ? null : crews.get(0);
    }
    
    public List<Crew> searchCrews(String keyword) {
        String query = "FROM Crew WHERE LOWER(name) LIKE :keyword " +
                       "OR LOWER(position) LIKE :keyword " +
                       "OR CAST(year AS string) LIKE :keyword";
        return entityManager.createQuery(query, Crew.class)
                            .setParameter("keyword", "%" + keyword.toLowerCase() + "%")
                            .getResultList();
    }


}
