package com.entities;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class RepoManager{
    private static UserRepository userRepo;
    private static ProductRepository productRepo;
    private static ReviewRepository reviewRepo;


    @Autowired
    private UserRepository userR;
    @Autowired
    private ProductRepository productR;
    @Autowired
    private ReviewRepository reviewR;

    @PostConstruct
    public void init(){
        if(userRepo == null) {
            userRepo = userR;
            reviewRepo = reviewR;
            productRepo = productR;
        }
    }

    public static UserRepository getUserRepository(){
        return userRepo;
    }
    public static ProductRepository getProductRepository(){
        return productRepo;
    }
    public static ReviewRepository getReviewRepository(){
        return reviewRepo;
    }
}
