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
    private String productName;

    @NotNull
    @ManyToOne
    private User creator;

    @NotNull
    private String description;

    @OneToMany
    private Set<Review> reviews = new HashSet<>();

    private Product(){}

    protected Product(User creator, String url, String description)
    {
        this.description = description;
        this.creator = creator;
        this.url = url;
    }

    private String getName() {
        return productName;
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
        return Collections.unmodifiableSet(this.reviews);
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
}