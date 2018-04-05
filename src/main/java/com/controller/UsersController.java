package com.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


@Controller
public class UsersController {

    //GET "/users?[usernamefrag={frag}]&[ordering={jaccard|bacon}]"
    @GetMapping("/users")
    public String getUsersList(@CookieValue("clientUserId") String clientUserId, Model model) {
        long id = 0;
        if(clientUserId == null || clientUserId == "" || clientUserId == "0") {
            return "redirect:/account";
        }else{
            id = Long.valueOf(clientUserId);
        }
        return null;
    }

    //GET "/users/{user id}"
    @GetMapping("/users/{userid}")
    public String getUsersData(@CookieValue("clientUserId") String clientUserId, Model model) {
        long id = 0;
        if(clientUserId == null || clientUserId == "" || clientUserId == "0") {
            return "redirect:/account";
        }else{
            id = Long.valueOf(clientUserId);
        }
        return null;
    }

    //DELETE "/users/{user id}"
    @DeleteMapping("/users/{userid}")
    public String deleteUser(@CookieValue("clientUserId") String clientUserId, Model model) {
        long id = 0;
        if(clientUserId == null || clientUserId == "" || clientUserId == "0") {
            return "redirect:/account";
        }else{
            id = Long.valueOf(clientUserId);
        }
        return null;
    }

    // POST "/users/follow/{userid}" # user id will be found in request body
    @PostMapping("/users/follow/{userid}")
    public String follow(@CookieValue("clientUserId") String clientUserId, Model model){
        long id = 0;
        if(clientUserId == null || clientUserId == "" || clientUserId == "0") {
            return "redirect:/account";
        }else{
            id = Long.valueOf(clientUserId);
        }
        return null;
    }

    // DELETE "/users/follow/{userid}"
    @DeleteMapping("/users/follow/{userid}")
    public String unfollow(@CookieValue("clientUserId") String clientUserId, Model model){
        long id = 0;
        if(clientUserId == null || clientUserId == "" || clientUserId == "0") {
            return "redirect:/account";
        }else{
            id = Long.valueOf(clientUserId);
        }
        return null;
    }
}