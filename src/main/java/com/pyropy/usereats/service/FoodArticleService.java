package com.pyropy.usereats.service;

import com.pyropy.usereats.dto.FoodArticleDto;
import com.pyropy.usereats.dto.RestaurantDto;
import com.pyropy.usereats.model.FoodArticle;
import com.pyropy.usereats.model.Restaurant;
import com.pyropy.usereats.repository.FoodArticleRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.expression.ExpressionException;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class FoodArticleService {

    @Autowired
    private FoodArticleRepository foodArticleRepository;

    @Autowired
    private RestaurantService restaurantService;

    @Autowired
    private ModelMapper modelMapper;


    /*
     * Converts FoodArticle Entity to FoodArticleDto class.
     * */
    public FoodArticleDto convertToDto(FoodArticle foodArticle) {
        return modelMapper.map(foodArticle, FoodArticleDto.class);
    }

    /*
     * Converts FoodArticleDto to FoodArticle entity.
     * */
    public FoodArticle convertToEntity(FoodArticleDto foodArticleDto) {
        return modelMapper.map(foodArticleDto, FoodArticle.class);
    }

    public List<FoodArticleDto> convertIterableToListDto(Iterable<FoodArticle> foodArticles) {
        return StreamSupport
                .stream(foodArticles.spliterator(), false)
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    public FoodArticleDto findById(Long id) {
        return foodArticleRepository.findById(id).map(this::convertToDto)
                .orElseThrow(() -> new EntityNotFoundException("Food article not found."));
    }

    public List<FoodArticleDto> findAll() {
        return convertIterableToListDto(foodArticleRepository.findAll());
    }

    public void deleteFoodArticle(FoodArticleDto foodArticleDto, String username) {
        foodArticleRepository
                .findFoodArticleByIdAndRestaurantOwnerUsername(foodArticleDto.getId(), username)
                .map(foodArticle -> {
                    foodArticleRepository.delete(foodArticle);
                    return null;
                });
    }

    public FoodArticleDto updateFoodArticle(FoodArticleDto foodArticleDto, String username) {
        return foodArticleRepository
                .findFoodArticleByIdAndRestaurantOwnerUsername(foodArticleDto.getId(), username)
                .map(foodArticle -> {
                    foodArticle.setName(foodArticle.getName());
                    foodArticle.setPrice(foodArticleDto.getPrice());
                    foodArticle.setImageUrl(foodArticleDto.getImageUrl());
                    foodArticle.setDescription(foodArticleDto.getDescription());
                    return convertToDto(foodArticle);
                })
                .orElseThrow(() -> new ExpressionException("Food article not found."));
    }

    public List<FoodArticleDto> findFoodArticlesByRestaurantId(Long id) {
        return convertIterableToListDto(foodArticleRepository.findFoodArticleByRestaurantId(id));
    }

    public FoodArticleDto createFoodArticle(FoodArticleDto foodArticleDto, String username) {
        RestaurantDto restaurantDto = restaurantService
                .findRestaurantByIdAndOwnerUsername(foodArticleDto.getRestaurantId(), username);
        Restaurant restaurant = restaurantService.convertToEntity(restaurantDto);
        FoodArticle foodArticle = convertToEntity(foodArticleDto);
        foodArticle.setRestaurant(restaurant);
        foodArticleRepository.save(foodArticle);
        return convertToDto(foodArticle);
    }
}
