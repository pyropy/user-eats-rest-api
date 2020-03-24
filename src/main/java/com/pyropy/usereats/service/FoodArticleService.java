package com.pyropy.usereats.service;

import com.pyropy.usereats.model.FoodArticle;
import com.pyropy.usereats.model.Restaurant;
import com.pyropy.usereats.repository.FoodArticleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class FoodArticleService {

    private final FoodArticleRepository foodArticleRepository;

    @Autowired
    public FoodArticleService(FoodArticleRepository foodArticleRepository) {
        this.foodArticleRepository = foodArticleRepository;
    }

    public List<FoodArticle> findAll() {
        List<FoodArticle> foodArticles = new ArrayList<>();
        foodArticleRepository.findAll().forEach(foodArticles::add);
        return foodArticles;
    }

    public void deleteFoodArticle(FoodArticle foodArticle) {
        foodArticleRepository.delete(foodArticle);
    }

    public FoodArticle saveFoodArticle(FoodArticle foodArticle) {
        return foodArticleRepository.save(foodArticle);
    }

    public FoodArticle updateFoodArticle(FoodArticle foodArticle, FoodArticle updatedFoodArticle) {
        foodArticle.setName(updatedFoodArticle.getName());
        foodArticle.setDescription(updatedFoodArticle.getDescription());
        foodArticle.setImageUrl(updatedFoodArticle.getImageUrl());
        foodArticle.setPrice(updatedFoodArticle.getPrice());
        return saveFoodArticle(foodArticle);
    }

    public List<FoodArticle> findFoodArticlesByRestaurantId(Long id) {
        return foodArticleRepository.findFoodArticleByRestaurantId(id);
    }

    public FoodArticle createFoodArticle(FoodArticle foodArticleInfo, Restaurant restaurant) {
        FoodArticle foodArticle = new FoodArticle(foodArticleInfo.getName(),
                foodArticleInfo.getDescription(), foodArticleInfo.getPrice(),
                foodArticleInfo.getImageUrl(), restaurant);
        return saveFoodArticle(foodArticle);
    }

    public Optional<FoodArticle> findFoodArticleByIdAndRestaurantOwnerUsername(Long id, String username) {
        return foodArticleRepository.findFoodArticleByIdAndRestaurantOwnerUsername(id, username);
    }
}
