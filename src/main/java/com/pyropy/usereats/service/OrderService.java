package com.pyropy.usereats.service;

import com.pyropy.usereats.dto.OrderArticleDto;
import com.pyropy.usereats.dto.OrderDto;
import com.pyropy.usereats.model.*;
import com.pyropy.usereats.repository.OrderRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private UserService userService;

    @Autowired
    private OrderArticleService orderArticleService;

    /*
     * Converts Order Entity to OrderDto class.
     * */
    public OrderDto convertToDto(Order order) {
        return modelMapper.map(order, OrderDto.class);
    }

    /*
     * Converts OrderDto entity to Order class.
     * */
    public Order convertToEntity(OrderDto orderDto) {
        return modelMapper.map(orderDto, Order.class);
    }

    public List<OrderDto> convertIterableToListDto(Iterable<Order> orders) {
        return StreamSupport
                .stream(orders.spliterator(), false)
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    public Order findById(Long id) {
        return this.orderRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Order not found."));
    }

    /* Creates new order for user if one not already opened */
    public OrderDto createOrder(OrderArticleDto orderArticleDto, String username) {
        User user = userService.convertToEntity(userService.findByUsername(username));
        orderRepository
                .findOrderByUserAndOrderStatus(user, OrderStatus.OPEN)
                .map(order -> updateOrderWithFoodArticle(convertToDto(order), orderArticleDto));
        Order order = new Order(user);
        return updateOrderWithFoodArticle(convertToDto(order), orderArticleDto);
    }

    public List<OrderDto> findUserOrders(String username) {
        return convertIterableToListDto(orderRepository.findOrderByUserUsername(username));
    }

    public OrderDto findOrderByIdAndUsername(Long id, String username) {
        return orderRepository.findOrderByIdAndUserUsername(id, username)
                .map(this::convertToDto)
                .orElseThrow(() -> new EntityNotFoundException("Order not found."));
    }

    public OrderDto updateOrderArticles(OrderArticleDto orderArticleDto, String username) {
        OrderDto orderDto = findOrderByIdAndUsername(orderArticleDto.getOrderId(), username);
        return updateOrderWithFoodArticle(orderDto, orderArticleDto);
    }

    private OrderDto updateOrderWithFoodArticle(OrderDto orderDto, OrderArticleDto orderArticleDto) {
        OrderArticles newOrderArticle = orderArticleService.convertToEntity(orderArticleDto);
        Order order = convertToEntity(orderDto);
        List<OrderArticles> orderArticles = order.getOrderArticles();
        orderArticles.removeIf(article -> article.getArticle().getId() == newOrderArticle.getArticle().getId());
        orderArticles.add(newOrderArticle);
        order.setOrderArticles(orderArticles);
        orderRepository.save(order);
        return convertToDto(order);
    }

    public OrderDto updateOrderStatus(OrderDto orderDto, OrderStatus orderStatus, String username) {
        // todo: add sending email of confirmed
        orderRepository.findOrderByIdAndUserUsername(orderDto.getOrderId(), username)
                .map(order -> {
                    order.setOrderStatus(orderStatus);
                    return convertToDto(order);
                }).orElseThrow(() -> new EntityNotFoundException("Order not found"));
        return null;
    }

}
