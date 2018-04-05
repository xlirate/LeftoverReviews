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


    private UserRepository userRepo;

    @Autowired
    public UsersController(UserRepository userRepo){
	        this.userRepo = userRepo;
	    }

    //GET "/users?[usernamefrag={frag}]&[ordering={jaccard|bacon}]"
    @GetMapping("/users")
    public String getUsersList(Model model,
                               @CookieValue(name = "clientUserId", required = false) String clientUserId,
                               @RequestParam(name = "usernamefrag", required = false) String fragment,
                               @RequestParam(name = "ordering", required = false) String ordering) {
        if (clientUserId == null || clientUserId.equals("0") || RepoManager.getUserRepository().findById(Long.valueOf(clientUserId)).orElse(null) == null) {
            return "redirect:/account";
        }
        User curentUser = RepoManager.getUserRepository().findById(Long.valueOf(clientUserId)).get();

        List<User> allUsers = new ArrayList<>();
        for (User u : userRepo.findAll()) {
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


    @GetMapping("/users/{userid}")
    public String getUser(Model model,
                          @CookieValue(name = "clientUserId", required = false) String clientUserId,
                          @RequestParam(name = "userid", required = false) Long userid) {
        if (clientUserId == null || clientUserId.equals("0") || RepoManager.getUserRepository().findById(Long.valueOf(clientUserId)).orElse(null) == null) {
            return "redirect:/account";
        }
        User curentUser = RepoManager.getUserRepository().findById(Long.valueOf(clientUserId)).get();
        User user = RepoManager.getUserRepository().findById(Long.valueOf(clientUserId)).get();


        model.addAttribute("currentuser", curentUser);
        model.addAttribute("user", user);
        model.addAttribute("addreviews", user.getWritenReviews());
        model.addAttribute("canfollow", !curentUser.getFollowedUsers().contains(user));

        return "user";
	}

    @PostMapping("/users/delete/{userid}")
    public String deleteUser(Model model,
                             @CookieValue(name = "clientUserId", required = false) String clientUserId,
                             @RequestParam(name = "userid", required = false) Long userid)
    {
        if (clientUserId == null || clientUserId.equals("0") || RepoManager.getUserRepository().findById(Long.valueOf(clientUserId)).orElse(null) == null) {
            return "redirect:/account";
        }
        User curentUser = RepoManager.getUserRepository().findById(Long.valueOf(clientUserId)).get();
        User user = RepoManager.getUserRepository().findById(Long.valueOf(clientUserId)).get();

        if(user.equals(curentUser)){
            RepoManager.getReviewRepository().deleteAll(user.getWritenReviews());
            RepoManager.getProductRepository().deleteAll(user.getCreatedProducts());
            user = user.save();
            RepoManager.getUserRepository().delete(user);
        }

        return "redirect:/users";
    }


    @PostMapping("/user/follow/{userid}")
    public String followUser(Model model,
                             @CookieValue(name = "clientUserId", required = false) String clientUserId,
                             @RequestParam(name = "userid", required = false) Long userid)
    {
        if (clientUserId == null || clientUserId.equals("0") || RepoManager.getUserRepository().findById(Long.valueOf(clientUserId)).orElse(null) == null) {
            return "redirect:/account";
        }
        User curentUser = RepoManager.getUserRepository().findById(Long.valueOf(clientUserId)).get();
        User user = RepoManager.getUserRepository().findById(Long.valueOf(clientUserId)).get();

        curentUser.follow(user);
        curentUser.save();

        return "redirect:/users/"+user.getId();
    }

    @PostMapping("/user/unfollow/{userid}")
    public String showUnfollow(Model model,
                               @CookieValue(name = "clientUserId", required = false) String clientUserId,
                               @RequestParam(name = "userid", required = false) Long userid)
    {
        if (clientUserId == null || clientUserId.equals("0") || RepoManager.getUserRepository().findById(Long.valueOf(clientUserId)).orElse(null) == null) {
            return "redirect:/account";
        }
        User curentUser = RepoManager.getUserRepository().findById(Long.valueOf(clientUserId)).get();
        User user = RepoManager.getUserRepository().findById(Long.valueOf(clientUserId)).get();

        curentUser.unfollow(user);
        curentUser.save();

        return "redirect:/users/"+user.getId();
    }
}