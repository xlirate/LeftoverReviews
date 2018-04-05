package com.controller;

import com.entities.Product;
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
public class ReviewSystemController {

    public ProductRepository productRepository;
    private UserRepository userRepo;

    @Autowired
    public ReviewSystemController(ProductRepository productRepository, UserRepository userRepo){
        this.productRepository = productRepository;
        this.userRepo = userRepo;
    }
    
    @GetMapping("/home")
    public String homeDisplay() {
        return "home";
    }
    
    @GetMapping("/")
    public String defaultDisplay() {
        return "home";
    }
    



}