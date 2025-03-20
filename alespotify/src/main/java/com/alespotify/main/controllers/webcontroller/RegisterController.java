package com.alespotify.main.controllers.webcontroller;

import com.alespotify.main.models.User;
import com.alespotify.main.repository.UserRepository;
import com.alespotify.main.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@Controller
@AllArgsConstructor
public class RegisterController {

    private PasswordEncoder passwordEncoder;
    private UserRepository userRepository;
    @Autowired
    private UserService userService;

    @GetMapping("/register")
    public String showRegisterForm(Model model) {
        model.addAttribute("user", new User());
        System.out.println("Entra en register");
        return "register";
    }


    @PostMapping("/register")
    public String registerUser(User user, Model model) {
        if (userService.findByEmail(user.getEmail()) != null) {
            model.addAttribute("error", "Email already exists");
            return "register";
        }
        userService.registerUser(user);
        System.out.println("User registered successfully");
        return "redirect:/app";
    }

}
