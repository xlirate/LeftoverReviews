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
    
    @GetMapping("/")
    public String homeDisplay() {
        return "home";
    }

    @GetMapping("/product")
    public String productDisplay(@RequestParam(name="name", required=false, defaultValue="World") String name, Model model) {
        productRepository.save(new Product(new User("Tom"), "google.com"));

        model.addAttribute("id", productRepository.findAll().iterator().next().getId());

        return "product";
    }
    
    @GetMapping("/user")
    public String userDisplay(Model model)
    {
    	User user = new User("Tom");
    	model.addAttribute("user", user);
    	return "user";
    }
    
    @PostMapping("/user")
    public String userSubmit(@ModelAttribute User user)
    {
    	userRepo.save(user);
    	return "result";
    }
    
    @GetMapping("/allusers")
    public String allUserDisplay(Model model)
    {
    	model.addAttribute("allusers", userRepo.findAll());
    	return "allusers";
    }

}