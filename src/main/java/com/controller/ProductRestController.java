package com.controller;

import org.springframework.http.MediaType;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/products")
public class ProductRestController {

    //GET "/api/products?[productnamefrag={frag}]&[user={user id}]&[ordering={jaccard|bacon}]"
    @GetMapping(path = "/", produces=MediaType.APPLICATION_JSON_VALUE)
    public String getProductsList(Model model) {
        return null;
    }

    //GET "/api/products/{product id}"
    @GetMapping(path = "/{product id}", produces=MediaType.APPLICATION_JSON_VALUE)
    public String getProductsDetails(Model model) {
        return null;//return the product object
    }

    //POST "/api/products" #create new product
    @PostMapping(path = "/", produces=MediaType.APPLICATION_JSON_VALUE)
    public String makeNewProduct(Model model) {
        return null;//return the object, with it's id
    }

    //DELETE "/api/products/{product id}" # return to list of all products
    @DeleteMapping(path = "/{product id}", produces=MediaType.APPLICATION_JSON_VALUE)
    public String deleteProduct(Model model) {
        return null;//return what was removed, no id
    }
}