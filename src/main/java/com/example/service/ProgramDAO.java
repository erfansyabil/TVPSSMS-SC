package com.example.service;

import java.util.List;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;

import org.springframework.stereotype.Repository;

import com.example.entity.Program;

@Repository
@Transactional
public class ProgramDAO {

    @PersistenceContext
    private EntityManager entityManager;

    public List<Program> findAll() {
        return entityManager.createQuery("FROM Program", Program.class).getResultList();
    }

    public Program findById(long id) {
        return entityManager.find(Program.class, id);
    }

    public Program findByName(String programName) {
        List<Program> results = entityManager.createQuery(
                "FROM Program WHERE programName = :programName", Program.class)
                .setParameter("programName", programName)
                .getResultList();
        return results.isEmpty() ? null : results.get(0);
    }

    public void save(Program program) {
        entityManager.persist(program);
    }

    public void update(Program program) {
        entityManager.merge(program);
    }

    public void deleteById(long id) {
        Program program = entityManager.find(Program.class, id);
        if (program != null) {
            entityManager.remove(program);
        }
    }

    public List<Program> searchByName(String partialName) {
        return entityManager.createQuery(
                "FROM Program WHERE LOWER(programName) LIKE :partialName", Program.class)
                .setParameter("partialName", "%" + partialName.toLowerCase() + "%")
                .getResultList();
    }
}
