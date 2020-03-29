package com.pyropy.usereats.api;

import com.pyropy.usereats.dto.OrderArticleDto;
import com.pyropy.usereats.dto.OrderDto;
import com.pyropy.usereats.model.*;
import com.pyropy.usereats.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import javax.websocket.server.PathParam;
import java.util.List;


@RestController
@RequestMapping("/api/v1/order")
public class OrderController {

    @Autowired
    private OrderService orderService;

    /* Get user order by order id */
    @GetMapping(value = "{id}")
    public OrderDto getUserOrder(Authentication authentication,
                                 @PathParam("id") Long id) {
        return orderService.findOrderByIdAndUsername(id, authentication.getName());
    }

    /* Get all user orders */
    @GetMapping(path = "me")
    public List<OrderDto> getUserOrders(Authentication authentication) {
        return orderService.findUserOrders(authentication.getName());
    }

    @PostMapping
    public OrderDto createOrder(Authentication authentication,
                                @RequestBody OrderArticleDto articles) {
        return orderService.createOrder(articles, authentication.getName());
    }

    @PutMapping
    public OrderDto updateOrderFoodArticles(Authentication authentication,
                                            @RequestBody OrderArticleDto orderArticleDto) {
        return orderService.updateOrderArticles(orderArticleDto, authentication.getName());
    }

    @PutMapping(value = "{status}")
    public OrderDto updateOrderStatus(Authentication authentication,
                                      @RequestBody OrderDto orderDto,
                                      @RequestParam("status") OrderStatus status) {
        return orderService.updateOrderStatus(orderDto, status, authentication.getName());
    }
}
