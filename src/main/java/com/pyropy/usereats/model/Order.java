package com.pyropy.usereats.model;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;


@Getter
@Setter
@Entity
@Table(name = "ORDERS")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ID", nullable = false, updatable = false)
    private Long id;

    @Column(nullable = false, name = "SUBTOTAL")
    private Float subtotal = (float) 0;

    @Column(nullable = false, name = "TOTAL")
    private Float total = (float) 0;

    @Column(nullable = false, name = "STATUS")
    @Enumerated(EnumType.ORDINAL)
    private OrderStatus orderStatus;

    @Column(name = "DELIVERY_ADDRESS")
    private String deliveryAddress;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private User user;

    @OneToMany(mappedBy = "order")
    private Set<FoodArticleOrder> foodArticles = new HashSet<>();

    public Order() {
    }

    public Order(User user) {
        this.orderStatus = OrderStatus.OPEN;
        this.user = user;
    }
}
