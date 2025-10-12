package com.inventory.management.system.controllers;

import com.inventory.management.system.entities.User;
import com.inventory.management.system.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("api/admin/")
public class AdminController {

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/dashboard")
    public String adminDashboard() {
        return "Kjo është faqja e adminit.";
    }

    @PostMapping("/add-product")
    public String addProduct() {
        return "Produkti u shtua me sukses (vetëm admini mund ta bëjë këtë).";
    }

    @GetMapping("/all-admins")
    public List<User> getAllAdmins() {
        return userRepository.findByRole("ADMIN");
    }
}
