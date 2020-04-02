package com.pyropy.usereats.service;

import com.pyropy.usereats.dto.OrderFoodArticleDto;
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
    private FoodArticleOrderService foodArticleOrderService;

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

    public Order findEntityById(Long id) {
        return this.orderRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Order not found."));
    }

    /* Creates new order for user if one not already opened */
    public OrderDto createOrder(OrderFoodArticleDto orderFoodArticleDto, String username) {
        User user = userService.convertToEntity(userService.findByUsername(username));
        Order order = new Order();
        order.setUser(user);
        order.setOrderStatus(OrderStatus.OPEN);
        orderRepository.save(order);
        return updateOrderWithFoodArticle(convertToDto(order), orderFoodArticleDto);
    }

    public List<OrderDto> findUserOrders(String username) {
        return convertIterableToListDto(orderRepository.findOrderByUserUsername(username));
    }

    public OrderDto findOrderByIdAndUsername(Long id, String username) {
        return orderRepository.findOrderByIdAndUserUsername(id, username)
                .map(this::convertToDto)
                .orElseThrow(() -> new EntityNotFoundException("Order not found."));
    }

    public OrderDto updateOrderArticles(OrderFoodArticleDto orderFoodArticleDto, String username) {
        OrderDto orderDto = findOrderByIdAndUsername(orderFoodArticleDto.getOrderId(), username);
        return updateOrderWithFoodArticle(orderDto, orderFoodArticleDto);
    }

    private OrderDto updateOrderWithFoodArticle(OrderDto orderDto, OrderFoodArticleDto orderFoodArticleDto) {
        Order order = findEntityById(orderDto.getId());
        orderFoodArticleDto.setOrderId(order.getId()); // double check
        foodArticleOrderService.updateFoodArticleOrder(orderFoodArticleDto);
        order.setSubtotal(calculateSubtotal(order));
        order.setTotal(calculateTotal(order));
        orderRepository.save(order);
        return convertToDto(order);
    }

    public OrderDto updateOrderStatus(OrderDto orderDto, OrderStatus orderStatus, String username) {
        // todo: add sending email of confirmed
        return orderRepository.findOrderByIdAndUserUsername(orderDto.getId(), username)
                .map(order -> {
                    order.setOrderStatus(orderStatus);
                    orderRepository.save(order);
                    return convertToDto(order);
                }).orElseThrow(() -> new EntityNotFoundException("Order not found"));
    }


    private Float calculateSubtotal(Order order) {
        return order.getFoodArticles()
                .stream()
                .map(article -> article.getQuantity() * article.getFoodArticle().getPrice())
                .reduce(0f, Float::sum);
    }

    private Float calculateTotal(Order order) {
        return calculateSubtotal(order);
    }
}
