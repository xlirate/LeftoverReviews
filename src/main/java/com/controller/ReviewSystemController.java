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
    
    
    @GetMapping("/product")
    public String productDisplay(@RequestParam(name="name", required=false, defaultValue="World") String name, Model model) {
        new User("Tom").createProduct("google.com", "Some description");

        model.addAttribute("id", productRepository.findAll().iterator().next().getId());

        return "product";
    }
    
    @GetMapping("/user")
    public String userDisplay(Model model)
    {
    	User user = new User("");
    	model.addAttribute("user", user);
        //model.addAttribute("message", "");
    	return "user";
    }
    
    @PostMapping("/user")
    public String userSubmit(Model model, @ModelAttribute User user)
    {
        if(user.hasUniqueName()) {
            userRepo.save(user);
            return "result";
        }else{
            model.addAttribute("message", "That name is already taken!");
            return "user";
        }
    }
    
    @GetMapping("/allusers")
    public String allUserDisplay(Model model)
    {
    	model.addAttribute("allusers", userRepo.findAll());
    	return "allusers";
    }
    
    @GetMapping("/follow")
    public String followUser(Model model)
    {
    	
    	
    	
    	return "follow";
    }

}