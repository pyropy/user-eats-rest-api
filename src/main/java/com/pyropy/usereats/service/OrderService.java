package com.pyropy.usereats.service;

import com.pyropy.usereats.model.FoodArticle;
import com.pyropy.usereats.model.Order;
import com.pyropy.usereats.model.OrderStatus;
import com.pyropy.usereats.model.User;
import com.pyropy.usereats.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityNotFoundException;
import java.util.List;

@Service
public class OrderService {

    private final OrderRepository orderRepository;
    private final UserNotificationService userNotificationService;

    @Autowired
    private OrderService(OrderRepository orderRepository,
                         @Qualifier("sendGrid") UserNotificationService userNotificationService) {
        this.orderRepository = orderRepository;
        this.userNotificationService = userNotificationService;
    }

    public Order saveOrder(Order order) {
        return orderRepository.save(order);
    }

    public Order findById(Long id) {
        return this.orderRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Order not found."));
    }

    /* Creates new order for user if one not already opened */
    public Order createOrder(FoodArticle foodArticle, User user, Integer quantity) {
        orderRepository
                .findOrderByUserAndOrderStatus(user, OrderStatus.OPEN)
                .ifPresent(order -> {
                    throw new EntityExistsException("Already found opened order.");
                });
        Order order = new Order(user);
        return updateOrderWithFoodArticle(order, foodArticle, quantity);
    }

    public List<Order> findUserOrders(String username) {
        return orderRepository.findOrderByUserUsername(username);
    }

    public Order findOrderByIdAndUsername(Long id, String username) {
        return orderRepository.findOrderByIdAndUserUsername(id, username)
                .orElseThrow(() -> new EntityNotFoundException("Order not found."));
    }

    public Order updateOrderWithFoodArticle(Order order, FoodArticle foodArticle, Integer quantity) {
//        order.addFoodArticle(newFoodArticle);
//        order.setSubtotal(order.getSubtotal() + newFoodArticle.getPrice());
        saveOrder(order);
        return order;
    }

//    public void updateOrderStatus(Long orderId, String username, OrderStatus newOrderStatus) {
//        Order order = findOrderByIdAndUsername(orderId, username);
//        HashMap<User, List<FoodArticle>> foodArticleOrders = new HashMap<>();
//        order.setOrderStatus(newOrderStatus);
//        if (order.getOrderStatus() == OrderStatus.CONFIRMED) {
//           order.getArticles().stream().map(foodArticle -> {
//                   User owner = foodArticle.getRestaurant().getOwner();
//                   foodArticleOrders.get(owner).add(foodArticle);
//                   foodArticleOrders.put(owner, foodArticleOrders);
//        })
//        saveOrder(order);
//    }
}
