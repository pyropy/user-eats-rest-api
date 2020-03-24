package com.pyropy.usereats.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


@Entity
@Table(name = "ORDERS")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ID")
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private long id;

    @JsonProperty
    @Column(nullable = false, name = "SUBTOTAL")
    private Float subtotal;

    @Column(nullable = false, name = "STATUS")
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    @Enumerated(EnumType.ORDINAL)
    private OrderStatus orderStatus;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinColumn(name = "FOOD_ARTICLE_ID", nullable = false)
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private List<FoodArticle> articles = new ArrayList<>();

    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "USER_ID", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private User user;


    public Order() {
    }

    public Order(User user, FoodArticle foodArticle) {
        this.orderStatus = OrderStatus.OPEN;
        this.user = user;
        this.subtotal = foodArticle.getPrice(); // set initial price to first food article
        this.articles = Collections.singletonList(foodArticle);
    }

    public Float getSubtotal() {
        return subtotal;
    }

    public void setSubtotal(Float subtotal) {
        this.subtotal = subtotal;
    }

    public long getId() {
        return id;
    }

    public OrderStatus getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(OrderStatus orderStatus) {
        this.orderStatus = orderStatus;
    }

    public List<FoodArticle> getArticles() {
        return articles;
    }

    public void setArticles(List<FoodArticle> articles) {
        this.articles = articles;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
