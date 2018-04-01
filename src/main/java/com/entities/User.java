package com.entities;

import org.springframework.beans.factory.annotation.Autowired;

import java.util.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
public class User {

    @Autowired
    @Transient
    private static UserRepository repo;
	public static UserRepository getRepo(){return repo;}

    @Id
    @GeneratedValue
	private Long id;

    @NotNull
	private String username;

    @OneToMany
    private Set<Product> createdProducts = new HashSet<>();

    @OneToMany
    private Set<Review> writenReviews = new HashSet<>();

    @ManyToMany
    private Set<User> followedUsers = new HashSet<>();

    private User(){}

    public User(String username){
		this.username = username;

        if(this.id == null) {
            this.id = repo.save(this).id;
        }
	}

    public Review writeReview(Product product, String description, Double score){
        Review r = new Review(product, this, description, score);
        this.writenReviews.add(r);
        return r;
    }

    public Product createProduct(String url, String description){
        Product product = new Product(this, url, description);
        this.createdProducts.add(product);
        return product;
    }

    /**
        returns the Jaccard distance to another user
        This sums the differance between scores of pairs of reviews on items that both users reviwed.
        https://en.wikipedia.org/wiki/Jaccard_index
     */
    public double jaccardDistance(User other){
        List<Review> s1 = new ArrayList<>(this.writenReviews);
        List<Review> s2 = new ArrayList<>(other.writenReviews);

        Comparator<Review> comp = new Comparator<Review>(){
            @Override
            public int compare(Review r1, Review r2) {
                return r1.getProduct().getId().compareTo(r2.getProduct().getId());
            }
        };

        Collections.sort(s1, comp);
        Collections.sort(s2, comp);

        //now both lists of reviews are sorted in assending order by their product's id
        //we now remove all unmatched reviews so that index maches up

        int i = 0;
        while(i<s1.size() && 1<s2.size()){
            Review r1 = s1.get(i);
            Review r2 = s2.get(i);
            if(r1.getProduct().getId() == r2.getProduct().getId()){
                i++;
            }else if(r1.getProduct().getId() < r2.getProduct().getId()){
                s1.remove(i);
            }else {//r1.getProduct().getId() > r2.getProduct().getId()
                s2.remove(i);
            }
        }

        int count = 0;
        double value = 0;
        for(int j = 0; j<s1.size() && j<s2.size(); j++){
            count ++;
            value += 1-Math.abs(s1.get(j).getScore()-s2.get(j).getScore());
        }

        if(count <= 0){
            return 1d;
        }else{
            return 1-(value/count);
        }
    }

    /**
        We only go forward through follow links
        I don't care if you follow a persion that I consider to be good, I care if they follow you
        @return null if we cannot find the distance
     */
    @Transient
    private static final int MAX_BACON_DEPTH = 6;
    public Integer baconDistance(User other){

        if(this.equals(other)){
            return 0;
        }

        Set<User> checked = new HashSet<>();
        checked.add(this);
        for(int i = 1; i<=MAX_BACON_DEPTH; i++){
            int count = checked.size();
            for(User u : new HashSet<>(checked)){
                checked.addAll(u.followedUsers);
            }
            if(checked.size() == count){
                return null;
            }
            if(checked.contains(other)) {
                return i;
            }
        }
        return null;
    }

    public void follow(User other){
        followedUsers.add(other);
    }

	public Long getId() {
		return id;
	}

	public String getUsername() {
		return username;
	}

    @Override
    public boolean equals(Object o){
        if(o == null || !(o instanceof User)){
            return false;
        }else{
            return this.id.equals(((User)o).id);
        }
    }

    @Override
    public int hashCode(){
        return this.id.intValue();
    }
}
