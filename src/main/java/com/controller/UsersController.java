package com.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


@Controller
public class UsersController {

    //GET "/users?[usernamefrag={frag}]&[ordering={jaccard|bacon}]"
    @GetMapping("/users")
    public String getUsersList(Model model) {
        return null;
    }

    //GET "/users/{user id}"
    @GetMapping("/users/{userid}")
    public String getUsersData(Model model) {
        return null;
    }

    //DELETE "/users/{user id}"
    @DeleteMapping("/users/{userid}")
    public String deleteUser(Model model) {
        return null;
    }

    // POST "/users/follow/{userid}" # user id will be found in request body
    @PostMapping("/users/follow/{userid}")
    public String follow(Model model){
        return null;
    }

    // DELETE "/users/follow/{userid}"
    @DeleteMapping("/users/follow/{userid}")
    public String unfollow(Model model){
        return null;
    }
}