package com.pyropy.usereats.service;

import com.pyropy.usereats.model.FoodArticle;
import com.pyropy.usereats.model.Restaurant;
import com.pyropy.usereats.repository.FoodArticleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class FoodArticleService {

    private final FoodArticleRepository foodArticleRepository;

    @Autowired
    public FoodArticleService(FoodArticleRepository foodArticleRepository) {
        this.foodArticleRepository = foodArticleRepository;
    }

    public List<FoodArticle> findAll() {
        List foodArticles = new ArrayList<>();
        foodArticleRepository.findAll().forEach(foodArticles::add);
        return foodArticles;
    }

    public List<FoodArticle> findFoodArticlesByRestaurantId(Long id) {
        return foodArticleRepository.findFoodArticleByRestaurantId(id);
    }

    public FoodArticle save(FoodArticle foodArticleInfo, Restaurant restaurant) {
        FoodArticle foodArticle = new FoodArticle(foodArticleInfo.getName(),
                foodArticleInfo.getDescription(), foodArticleInfo.getPrice(), restaurant);
        return foodArticleRepository.save(foodArticle);
    }

}
