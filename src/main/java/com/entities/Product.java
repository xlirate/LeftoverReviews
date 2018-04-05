package com.entities;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

@Entity
public class Product {

    @Id
    @GeneratedValue
    private Long id;

    @NotNull
    private String url;

    @NotNull
    @ManyToOne
    private User creator;

    @NotNull
    private String description;

    @NotNull
    private String name;

    @OneToMany
    private Set<Review> reviews = new HashSet<>();

    private Product(){}

    protected Product(User creator, String name, String url, String description)
    {
        this.description = description;
        this.creator = creator;
        this.url = url;
        this.name = name;
    }

    public Long getId() {
        return id;
    }

	public String getUrl() {
		return url;
	}

    protected void addReview(Review review) {
        this.reviews.add(review);
    }

    public Set<Review> getReviews() {
        return this.reviews;
    }

    public ProductRepository getRepo(){
        return RepoManager.getProductRepository();
    }

    public Product save(){
        return this.getRepo().save(this);
    }

    @Override
    public boolean equals(Object o){
        if(o == null || !(o instanceof Product)){
            return false;
        }else{
            return this.getId().equals(((Product)o).getId());
        }
    }

    public double jacardWeightedScore(User u){
        double totalJScores = 0d;
        double totalRaging = 0d;

        for(Review r : this.getReviews()){
            double jScore = 1d-u.jaccardDistance(r.getWriter());
            totalJScores += jScore;
            totalRaging += r.getScore()*jScore;
        }
        if(totalJScores <= 0){
            return 0d;
        }else {
            return totalRaging / totalJScores;
        }
    }

    public double baconWeightedScore(User u){
        double totalScores = 0d;
        double totalRaging = 0d;

        for(Review r : this.getReviews()){
            Integer score = u.baconDistance(r.getWriter());
            if(score != null && score >0){
                totalScores += 1d/(double)score;
                totalRaging += r.getScore()/score;
            }
        }

        if(totalScores <= 0){
            return 0d;
        }else {
            return totalRaging / totalScores;
        }
    }

    public double averageFollowedScore(User u){
        double score = 0d;
        double total = 0d;
        for(Review r : this.getReviews()){
            if(u.getFollowedUsers().contains(r.getWriter())) {
                score += r.getScore();
                total += 1d;
            }
        }
        if(total <= 0){
            return 0d;
        }else{
            return score/total;
        }
    }

    public double averageScore(){
        double score = 0d;
        double total = 0d;
        for(Review r : this.getReviews()){
            score += r.getScore();
            total += 1d;
        }
        if(total <= 0){
            return 0d;
        }else{
            return score/total;
        }
    }

    @Override
    public int hashCode(){
        return this.getId().intValue();
    }


    public String getDescription() {
        return this.description;
    }

    public User getCreator() {
        return this.creator;
    }

    public String getName() {
        return this.name;
    }
}