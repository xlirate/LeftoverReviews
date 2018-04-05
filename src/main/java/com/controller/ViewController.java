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
}