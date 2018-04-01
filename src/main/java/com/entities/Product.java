package com.entities;

import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

@Entity
public class Product {

    @Autowired
    @Transient
    private static ProductRepository repo;
    public static ProductRepository getRepo(){return repo;}

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

    @OneToMany
    private Set<Review> reviews = new HashSet<>();

    private Product(){}

    protected Product(User creator, String url, String description)
    {
        this.description = description;
        this.creator = creator;
        this.url = url;

        if(this.id == null) {
            this.id = repo.save(this).id;
        }
    }

    public Long getId() {
		return id;
	}

	private void setId(Long id) {
		this.id = id;
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

    @Override
    public boolean equals(Object o){
        if(o == null || !(o instanceof Product)){
            return false;
        }else{
            return this.id.equals(((Product)o).id);
        }
    }

    @Override
    public int hashCode(){
        return this.id.intValue();
    }


}