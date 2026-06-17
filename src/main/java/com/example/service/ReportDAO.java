package com.example.service;

import java.util.List;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;

import org.springframework.stereotype.Repository;

import com.example.entity.Report;

@Repository
@Transactional
public class ReportDAO {

    @PersistenceContext
    private EntityManager entityManager;

    public void save(Report report) {
        entityManager.persist(report);
    }

    public List<Report> findAll() {
        return entityManager.createQuery("FROM Report", Report.class).getResultList();
    }

    public Report findById(long id) {
        return entityManager.find(Report.class, id);
    }

    public Report findByProgramId(Long programId) {
        List<Report> results = entityManager.createQuery(
                "FROM Report WHERE program.id = :programId", Report.class)
                .setParameter("programId", programId)
                .getResultList();
        return results.isEmpty() ? null : results.get(0);
    }
}
