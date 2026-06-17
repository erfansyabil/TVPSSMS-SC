package com.example.controller;

import com.example.entity.Crew;
import com.example.service.CrewDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/crew")
public class CrewController {

    @Autowired
    private CrewDAO crewDAO;

    @PreAuthorize("hasRole('TEACHER')")
    @GetMapping("/registration")
    public String viewRegistrationPage(
            @RequestParam(required = false) String search,
            Authentication authentication,
            Model model) {

        // Fetch the current user
        String username = authentication.getName();
        Crew existingCrew = crewDAO.findByUsername(username);

        if (existingCrew != null) {
            model.addAttribute("message", "You are already registered as a crew.");
        } else {
            model.addAttribute("message", "You are not registered as a crew yet.");
        }

        // Determine whether to perform a search or fetch all crews
        List<Crew> crewList = (search != null && !search.isEmpty())
                ? crewDAO.searchCrews(search)
                : crewDAO.findAll();

        model.addAttribute("crewList", crewList);
        model.addAttribute("search", search); // Retain search keyword in the form
        return "crew/registration";
    }

    
    @PreAuthorize("hasRole('STUDENT')")
    @GetMapping("/register")
    public String showRegisterForm(Model model, Authentication authentication) {
        String username = authentication.getName(); // Get the username of the logged-in user
        Crew existingCrew = crewDAO.findByUsername(username);

        if (existingCrew != null) {
            model.addAttribute("alreadyRegistered", true); // Add a flag indicating registration status
            model.addAttribute("crew", existingCrew);      // Pass the existing Crew object for display if needed
        } else {
            model.addAttribute("alreadyRegistered", false);
            model.addAttribute("crew", new Crew()); // Add a new Crew object to the model
        }
        return "crew/register"; // Return the form view
    }

    
    @PreAuthorize("hasRole('STUDENT')")
    @PostMapping("/register")
    public String registerCrew(Authentication authentication, @ModelAttribute Crew crew) {
        if (authentication == null) {
            return "redirect:/users/loginrole"; // Redirect to login if not authenticated
        }

        String username = authentication.getName(); // Get logged-in user's username
        Crew existingCrew = crewDAO.findByUsername(username);

        if (existingCrew == null) {
            crew.setUsername(username); // Set the username for the crew
            crewDAO.saveOrUpdate(crew);
        }

        return "redirect:/crew/register";
    }

    @PreAuthorize("hasRole('TEACHER')")
    @GetMapping("/delete/{id}")
    public String deleteCrew(@PathVariable int id) {
        crewDAO.deleteById(id);
        return "redirect:/crew/registration";
    }
    
    @PreAuthorize("hasRole('TEACHER')")
    @GetMapping("/review/{id}")
    public String reviewCrew(@PathVariable int id, Model model) {
        // Fetch the Crew by ID to show their details
        Crew crew = crewDAO.findById(id);
        model.addAttribute("crew", crew);  // Add crew object to the model for review
        return "crew/review";  // Return the review page view
    }
    
    @PreAuthorize("hasRole('TEACHER')")
    @PostMapping("/approve/{id}")
    public String approveCrew(@PathVariable int id) {
        // Fetch the Crew by ID
        Crew crew = crewDAO.findById(id);
        if (crew != null) {
            crew.setStatus("Approved");  // Update the status to 'Approved'
            crewDAO.saveOrUpdate(crew);  // Save the changes
        }
        return "redirect:/crew/registration";  // Redirect back to the registration list
    }

    @PreAuthorize("hasRole('TEACHER')")
    @PostMapping("/reject/{id}")
    public String rejectCrew(@PathVariable int id) {
        // Fetch the Crew by ID
        Crew crew = crewDAO.findById(id);
        if (crew != null) {
            crew.setStatus("Rejected");  // Update the status to 'Rejected'
            crewDAO.saveOrUpdate(crew);  // Save the changes
        }
        return "redirect:/crew/registration";  // Redirect back to the registration list
    }

}
