package com.controller;

import org.springframework.http.MediaType;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/api/reviews")
public class FollowRestController {

    // POST "api/follow" # user id will be found in request body
    @PostMapping(path = "/", produces=MediaType.APPLICATION_JSON_VALUE)
    public String follow(Model model){
        return null;
    }

    // DELETE "api/follow/{user id}"
    @PostMapping(path = "/", produces=MediaType.APPLICATION_JSON_VALUE)
    public String unfollow(Model model){
        return null;
    }

}