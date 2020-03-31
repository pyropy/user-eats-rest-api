package com.pyropy.usereats.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table(name = "FOOD_ARTICLE_ORDER")
public class FoodArticleOrder {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ID", updatable = false, nullable = false)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "ORDERS_ID", nullable = false)
    private Order order;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "FOOD_ARTICLE_ID", nullable = false)
    private FoodArticle foodArticle;

    @Column
    private Integer quantity;

    public FoodArticleOrder() {
    }

    public FoodArticleOrder(FoodArticle foodArticle, Order order, Integer quantity) {
        this.order = order;
        this.foodArticle = foodArticle;
        this.quantity = quantity;
    }
}
