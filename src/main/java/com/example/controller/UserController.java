package com.example.controller;

import com.example.entity.User;
import com.example.service.UserDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserDAO userDAO;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

 // Render custom login page
    @GetMapping("/loginrole")
    public String showLoginRolePage(@RequestParam(value = "error", required = false) String error, Model model) {
        if (error != null) {
            model.addAttribute("error", "Invalid username or password");
        }
        return "user/loginrole"; // Points to user/loginrole.html
    }
    
    @GetMapping("/admin")
    public String showAdminIndex() {
        return "admin/index"; 
    }
    
    @GetMapping("/teacher")
    public String showTeacherIndex() {
        return "teacher/index"; 
    }
    
    @GetMapping("/student")
    public String showStudentIndex() {
        return "student/index"; 
    }
    

 

    // Handle login submission
    @PostMapping("/loginrole")
    public String loginUser(@RequestParam("username") String username,
                            @RequestParam("password") String password,
                            @RequestParam("role") String role, // Capturing role from the form
                            Model model) {
        User user = userDAO.findByUsername(username);

        if (user != null) {
            // Verify password
            boolean isPasswordValid = passwordEncoder.matches(password, user.getPassword());

            // Verify role
            boolean isRoleValid = user.getAuthorities().stream()
                    .anyMatch(auth -> auth.getAuthority().equalsIgnoreCase(role));

            if (isPasswordValid && isRoleValid) {
                // Successful login, return with role-based redirection
                if (role.equalsIgnoreCase("ROLE_ADMIN")) {
                    return "redirect:/admin/index.html"; // Redirect to the admin page
                } else if (role.equalsIgnoreCase("ROLE_TEACHER")) {
                    return "redirect:/teacher/index.html"; // Redirect to the teacher page
                } else if (role.equalsIgnoreCase("ROLE_STUDENT")) {
                    return "redirect:/student/index.html"; // Redirect to the student page
                }
            }
        }

        // Login failed, return with error
        model.addAttribute("error", "Invalid username, password, or role.");
        return "user/loginrole"; // Return back to login page if failed
    }
}
