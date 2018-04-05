package com.controller;

import com.entities.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


@Controller
public class ReviewSystemController {

    @Autowired
    private UserRepository userRepo;

    @GetMapping("/home")
    public String homeDisplay() {
        return "redirect:/";
    }
    
    @GetMapping("/")
    public String defaultDisplay(Model model,
                                 @CookieValue(name = "clientUserId", required = false) String clientUserId) {
        if (clientUserId == null || clientUserId.equals("0") || RepoManager.getUserRepository().findById(Long.valueOf(clientUserId)).orElse(null) == null) {
            return "redirect:/account";
        }
        User curentUser = RepoManager.getUserRepository().findById(Long.valueOf(clientUserId)).get();
        model.addAttribute("currentuser", curentUser);
        return "home";
    }
    



}