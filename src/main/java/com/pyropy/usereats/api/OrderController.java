package com.pyropy.usereats.api;

import com.pyropy.usereats.model.FoodArticle;
import com.pyropy.usereats.model.Order;
import com.pyropy.usereats.model.User;
import com.pyropy.usereats.service.JwtService;
import com.pyropy.usereats.service.OrderService;
import com.pyropy.usereats.service.UserModelDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/api/v1/order")
public class OrderController {

    @Autowired
    OrderService orderService;

    @Autowired
    UserModelDetailsService userModelDetailsService;

    @Autowired
    JwtService jwtService;

    @PostMapping
    public Order createOrder(Authentication authentication, FoodArticle foodArticle) {
        User user = userModelDetailsService.findUserByAuthentication(authentication);
        return orderService.createOrder(foodArticle, user);
    }
}
