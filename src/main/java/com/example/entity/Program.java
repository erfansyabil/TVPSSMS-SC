package com.example.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "program") // Maps to the "program" table in the database
public class Program {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Assuming the primary key is auto-incremented
    @Column(name = "id") // Maps to the "id" column in the table
    private Long id;

    @Column(name = "program_name") // Maps to the "program_name" column in the table
    private String programName;
    
    @Column(name = "location") // Maps to the "location" column in the table
    private String location;

    @Column(name = "description") // Maps to the "description" column in the table
    private String description;

    @Column(name = "date") // Maps to the "date" column in the table
    private String date; // Or LocalDate/LocalDateTime based on your schema

    // Getters and setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getProgramName() {
        return programName;
    }

    public void setProgramName(String programName) {
        this.programName = programName;
    }
    
    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }
    

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
