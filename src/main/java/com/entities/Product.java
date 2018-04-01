package com.entities;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;

@Entity
public class Product {

    @Id
    @GeneratedValue
    private Long id;

    @NotNull
    private String url;

    @NotNull
    private User creator;

    //can be null, replace null with something like "No description given"
    private String description;

    private Product(){}

    public Product(User creator, String url, String description)
    {
        this.description = description;
        this.creator = creator;
        this.url = url;
    }

    public Product(User creator, String url)
    {
        this(creator, url, null);
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

	public void setUrl(String url) {
		this.url = url;
	}

}