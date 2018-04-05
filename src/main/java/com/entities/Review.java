package com.entities;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
public class Review {

    @Id
    @GeneratedValue
    private Long id;

    @NotNull
    @ManyToOne
    private Product product;

    @NotNull
    @ManyToOne
    private User writer;

    @NotNull
    private String description;

    @NotNull
    private Double score;

    private Review(){}

    protected Review(Product product, User writer, String description, Double score){
        this.product = product;
        this.writer = writer;
        this.description = description;
        this.score = score;
    }

    public Product getProduct(){
        return this.product;
    }

    public User getWriter(){
        return this.writer;
    }

    public String getDescription(){
        return this.description;
    }

    public Double getScore(){
        return this.score;
    }

    public ReviewRepository getRepo(){
        return RepoManager.getReviewRepository();
    }

    @Override
    public boolean equals(Object o){
        if(o == null || !(o instanceof Review)){
            return false;
        }else{
            return this.getId().equals(((Review)o).getId());
        }
    }

    public Review save(){
        return this.getRepo().save(this);
    }

    @Override
    public int hashCode(){
        return this.getId().intValue();
    }

    public Long getId() {
        return id;
    }

    public void update(String description, Double score) {
        this.description = description;
        this.score = score;
        this.getRepo().save(this);
    }
}
