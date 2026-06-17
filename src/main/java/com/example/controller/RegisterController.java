package com.example.controller;

import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.example.entity.Authority;
import com.example.entity.User;
import com.example.service.UserDAO;

@Controller
public class RegisterController {

    @Autowired
    private UserDAO userDAO;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder; // Autowire the encoder

    // Show registration page
    @GetMapping("/register")
    public String register() {
        return "user/register"; // Points to user/register.html
    }

    // Handle registration form submission
    @PostMapping("/register")
    public String register(String username, String password, String confirmPassword, String role, Model model) {
        // Validate password match
        if (!password.equals(confirmPassword)) {
            model.addAttribute("error", "Passwords do not match!");
            model.addAttribute("username", username); // Retain entered username
            return "user/register"; // Return registration form with error
        }

        // Check if username already exists
        if (userDAO.findByUsername(username) != null) {
            model.addAttribute("error", "Username already exists!");
            model.addAttribute("username", username); // Retain entered username
            return "user/register"; // Return registration form with error
        }

        // Create new User entity
        User user = new User();
        user.setUsername(username);
        user.setPassword(passwordEncoder.encode(password)); // Encode password
        user.setEnabled(true); // Enable the user

        // Assign the selected role
        Authority authority = new Authority();
        authority.setAuthority(role); // Set the role (e.g., ROLE_ADMIN, ROLE_TEACHER, etc.)
        authority.setUser(user); // Associate the role with the user

        Set<Authority> authorities = new HashSet<>();
        authorities.add(authority); // Add the role to the user
        user.setAuthorities(authorities); // Assign the authorities

        // Save the user in the database
        userDAO.save(user);

        // Redirect to the login page after successful registration
        return "redirect:/users/loginrole"; // Redirect to login page after successful registration
    }
}
