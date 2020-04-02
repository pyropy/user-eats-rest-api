package com.pyropy.usereats.api;

import com.pyropy.usereats.dto.FoodArticleDto;
import com.pyropy.usereats.service.FoodArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityNotFoundException;
import javax.websocket.server.PathParam;
import java.util.List;

@RestController
@RequestMapping("/api/v1/food")
public class FoodArticleController {

    @Autowired
    private FoodArticleService foodArticleService;

    @GetMapping
    public List<FoodArticleDto> getAllFoodArticles() {
        // todo: add pagination
        return foodArticleService.findAll();
    }

    @GetMapping(value = "{restaurantId}")
    public List<FoodArticleDto> getRestaurantFoodArticles(@PathVariable("restaurantId") Long restaurantId) {
        return foodArticleService.findFoodArticlesByRestaurantId(restaurantId);
    }

    @PostMapping
    public FoodArticleDto createFoodArticle(Authentication authentication,
                                            @RequestBody FoodArticleDto foodArticleDto) {
        return foodArticleService.createFoodArticle(foodArticleDto, authentication.getName());
    }

    @PutMapping
    public FoodArticleDto updateFoodArticle(Authentication authentication,
                                            @RequestBody FoodArticleDto foodArticleInfo) {
        return foodArticleService.updateFoodArticle(foodArticleInfo, authentication.getName());
    }

    @DeleteMapping
    public void deleteFoodArticle(Authentication authentication,
                                  @RequestBody FoodArticleDto foodArticleInfo) {
        foodArticleService.deleteFoodArticle(foodArticleInfo, authentication.getName());
    }

}
