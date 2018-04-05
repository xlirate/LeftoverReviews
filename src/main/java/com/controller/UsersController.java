package com.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.entities.User;
import com.entities.UserRepository;


@Controller
public class UsersController {
		
		
    private UserRepository userRepo;

    @Autowired
    public UsersController(UserRepository userRepo){
	        this.userRepo = userRepo;
	    }

    //GET "/users?[usernamefrag={frag}]&[ordering={jaccard|bacon}]"
    @GetMapping("/allusers")
    public String getUsersList(@CookieValue("clientUserId") String clientUserId, Model model) {
        long id = 0;
        if(clientUserId == null || clientUserId == "" || clientUserId == "0") {
            return "redirect:/account";
        }else{
            id = Long.valueOf(clientUserId);
        }
    	model.addAttribute("userlist", userRepo.findAll());
        return "allusers";
    }

    //GET "/users/{user id}"
    @RequestMapping(value = "/users/{userid}", method = {RequestMethod.POST, RequestMethod.GET})    
    public String getUsersData(@PathVariable("userid") String userid, @CookieValue("clientUserId") String clientUserId, Model model) {
        long id = 0;
        long otherid = 0;
        if(clientUserId == null || clientUserId == "" || clientUserId == "0") {
            return "redirect:/account";
        }else{
            id = Long.valueOf(clientUserId);
        }
        otherid = Long.valueOf(userid);
    	model.addAttribute("user", userRepo.findById(otherid).get());
    	return "user";
    }

    //DELETE "/users/{user id}"
//    @DeleteMapping("/users/{userid}")
//    public String deleteUser(@PathVariable(value="userid") String userID, @CookieValue("clientUserId") String clientUserId, Model model) {
//        long id = 0;
//        long otherid = 0;
//        if(clientUserId == null || clientUserId == "" || clientUserId == "0") {
//            return "redirect:/account";
//        }else{
//            id = Long.valueOf(clientUserId);
//        }
//        otherid = Long.getLong(userID);
//    	userRepo.deleteById(otherid);
//        return "delete";
//    }

    // POST "/users/follow/{userid}" # user id will be found in request body
//    @PostMapping("/users/follow/{userid}")
//    public String follow(@PathVariable(value="userid") String userID, @CookieValue("clientUserId") String clientUserId, Model model){
//        long id = 0;
//        long otherid = 0;
//        if(clientUserId == null || clientUserId == "" || clientUserId == "0") {
//            return "redirect:/account";
//        }else{
//            id = Long.valueOf(clientUserId);
//        }
//        otherid = Long.valueOf(userID);
//        User user = userRepo.findById(id).get();
//        user.follow(userRepo.findById(otherid).get());
//        
//        return "follow";
//    }

    // DELETE "/users/follow/{userid}"
//    @DeleteMapping("/users/follow/{userid}")
//    public String unfollow(@PathVariable(value="userid") String userID, @CookieValue("clientUserId") String clientUserId, Model model){
//        long id = 0;
//        long otherid = 0;
//        if(clientUserId == null || clientUserId == "" || clientUserId == "0") {
//            return "redirect:/account";
//        }else{
//            id = Long.valueOf(clientUserId);
//        }
//        otherid = Long.valueOf(userID);
//        User user = userRepo.findById(id).get();
//        user.unfollow(user);
//        return "unfollow";
//    }
}