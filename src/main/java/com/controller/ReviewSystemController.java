package com.controller;

import com.entities.Product;
import com.entities.ProductRepository;
import com.entities.User;
import com.entities.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
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

    @GetMapping("/product")
    public String productDisplay(@RequestParam(name="name", required=false, defaultValue="World") String name, Model model) {
        productRepository.save(new Product());

        model.addAttribute("id", productRepository.findAll().iterator().next().getId());

        return "product";
    }
    
    @GetMapping("/user")
    public String userDisplay(Model model)
    {
    	User user = new User();
    	userRepo.save(user);
    	model.addAttribute("user", userRepo.findById(user.getUserID()).get());
    	return "user";
    }

}