package com.entities;

import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
public class Review {

    @Autowired
    @Transient
    private static ReviewRepository repo;
    public static ReviewRepository getRepo(){return repo;}

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

        product.addReview(this);

        if(this.id == null) {
            this.id = repo.save(this).id;
        }
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

    @Override
    public boolean equals(Object o){
        if(o == null || !(o instanceof Review)){
            return false;
        }else{
            return this.id.equals(((Review)o).id);
        }
    }

    @Override
    public int hashCode(){
        return this.id.intValue();
    }
}
