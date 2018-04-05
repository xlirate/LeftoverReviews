package com.controller;

import com.entities.ProductRepository;
import com.entities.User;
import com.entities.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/users")
public class UsersRestController {

    //GET "api/users?[usernamefrag={frag}]&[ordering={jaccard|bacon}]"
    @GetMapping(path = "/", produces=MediaType.APPLICATION_JSON_VALUE)
    public String getUsersList(Model model) {
        return null;
    }

    //GET "api/users/{user id}"
    @GetMapping(path = "/{userid}", produces=MediaType.APPLICATION_JSON_VALUE)
    public String getUsersData(Model model) {
        return null;
    }

    //DELETE "api/users/{user id}"
    @DeleteMapping(path = "/{userid}", produces=MediaType.APPLICATION_JSON_VALUE)
    public String deleteUser(Model model) {
        return null;//return {username = their username}
    }
}