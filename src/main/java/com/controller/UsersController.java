package com.controller;

import java.util.*;

import com.entities.RepoManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.entities.User;
import com.entities.UserRepository;


@Controller
public class UsersController {

    //GET "/users?[usernamefrag={frag}]&[ordering={jaccard|bacon}]"
    @GetMapping("/allusers")
    public String getUsersList(Model model,
                               @CookieValue(name = "clientUserId", required = false) String clientUserId,
                               @RequestParam(name = "usernamefrag", required = false) String fragment,
                               @RequestParam(name = "ordering", required = false) String ordering) {
        if (clientUserId == null || clientUserId.equals("0") || RepoManager.getUserRepository().findById(Long.valueOf(clientUserId)).orElse(null) == null) {
            return "redirect:/account";
        }
        User curentUser = RepoManager.getUserRepository().findById(Long.valueOf(clientUserId)).get();

        List<User> allUsers = new ArrayList<>();
        for (User u : RepoManager.getUserRepository().findAll()) {
            allUsers.add(u);
        }

        Collections.shuffle(allUsers);

        if (fragment != null && !"".equals(fragment)) {
            Iterator<User> itr = allUsers.iterator();
            while (itr.hasNext()) {
                if (!itr.next().getUsername().toUpperCase().contains(fragment.toUpperCase())) {
                    itr.remove();
                }
            }
        }

        if ("bacon".equalsIgnoreCase(ordering)) {
            Collections.sort(allUsers, new Comparator<User>() {
                @Override
                public int compare(User o1, User o2) {
                    return Integer.compare(curentUser.baconDistance(o1), curentUser.baconDistance(o2));
                }
            });
        } else if ("jaccard".equalsIgnoreCase(ordering)){
            Collections.sort(allUsers, new Comparator<User>() {
                @Override
                public int compare(User o1, User o2) {
                    return Double.compare(curentUser.jaccardDistance(o1), curentUser.jaccardDistance(o2));
                }
            });
        }

        model.addAttribute("currentuser", curentUser);
    	model.addAttribute("allusers", allUsers);
        return "allusers";
    }


    @GetMapping("/user")
    public String getUser(Model model,
                          @CookieValue(name = "clientUserId", required = false) String clientUserId) {
        if (clientUserId == null || clientUserId.equals("0") || RepoManager.getUserRepository().findById(Long.valueOf(clientUserId)).orElse(null) == null) {
            return "redirect:/account";
        }
        User curentUser = RepoManager.getUserRepository().findById(Long.valueOf(clientUserId)).get();
        User user = RepoManager.getUserRepository().findById(Long.valueOf(clientUserId)).get();
        model.addAttribute("currentuser", curentUser);
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
      if(RepoManager.getUserRepository().findByUsername(user.getUsername()) != null)
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
      User deleteUser = RepoManager.getUserRepository().findByUsername(user.getUsername());
      if(deleteUser != null)
      {
    	  RepoManager.getUserRepository().delete(deleteUser);
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
     User currentUser = RepoManager.getUserRepository().findById(id).get();
     if(currentUser != null)
     {
         currentUser.follow(RepoManager.getUserRepository().findByUsername(user.getUsername()));
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
      User unfollowUser = RepoManager.getUserRepository().findByUsername(user.getUsername());
      User currentUser = RepoManager.getUserRepository().findById(id).get();
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