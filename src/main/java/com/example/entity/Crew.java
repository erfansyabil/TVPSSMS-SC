package com.example.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "crew")
public class Crew {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "username", nullable = false, unique = true)
    private String username;

    @Column(name = "email", nullable = false, unique = true)
    private String email;

    @Column(name = "phone_no")
    private String phoneNo;

    @Column(name = "position")
    private String position;

    @Column(name = "year")
    private String year;

    @Column(name = "status", nullable = false)
    private String status = "Pending"; // Default to "Pending"

    // Default constructor
    public Crew() {
    }

    // Parameterized constructor
    public Crew(String name, String username, String email, String phoneNo, String position, String year) {
        this.name = name;
        this.username = username;
        this.email = email;
        this.phoneNo = phoneNo;
        this.position = position;
        this.year = year;
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

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNo() {
        return phoneNo;
    }

    public void setPhoneNo(String phoneNo) {
        this.phoneNo = phoneNo;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    // Override toString method for debugging
    @Override
    public String toString() {
        return "Crew [id=" + id + ", name=" + name + ", username=" + username + ", email=" + email + ", phoneNo="
                + phoneNo + ", position=" + position + ", year=" + year + ", status=" + status + "]";
    }
}
