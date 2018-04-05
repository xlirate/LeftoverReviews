package com.controller;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


@Controller
public class ProductController {

    ///   /products?[productnamefrag={frag}]&[user={user id}]&[ordering={jaccard|bacon}]"
    @GetMapping("/products")
    public String productListDisplay(Model model) {
        return "productList";
    }

    @GetMapping("/products/{product id}")
    public String productDisplay(Model model) {
        return "product";
    }


    //POST "/api/products" #create new product
    @PostMapping(path = "products/", produces=MediaType.APPLICATION_JSON_VALUE)
    public String makeNewProduct(Model model) {
        return null;//return the object, with it's id
    }

    //DELETE "/api/products/{product id}" # return to list of all products
    @DeleteMapping(path = "/{product id}", produces=MediaType.APPLICATION_JSON_VALUE)
    public String deleteProduct(Model model) {
        return null;//return what was removed, no id
    }
}