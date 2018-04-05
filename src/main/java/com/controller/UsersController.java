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
    
    
    @GetMapping("/user")
    public String getUser(@CookieValue("clientUserId") String clientUserId, Model model)
    {
    	  long id = 0;
          long otherid = 0;
          if(clientUserId == null || clientUserId == "" || clientUserId == "0") {
              return "redirect:/account";
          }else{
              id = Long.valueOf(clientUserId);
          }
          User user = new User("");
          model.addAttribute("user", user);
          return "user";
	}
    
    @PostMapping("/user")
    public String showUser(@CookieValue("clientUserId") String clientUserId, @ModelAttribute User user, Model model)
    {
  	  long id = 0;
      long otherid = 0;
      if(clientUserId == null || clientUserId == "" || clientUserId == "0") {
          return "redirect:/account";
      }else{
          id = Long.valueOf(clientUserId);
      }
      if(userRepo.findByUsername(user.getUsername()) != null)
      {
          return "result";
      }
      else
      {
    	  return "finderror";
      }
    }

    @GetMapping("/delete")
    public String deleteUser(@CookieValue("clientUserId") String clientUserId, Model model)
    {
    	  long id = 0;
          long otherid = 0;
          if(clientUserId == null || clientUserId == "" || clientUserId == "0") {
              return "redirect:/account";
          }else{
              id = Long.valueOf(clientUserId);
          }
          User user = new User("");
          model.addAttribute("user", user);
          return "delete";
	}
    
    @PostMapping("/delete")
    public String showDelete(@CookieValue("clientUserId") String clientUserId, @ModelAttribute User user, Model model)
    {
  	  long id = 0;
      long otherid = 0;
      if(clientUserId == null || clientUserId == "" || clientUserId == "0") {
          return "redirect:/account";
      }else{
          id = Long.valueOf(clientUserId);
      }
      User deleteUser = userRepo.findByUsername(user.getUsername());
      if(deleteUser != null)
      {
    	  userRepo.delete(deleteUser);
          return "deleteresult";
      }
      else
      {
    	  return "finderror";
      }
    }
    
    @GetMapping("/follow")
    public String followUser(@CookieValue("clientUserId") String clientUserId, Model model)
    {
    	  long id = 0;
          long otherid = 0;
          if(clientUserId == null || clientUserId == "" || clientUserId == "0") {
              return "redirect:/account";
          }else{
              id = Long.valueOf(clientUserId);
          }
          User user = new User("");
          model.addAttribute("user", user);
          return "follow";
	}
    
    @PostMapping("/follow")
    public String followUser(@CookieValue("clientUserId") String clientUserId, @ModelAttribute User user, Model model)
    {
  	  long id = 0;
      long otherid = 0;
      if(clientUserId == null || clientUserId == "" || clientUserId == "0") {
          return "redirect:/account";
      }else{
          id = Long.valueOf(clientUserId);
      }
     User currentUser = userRepo.findById(id).get();
     if(currentUser != null)
     {
         currentUser.follow(userRepo.findByUsername(user.getUsername()));
         currentUser.save();
         return "followresult";
     }
     else
     {
   	  return "finderror";
     }
     
    }

    @GetMapping("/unfollow")
    public String unfollowUser(@CookieValue("clientUserId") String clientUserId, Model model)
    {
    	  long id = 0;
          long otherid = 0;
          if(clientUserId == null || clientUserId == "" || clientUserId == "0") {
              return "redirect:/account";
          }else{
              id = Long.valueOf(clientUserId);
          }
          User user = new User("");
          model.addAttribute("user", user);
          return "unfollow";
	}
    
    @PostMapping("/unfollow")
    public String showUnfollow(@CookieValue("clientUserId") String clientUserId, @ModelAttribute User user, Model model)
    {
  	  long id = 0;
      long otherid = 0;
      if(clientUserId == null || clientUserId == "" || clientUserId == "0") {
          return "redirect:/account";
      }else{
          id = Long.valueOf(clientUserId);
      }
      User unfollowUser = userRepo.findByUsername(user.getUsername());
      User currentUser = userRepo.findById(id).get();
      if(unfollowUser != null)
      {
    	  currentUser.unfollow(unfollowUser);
    	  currentUser.save();
          return "unfollowresult";
      }
      else
      {
    	  return "finderror";
      }
    }
}