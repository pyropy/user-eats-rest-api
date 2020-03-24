package com.pyropy.usereats.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "FOOD_ARTICLE")
public class FoodArticle {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ID")
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private long id;

    @Column(nullable = false, length = 100, name = "NAME")
    @JsonProperty
    private String name;

    @JsonProperty
    @Column(nullable = false, name = "PRICE")
    private Float price;

    @JsonProperty
    @Column(nullable = false, name = "IMAGE_URL")
    private String imageUrl;

    @Column(name = "DESCRIPTION")
    @JsonProperty
    private String description;

    @Column(nullable = false)
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private boolean active;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "RESTAURAUNT_ID", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonIgnore
    private Restaurant restaurant;

    public FoodArticle() {
    }

    public FoodArticle(String name, String description, Float price, String imageUrl, Restaurant restaurant) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.restaurant = restaurant;
        this.active = true;
        this.imageUrl = imageUrl;
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Float getPrice() {
        return price;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public void setPrice(Float price) {
        this.price = price;
    }

    public boolean isActive() {
        return active;
    }
}
