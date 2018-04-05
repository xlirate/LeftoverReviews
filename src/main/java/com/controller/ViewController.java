package com.controller;

import com.entities.ProductRepository;
import com.entities.User;
import com.entities.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;


@Controller
public class ViewController {
    @GetMapping("/")
    public String homeDisplay(Model model) {
        return "home";
    }

    @GetMapping("/home")
    public String homeRedirectDisplay(Model model) {
        return "redirect:/";
    }

    @GetMapping("/users")
    public String usersListDisplay(Model model) {
        return "usersList";
    }

    @GetMapping("/users/{user id}")
    public String userDisplay(Model model) {
        return "user";
    }

    @GetMapping("/products")
    public String productListDisplay(Model model) {
        return "productList";
    }

    @GetMapping("/products/{product id}")
    public String productDisplay(Model model) {
        return "product";
    }

    @GetMapping("/accounts")
    public String accountsDisplay(Model model) {
        return "usersList";
    }

    @PostMapping("/register/{username}")
    public String registerPost(Model model) {
        return "usersList";
    }

    @PostMapping("/login/{username}")
    public String loginPost(Model model) {
        return "usersList";
    }
}