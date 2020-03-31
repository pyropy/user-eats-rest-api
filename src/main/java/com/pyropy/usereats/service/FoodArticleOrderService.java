package com.pyropy.usereats.service;

import com.pyropy.usereats.dto.OrderFoodArticleDto;
import com.pyropy.usereats.model.FoodArticle;
import com.pyropy.usereats.model.FoodArticleOrder;
import com.pyropy.usereats.model.Order;
import com.pyropy.usereats.repository.FoodArticleOrderRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class FoodArticleOrderService {

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private FoodArticleService foodArticleService;

    @Autowired
    private OrderService orderService;

    @Autowired
    private FoodArticleOrderRepository foodArticleOrderRepository;

    /*
     * Converts OrderArticle Entity to OrderArticleDto class.
     * */
    public OrderFoodArticleDto convertToDto(FoodArticleOrder foodArticleOrder) {
        return modelMapper.map(foodArticleOrder, OrderFoodArticleDto.class);
    }

    /*
     * Converts OrderArticleDto entity to OrderArticle class.
     * */
    public FoodArticleOrder convertToEntity(OrderFoodArticleDto orderFoodArticleDto) {
        return modelMapper.map(orderFoodArticleDto, FoodArticleOrder.class);
    }

    public List<OrderFoodArticleDto> convertIterableToListDto(Iterable<FoodArticleOrder> orderArticles) {
        return StreamSupport
                .stream(orderArticles.spliterator(), false)
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    public OrderFoodArticleDto createFoodArticleOrder(OrderFoodArticleDto orderFoodArticleDto) {
        FoodArticle foodArticle = foodArticleService
                .convertToEntity(foodArticleService.findById(orderFoodArticleDto.getFoodArticleId()));

        Order order = orderService.findEntityById(orderFoodArticleDto.getOrderId());
        FoodArticleOrder foodArticleOrder = new FoodArticleOrder(foodArticle, order, orderFoodArticleDto.getQuantity());
        foodArticleOrderRepository.save(foodArticleOrder);
        return convertToDto(foodArticleOrder);
    }

    public OrderFoodArticleDto updateFoodArticleOrder(OrderFoodArticleDto orderFoodArticleDto) {
        Optional<FoodArticleOrder> foodArticleOrder = foodArticleOrderRepository.findFoodArticleOrderByFoodArticleIdAndOrderId(
                orderFoodArticleDto.getFoodArticleId(),
                orderFoodArticleDto.getOrderId());

        if (!foodArticleOrder.isEmpty()) {
            foodArticleOrder.get().setQuantity(orderFoodArticleDto.getQuantity());
            foodArticleOrderRepository.save(foodArticleOrder.get());
            return convertToDto(foodArticleOrder.get());
        }
        return createFoodArticleOrder(orderFoodArticleDto);
    }
}
