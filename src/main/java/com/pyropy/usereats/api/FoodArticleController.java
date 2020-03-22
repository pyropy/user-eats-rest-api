package com.pyropy.usereats.api;

import com.pyropy.usereats.model.FoodArticle;
import com.pyropy.usereats.service.FoodArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/food")
public class FoodArticleController {

    @Autowired
    FoodArticleService foodArticleService;

    @GetMapping
    public List<FoodArticle> getAllFoodArticles() {
        return foodArticleService.findAll();
    }

    @GetMapping("/{restaurantuntId}")
    public List<FoodArticle> getRestaurantFoodArticle(@PathVariable("restaurantId") Long restaurantId) {
        return foodArticleService.findFoodArticlesByRestaurantId(restaurantId);
    }
}
