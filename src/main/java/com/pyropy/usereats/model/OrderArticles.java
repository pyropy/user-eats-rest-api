package com.pyropy.usereats.model;

import javax.persistence.*;

@Entity
public class OrderArticles {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ID")
    private long id;

    @OneToOne
    @JoinColumn(name = "ORDERS_ID", nullable = false)
    private Order order;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "FOOD_ARTICLE_ID", nullable = false)
    private FoodArticle article;

    @Column
    private Integer quantity;

    public OrderArticles() {
    }

    public OrderArticles(Order order, FoodArticle article, Integer quantity) {
        this.order = order;
        this.article = article;
        this.quantity = quantity;
    }

    public Order getOrder() {
        return order;
    }

    public Long getOrderId() {
        return order.getId();
    }

    public FoodArticle getArticle() {
        return article;
    }

    public void setArticle(FoodArticle article) {
        this.article = article;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }
}
