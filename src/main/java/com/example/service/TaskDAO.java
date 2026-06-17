package com.example.service;

import com.example.entity.Task;
import org.springframework.stereotype.Repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import java.util.List;

@Repository
@Transactional
public class TaskDAO {

    @PersistenceContext
    private EntityManager entityManager;

    // Save or update a Task entity
    public Task saveOrUpdate(Task task) {
        if (task.getId() == 0) {
            entityManager.persist(task); // New task, so we persist
        } else {
            entityManager.merge(task); // Existing task, so we merge
        }
        return task;
    }

    // Retrieve all Task entities
    public List<Task> findAll() {
        return entityManager.createQuery("FROM Task", Task.class).getResultList();
    }

    // Retrieve a Task entity by ID
    public Task findById(int id) {
        return entityManager.find(Task.class, id);
    }

    // Delete a Task entity by ID
    public boolean deleteById(int id) {
        Task task = findById(id);
        if (task != null) {
            entityManager.remove(task); // Delete the task if found
            return true; // Return true if deletion was successful
        }
        return false; // Return false if task was not found
    }
    
    public List<Task> findByCrewNameOrTaskName(String search) {
        String query = "FROM Task t WHERE LOWER(t.name) LIKE LOWER(:search) OR LOWER(t.crew.name) LIKE LOWER(:search)";
        return entityManager.createQuery(query, Task.class)
                            .setParameter("search", "%" + search + "%")
                            .getResultList();
    }

}
