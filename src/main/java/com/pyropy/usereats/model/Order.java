package com.pyropy.usereats.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


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

    @JsonProperty
    @Column(name = "DELIVERY_ADDRESS")
    private String deliveryAddress;

    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "USER_ID", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private User user;

    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "ORDER_ARTICLES_ID", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private List<OrderArticles> orderArticles = new ArrayList<>();

    public Order() {
    }

    public Order(User user) {
        this.orderStatus = OrderStatus.OPEN;
        this.user = user;
    }

    public Float getSubtotal() {
        return subtotal;
    }

    public void setSubtotal(Float subtotal) {
        this.subtotal = subtotal;
    }

    public long getId() { return id;
    }

    public OrderStatus getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(OrderStatus orderStatus) { this.orderStatus = orderStatus;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getDeliveryAddress() {
        return deliveryAddress;
    }

    public void setDeliveryAddress(String deliveryAddress) {
        this.deliveryAddress = deliveryAddress;
    }

    public List<OrderArticles> getOrderArticles() {
        return orderArticles;
    }

    public void setOrderArticles(List<OrderArticles> orderArticles) {
        this.orderArticles = orderArticles;
    }
}
