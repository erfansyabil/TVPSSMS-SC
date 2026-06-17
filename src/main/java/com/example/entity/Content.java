package com.example.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "content")
public class Content {
    
    @Id
    @Column(name = "contentID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)  
    private int contentID;

    @Column(name = "contentTitle")
    private String contentTitle;

    @Column(name = "contentDescription")
    private String contentDescription;

    @Column(name = "contentUploadDate")
    private String contentUploadDate;

    @Column(name = "contentType")
    private String contentType;

    @Column(name = "programID")
    private String programID;

    @Column(name = "link")
    private String link;

    @Column(name = "owner")
    private String owner;  // Assuming owner is a simple string, adjust if needed

    public Content() {}

    public Content(int contentID, String contentTitle, String contentDescription, String contentUploadDate, String contentType, String programID, String link, String owner) {
        this.contentID = contentID;
        this.contentTitle = contentTitle;
        this.contentDescription = contentDescription;
        this.contentUploadDate = contentUploadDate;
        this.contentType = contentType;
        this.programID = programID;
        this.link = link;
        this.owner = owner;
    }

    // Getter and Setter for contentID
    public int getContentID() {
        return contentID;
    }

    public void setContentID(int contentID) {
        this.contentID = contentID;
    }

    // Getter and Setter for contentTitle
    public String getContentTitle() {
        return contentTitle;
    }

    public void setContentTitle(String contentTitle) {
        this.contentTitle = contentTitle;
    }

    // Getter and Setter for contentDescription
    public String getContentDescription() {
        return contentDescription;
    }

    public void setContentDescription(String contentDescription) {
        this.contentDescription = contentDescription;
    }

    // Getter and Setter for contentUploadDate
    public String getContentUploadDate() {
        return contentUploadDate;
    }

    public void setContentUploadDate(String contentUploadDate) {
        this.contentUploadDate = contentUploadDate;
    }

    // Getter and Setter for contentType
    public String getContentType() {
        return contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    // Getter and Setter for programID
    public String getProgramID() {
        return programID;
    }

    public void setProgramID(String programID) {
        this.programID = programID;
    }

    // Getter and Setter for link
    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    // Getter and Setter for owner
    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }
}
