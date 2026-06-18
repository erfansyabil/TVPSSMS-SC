package com.example.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.entity.Content;
import com.example.service.ContentDAO;

@RestController
@RequestMapping("/api/content")
public class StudentRestController {

    @Autowired
    private ContentDAO contentDAO;

    // 1. Retrieve / List all records
    @GetMapping
    public ResponseEntity<List<Content>> getAllContent() {
        List<Content> contents = contentDAO.findAll();
        return ResponseEntity.ok(contents);
    }

    // 2. Retrieve record by ID
    @GetMapping("/{id}")
    public ResponseEntity<Content> getContentById(@PathVariable("id") int id) {
        Content content = contentDAO.findById(id);
        if (content != null) {
            return ResponseEntity.ok(content);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
