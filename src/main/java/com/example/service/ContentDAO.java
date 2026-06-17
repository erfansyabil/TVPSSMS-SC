package com.example.service;

import java.util.List;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;

import org.springframework.stereotype.Repository;

import com.example.entity.Content;

@Repository
@Transactional
public class ContentDAO {

    @PersistenceContext
    private EntityManager entityManager;

    public List<Content> findAll() {
        return entityManager.createQuery("FROM Content", Content.class).getResultList();
    }

    public Content findById(int id) {
        return entityManager.find(Content.class, id);
    }

    public void save(Content content) {
        entityManager.persist(content);
    }

    public void update(Content content) {
        entityManager.merge(content);
    }

    public void deleteById(int contentID) {
        Content content = entityManager.find(Content.class, contentID);
        if (content != null) {
            entityManager.remove(content);
        }
    }

    public List<Content> searchByTitle(String title) {
        return entityManager.createQuery(
                "FROM Content WHERE LOWER(contentTitle) LIKE :title", Content.class)
                .setParameter("title", "%" + title.toLowerCase() + "%")
                .getResultList();
    }

    public List<Content> findByOwner(String owner) {
        return entityManager.createQuery(
                "FROM Content WHERE LOWER(owner) = :owner", Content.class)
                .setParameter("owner", owner.toLowerCase())
                .getResultList();
    }
}
