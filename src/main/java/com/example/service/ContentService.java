package com.example.service;

import com.example.entity.Content;
import com.example.service.ContentDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ContentService {

    private final ContentDAO contentDao;

    @Autowired
    public ContentService(ContentDAO contentDao) {
        this.contentDao = contentDao;
    }

    // 1 - Get all content
    public List<Content> getAllContent() {
        return contentDao.findAll();
    }

    // 2 - Get content by ID
    public Content getContentById(int i) {
        return contentDao.findById(i);
    }

    // 3 - Create a new content
    public void createContent(Content content) {
        contentDao.save(content);
    }

    // 4 - Update an existing content
    public void updateContent(Content content) {
        contentDao.update(content);
    }

    // 5 - Delete content by ID
    public void deleteContentById(int contentID) {
        contentDao.deleteById(contentID);
    }

    // 6 - Search for content by title
    public List<Content> searchContentByTitle(String title) {
        return contentDao.searchByTitle(title);
    }
    
    public List<Content> getContentbyOwner(String owner) {
        return contentDao.findByOwner(owner);
    }
}
