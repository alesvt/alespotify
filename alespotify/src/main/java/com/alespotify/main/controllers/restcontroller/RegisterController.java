package com.alespotify.main.controllers.restcontroller;

import com.alespotify.main.models.entities.Usuario;
import com.alespotify.main.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class RegisterController {

    private final UserService userServiceImpl;

    public RegisterController(UserService userServiceImpl) {
        this.userServiceImpl = userServiceImpl;
    }

    @GetMapping("/register")
    public String showRegisterForm(Model model) {
        model.addAttribute("user", new Usuario());
        System.out.println("Entra en register");
        return "register";
    }

    @PostMapping("/register")
    public String registerUser(Usuario user, Model model) {
        if (userServiceImpl.findByEmail(user.getEmail()).isPresent()) {
            model.addAttribute("error", "Email already exists");
            return "register";
        }
        userServiceImpl.registerUser(user);
        System.out.println("User registered successfully");
        return "redirect:/app";
    }
}
