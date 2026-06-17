package com.example.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import com.example.entity.Content;
import com.example.service.ContentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/content")
public class ContentController {

    private final ContentService contentService;

    @Autowired
    public ContentController(ContentService contentService) {
        this.contentService = contentService;
    }

    // 1 - View all contents (previously /viewall)    
    @RequestMapping("/")
    public ModelAndView viewAllContent() {
        List<Content> contents = contentService.getAllContent();
        ModelAndView modelAndView = new ModelAndView("content/viewall"); // View name
        modelAndView.addObject("contents", contents);
        
        Optional<String> Orole=SecurityContextHolder.getContext().getAuthentication().getAuthorities().stream().map(GrantedAuthority::getAuthority).findFirst();
        String role="";
        role=Orole.orElse("");
        modelAndView.addObject("role", role);
        
        return modelAndView;
    }

    // 2 - View content by ID
    @RequestMapping("/{contentID}")
    public ModelAndView viewContentById(@PathVariable String contentID) {
        Content content = contentService.getContentById(Integer.parseInt(contentID));
        ModelAndView modelAndView = new ModelAndView("content/view"); // View name
        if (content != null) {
            modelAndView.addObject("content", content);
        } else {
            modelAndView.addObject("message", "Content not found.");
        }
        
        Optional<String> Orole=SecurityContextHolder.getContext().getAuthentication().getAuthorities().stream().map(GrantedAuthority::getAuthority).findFirst();
        String role="";
        role=Orole.orElse("");
        modelAndView.addObject("role", role);
        
        return modelAndView;
    }

    
//    TEACHER
    // 3 - Add new content (GET form)
    @PreAuthorize("hasRole('TEACHER') or hasRole('ADMIN')")
    @RequestMapping("/add")
    public ModelAndView showAddContentForm() {
        ModelAndView modelAndView = new ModelAndView("content/add"); // View name for the form
        Content c=new Content();
        
        String userName = SecurityContextHolder.getContext().getAuthentication().getName();
        c.setOwner(userName);
        
        modelAndView.addObject("content", c);
        
        Optional<String> Orole=SecurityContextHolder.getContext().getAuthentication().getAuthorities().stream().map(GrantedAuthority::getAuthority).findFirst();
        String role="";
        role=Orole.orElse("");
        modelAndView.addObject("role", role);
        
        return modelAndView;
    }

    // 4 - Handle add content (POST form)
    @RequestMapping("/add/save")
    public ModelAndView addContent(Content content) {
        contentService.createContent(content);
        return new ModelAndView("redirect:/content/manage"); // Redirect to view all content after saving
    }

    
// ADMIN AND TEACHER
    
    
    
    
 // 1.5 - View all contents (previously /manage)    
    @PreAuthorize("hasRole('TEACHER') or hasRole('ADMIN')")
    @RequestMapping("/manage")
    public ModelAndView manageAllContent() {
    	String userName = SecurityContextHolder.getContext().getAuthentication().getName();
        List<Content> contents = contentService.getContentbyOwner(userName);
        
        
        ModelAndView modelAndView = new ModelAndView("content/manageall"); // View name
        
        Optional<String> Orole=SecurityContextHolder.getContext().getAuthentication().getAuthorities().stream().map(GrantedAuthority::getAuthority).findFirst();
        String role="";
        
        role=Orole.orElse("");
        
        if(role.equals("ROLE_ADMIN")) {
        	contents=contentService.getAllContent();
        }
        modelAndView.addObject("role", role);
        modelAndView.addObject("contents", contents);
        return modelAndView;
    }

    
 // 5 - Update existing content (GET form)    
    @PreAuthorize("hasRole('TEACHER') or hasRole('ADMIN')")
    @RequestMapping("/update/{contentID}")
    public ModelAndView showUpdateForm(@PathVariable String contentID) {
        Content content = contentService.getContentById(Integer.parseInt(contentID));  // Fetch content by ID
        
        ModelAndView modelAndView = new ModelAndView("content/update"); // View name for the update form
        modelAndView.addObject("content", content);  // Add content object to model
        
        Optional<String> Orole=SecurityContextHolder.getContext().getAuthentication().getAuthorities().stream().map(GrantedAuthority::getAuthority).findFirst();
        String role="";
        role=Orole.orElse("");
        modelAndView.addObject("role", role);
        
        return modelAndView;
    }

    // 6 - Handle update content (POST form)
    @PreAuthorize("hasRole('TEACHER') or hasRole('ADMIN')")
    @RequestMapping("/update/save")
    public ModelAndView updateContent(Content content) {
        contentService.updateContent(content);  // Call service to update content
        return new ModelAndView("redirect:/content/manage");  // Redirect to view all content after saving
    }
    
    // 7 - Delete content
    @PreAuthorize("hasRole('TEACHER') or hasRole('ADMIN')")
    @RequestMapping("/delete/{contentID}")
    public ModelAndView deleteContent(@PathVariable String contentID) {
        contentService.deleteContentById(Integer.parseInt(contentID));
        return new ModelAndView("redirect:/content/manage"); // Redirect to view all content after deletion
    }
}
