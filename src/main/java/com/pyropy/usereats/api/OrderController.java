package com.pyropy.usereats.api;

import com.pyropy.usereats.model.*;
import com.pyropy.usereats.service.OrderService;
import com.pyropy.usereats.service.UserSecurityDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import javax.websocket.server.PathParam;
import java.util.List;


@RestController
@RequestMapping("/api/v1/order")
public class OrderController {

    @Autowired
    OrderService orderService;

    @Autowired
    UserSecurityDetailsService userSecurityDetailsService;

    /* Get user order by order id */
    @GetMapping(value = "{id}")
    public Order getUserOrder(Authentication authentication,
                              @PathParam("id") Long id) {
        return orderService.findOrderByIdAndUsername(id, authentication.getName());
    }

    /* Get all user orders */
    @GetMapping(path = "all")
    public List<Order> getUserOrders(Authentication authentication) {
        return orderService.findUserOrders(authentication.getName());
    }

    @PostMapping
    public Order createOrder(Authentication authentication,
                             @RequestBody OrderArticles articles) {
//                             @RequestBody FoodArticle foodArticle,
//                             @RequestParam Integer quantity) {
        User user = userSecurityDetailsService.findUserByAuthentication(authentication);
        return orderService.createOrder(articles.getArticle(), user, articles.getQuantity());
    }

    @PutMapping
    public Order updateOrderFoodArticles(Authentication authentication,
                                         @RequestBody OrderArticles orderArticles) {
        Order order = orderService.findOrderByIdAndUsername(orderArticles.getOrderId(), authentication.getName());
        return orderService.updateOrderWithFoodArticle(order, orderArticles.getArticle(), orderArticles.getQuantity());
    }

    @PutMapping(value = "{id}/{status}")
    public ResponseEntity<?> updateOrderStatus(Authentication authentication,
                                               @RequestParam("id") Long id,
                                               @RequestParam("status") OrderStatus status) {
        return ResponseEntity.ok().build();
    }
}
