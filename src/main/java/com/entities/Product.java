package com.entities;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;

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

    private Product(){}

    public Product(User creator, String url, String description)
    {
        this.description = description;
        this.creator = creator;
        this.url = url;
    }

    public Product(User creator, String url)
    {
        this(creator, url, "No description given");
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