package com.pyropy.usereats.service;

import com.pyropy.usereats.model.FoodArticle;
import com.pyropy.usereats.model.Order;
import com.pyropy.usereats.model.OrderStatus;
import com.pyropy.usereats.model.User;
import com.pyropy.usereats.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityExistsException;

@Service
public class OrderService {

    private final OrderRepository orderRepository;

    @Autowired
    private OrderService(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    public Order saveOrder(Order order) {
        return orderRepository.save(order);
    }

    /* Creates new order for user if one not already opened */
    public Order createOrder(FoodArticle foodArticle, User user) {
        orderRepository
                .findOrderByUserAndOrderStatus(user, OrderStatus.OPEN)
                .ifPresent(order -> {
                    throw new EntityExistsException("Already found opened order.");
                });
        Order order = new Order(user, foodArticle);
        return saveOrder(order);
    }
}
