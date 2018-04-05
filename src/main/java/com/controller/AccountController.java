package com.controller;

import com.entities.RepoManager;
import com.entities.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;


@Controller
public class AccountController {
    @Autowired
    RepoManager manager;

    @GetMapping("/account")
    public String accountDisplay(HttpServletResponse response, Model model) {
        Cookie c = new Cookie("clientUserId", "0");
        c.setPath("/");
        response.addCookie(c);
        return "account";
    }

    @PostMapping("account/login")
    public String login(HttpServletResponse response, @RequestParam("username") String username, Model model) {
        User u;

        if(username != null && (u = RepoManager.getUserRepository().findByUsername(username)) != null){
            Cookie c = new Cookie("clientUserId", u.getId().toString());
            c.setPath("/");
            response.addCookie(c);
            return "redirect:/";
        }else {
            model.addAttribute("message", "User not found!");
            return "account";
        }
    }

    @PostMapping("account/register")
    public String register(HttpServletResponse response, @RequestParam("username") String username, Model model) {
        User u;

        if(username != null && (u = new User(username)).hasUniqueName()){
            u = u.save();
            Cookie c = new Cookie("clientUserId", u.getId().toString());
            c.setPath("/");
            response.addCookie(c);
            return "redirect:/";
        }else {

            model.addAttribute("message", "Username already in use!");
            return "account";
        }
    }
}