package com.example.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "task")
public class Task {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "name", nullable = false)
    private String name;

    @ManyToOne
    @JoinColumn(name = "crew_id", nullable = false) // Links to Crew's ID
    private Crew crew;

    // Default constructor
    public Task() {
    }

    // Parameterized constructor
    public Task(String name, Crew crew) {
        this.name = name;
        this.crew = crew;
    }

    // Getters and setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Crew getCrew() {
        return crew;
    }

    public void setCrew(Crew crew) {
        this.crew = crew;
    }

    // Dynamically fetch role from Crew entity
    public String getRole() {
        return crew != null && crew.getPosition() != null ? crew.getPosition() : "No role available";
    }
    

    @Override
    public String toString() {
        return "Task [id=" + id + ", name=" + name + ", role=" + getRole() + ", crew=" + crew.getName() + "]";
    }
}
