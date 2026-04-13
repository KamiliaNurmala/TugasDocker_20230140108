package com.tugas.deploy.controller;

import com.tugas.deploy.model.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import jakarta.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

@Controller
public class UserController {

    // Hardcoded credentials
    private static final String USERNAME = "admin";
    private static final String PASSWORD = "20230140108"; // Replace with your NIM

    // Temporary storage (in-memory)
    private static List<User> mahasiswaList = new ArrayList<>();

    @GetMapping("/login")
    public String showLoginPage() {
        return "login";
    }

    @PostMapping("/login")
    public String processLogin(
            @RequestParam String username,
            @RequestParam String password,
            HttpSession session,
            Model model) {

        if (USERNAME.equals(username) && PASSWORD.equals(password)) {
            session.setAttribute("user", username);
            return "redirect:/home";
        } else {
            model.addAttribute("error", "Invalid username or password!");
            return "login";
        }
    }

    @GetMapping("/home")
    public String showHomePage(HttpSession session, Model model) {
        if (session.getAttribute("user") == null) {
            return "redirect:/login";
        }

        model.addAttribute("mahasiswaList", mahasiswaList);
        return "home";
    }

    @GetMapping("/form")
    public String showFormPage(HttpSession session, Model model) {
        if (session.getAttribute("user") == null) {
            return "redirect:/login";
        }

        model.addAttribute("user", new User());
        return "form";
    }

    @PostMapping("/save")
    public String saveData(
            @ModelAttribute User user,
            HttpSession session) {

        if (session.getAttribute("user") == null) {
            return "redirect:/login";
        }

        // Add to temporary list
        mahasiswaList.add(user);
        return "redirect:/home";
    }

    @PostMapping("/delete")
    public String deleteData(
            @RequestParam String nim,
            HttpSession session) {

        if (session.getAttribute("user") == null) {
            return "redirect:/login";
        }

        // Remove from list
        mahasiswaList.removeIf(m -> m.getNim().equals(nim));
        return "redirect:/home";
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/login";
    }

    @GetMapping("/")
    public String redirectToLogin() {
        return "redirect:/login";
    }
}