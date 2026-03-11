package com.example.systemwar.controllers;

import com.example.systemwar.entities.LeaveRequest;
import com.example.systemwar.entities.LeaveStatus;
import com.example.systemwar.entities.User;
import com.example.systemwar.repositories.LeaveRequestRepository;
import com.example.systemwar.repositories.LeaveTypeRepository;
import com.example.systemwar.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class MainController {

    @Autowired
    private LeaveRequestRepository leaveRepo;
    @Autowired private LeaveTypeRepository typeRepo;
    @Autowired private UserRepository userRepo;

    // Custom Login Page
    @GetMapping("/login")
    public String login() { return "login"; }

    // Dashboard (Redirects based on role)
    @GetMapping("/")
    public String dashboard(Authentication auth, Model model) {
        if (auth.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ADMIN"))) {
            return "redirect:/admin/requests";
        }
        User user = userRepo.findByUsername(auth.getName()).get();
        model.addAttribute("requests", leaveRepo.findByUser(user));
        return "user-dashboard";
    }

    // User: Show Create Request Form
    @GetMapping("/request/new")
    public String showCreateForm(Model model) {
        model.addAttribute("leaveRequest", new LeaveRequest());
        model.addAttribute("leaveTypes", typeRepo.findAll());
        return "create-request";
    }

    // User: Submit Request
    @PostMapping("/request/save")
    public String saveRequest(@ModelAttribute LeaveRequest leaveRequest, Authentication auth) {
        User user = userRepo.findByUsername(auth.getName()).get();
        leaveRequest.setUser(user);
        leaveRequest.setStatus(LeaveStatus.PENDING);
        leaveRepo.save(leaveRequest);
        return "redirect:/";
    }

    // Admin: View All
    @GetMapping("/admin/requests")
    public String adminDashboard(Model model) {
        model.addAttribute("allRequests", leaveRepo.findAll());
        return "admin-dashboard";
    }

    // Admin: Update Status
    @PostMapping("/admin/request/{id}/status")
    public String updateStatus(@PathVariable Long id, @RequestParam LeaveStatus status) {
        LeaveRequest req = leaveRepo.findById(id).orElseThrow();
        req.setStatus(status);
        leaveRepo.save(req);
        return "redirect:/admin/requests";
    }
}
