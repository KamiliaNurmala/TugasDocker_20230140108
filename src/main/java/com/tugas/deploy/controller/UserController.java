package com.tugas.deploy.controller;

import com.tugas.deploy.model.User;
import com.tugas.deploy.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    // ── AUTH ──────────────────────────────────────────────
    @GetMapping("/")
    public String loginPage() {
        return "login";
    }

    @PostMapping("/login")
    public String prosesLogin(@RequestParam String username,
                              @RequestParam String password,
                              Model model) {
        if ("admin".equals(username) && "20230140108".equals(password)) {
            return "redirect:/home";
        }
        model.addAttribute("error", "Username atau Password salah!");
        return "login";
    }

    @GetMapping("/logout")
    public String logout() {
        return "redirect:/";
    }

    // ── HOME ──────────────────────────────────────────────
    @GetMapping("/home")
    public String homePage(Model model) {
        model.addAttribute("mahasiswaList", userService.getAllUser()); // fix: nama variable cocok dengan home.html

        // Ganti tiga baris ini sesuai identitas masing-masing mahasiswa
        model.addAttribute("namaOwner",  "Muhammad Farhan");         // ← nama kamu
        model.addAttribute("greeting",   "Selamat datang, semoga harimu menyenangkan! 🎉"); // ← kata-kata kamu
        model.addAttribute("fotoProfil", "/images/foto.jpg");        // ← taruh foto di src/main/resources/static/images/

        return "home";
    }

    // ── FORM ──────────────────────────────────────────────
    @GetMapping("/form")
    public String formPage(Model model) {
        model.addAttribute("user", new User());
        return "form";
    }

    @PostMapping("/save")          // fix: cocokkan dengan th:action="@{/save}" di form.html
    public String saveData(User user) {
        userService.addUser(user);
        return "redirect:/home";
    }

    // ── DELETE ────────────────────────────────────────────
    @PostMapping("/delete")        // fix: handler yang hilang
    public String deleteData(@RequestParam String id) {
        userService.deleteUser(id);
        return "redirect:/home";
    }
}