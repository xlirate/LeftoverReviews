package com.controller;

import org.springframework.http.MediaType;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/reviews")
public class ReviewRestController {

    // GET "api/reviews?[user={user id}]&[product={product id}]&[ordering={jaccard|bacon}]"
    @GetMapping(path = "/", produces=MediaType.APPLICATION_JSON_VALUE)
    public String getReviews(Model model){
        return null;
    }
    // POST "api/reviews"
    @PostMapping(path = "/", produces=MediaType.APPLICATION_JSON_VALUE)
    public String madeReview(Model model){
        return null;
    }
    // DELETE "api/reviews/{review id}"
    @DeleteMapping(path = "/", produces=MediaType.APPLICATION_JSON_VALUE)
    public String deleteReview(Model model){
        return null;
    }


}