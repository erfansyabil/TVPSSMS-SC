package com.example.controller;

import com.example.entity.Program;
import com.example.entity.Report;
import com.example.service.ProgramDAO;
import com.example.service.ReportDAO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/report")
public class ReportController {

    private final ReportDAO reportDAO;
    private final ProgramDAO programDAO;

    @Autowired
    public ReportController(ReportDAO reportDAO, ProgramDAO programDAO) {
        this.reportDAO = reportDAO;
        this.programDAO = programDAO;
    }

    /**
     * View all programs.
     */
    @GetMapping("/view")
    public String viewPrograms(Model model) {
        List<Program> programs = programDAO.findAll(); // Fetch all programs
        model.addAttribute("programs", programs);     // Add programs to the model
        return "report/view";                         // Return the view page
    }

    /**
     * Add Report Form
     * Displays the form to add a new report for a specific program.
     */
    @GetMapping("/add/{programId}")
    public String addReportForm(@PathVariable Long programId, Model model) {
        Program program = programDAO.findById(programId); // Fetch the program by ID
        if (program == null) {
            throw new IllegalArgumentException("Program not found for ID: " + programId); // Handle missing program
        }

        Report report = new Report();
        report.setProgram(program); // Associate the report with the program
        model.addAttribute("report", report);
        return "report/add"; // Return the add report form view
    }

    /**
     * Save Report
     * Handles the submission of the add report form.
     */
    @PostMapping("/add")
    public String saveReport(@ModelAttribute("report") Report report, RedirectAttributes redirectAttributes) {
        try {
            // Check if program is set and associated
            if (report.getProgram() != null && report.getProgram().getId() != null) {
                Program program = programDAO.findById(report.getProgram().getId()); // Ensure program is valid
                if (program != null) {
                    report.setProgram(program); // Associate the correct program
                    reportDAO.save(report); // Save the report
                    redirectAttributes.addFlashAttribute("status", "success");
                    redirectAttributes.addFlashAttribute("message", "Report has been successfully added.");
                } else {
                    throw new IllegalArgumentException("Program not found for ID: " + report.getProgram().getId());
                }
            } else {
                throw new IllegalArgumentException("Program ID is required for the report.");
            }
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("status", "error");
            redirectAttributes.addFlashAttribute("message", "Failed to add the report. Please try again.");
        }
        return "redirect:/report/view"; // Redirect to the programs view page
    }


    /**
     * Admin: View All Reports
     * Displays all reports for the admin interface.
     */
    @GetMapping("/admin-view")
    public String viewAllReports(Model model) {
        List<Report> reports = reportDAO.findAll(); // Fetch all reports
        if (reports == null || reports.isEmpty()) {
            System.out.println("No reports found.");
        } else {
            System.out.println("Reports fetched: " + reports.size());
        }
        model.addAttribute("reports", reports);
        return "report/admin-report-view"; // Return the admin view page
    }
}
