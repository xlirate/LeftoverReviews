package com.controller;

import com.entities.Product;
import com.entities.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;


@Controller
public class GreetingController {

    public ProductRepository productRepository;

    @Autowired
    public GreetingController(ProductRepository productRepository){
        this.productRepository = productRepository;
    }

    @GetMapping("/greeting")
    public String greeting(@RequestParam(name="name", required=false, defaultValue="World") String name, Model model) {
        productRepository.save(new Product());

        model.addAttribute("id", productRepository.findAll().iterator().next().id);

        return "greeting";
    }

}