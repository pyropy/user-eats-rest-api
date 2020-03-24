package com.pyropy.usereats.api;

import com.pyropy.usereats.model.FoodArticle;
import com.pyropy.usereats.model.Restaurant;
import com.pyropy.usereats.service.FoodArticleService;
import com.pyropy.usereats.service.JwtService;
import com.pyropy.usereats.service.RestaurantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityNotFoundException;
import javax.websocket.server.PathParam;
import java.util.List;

@RestController
@RequestMapping("/api/v1/food")
public class FoodArticleController {

    @Autowired
    FoodArticleService foodArticleService;

    @Autowired
    RestaurantService restaurantService;

    @Autowired
    JwtService jwtService;

    @GetMapping
    public List<FoodArticle> getAllFoodArticles() {
        // todo: add pagination
        return foodArticleService.findAll();
    }

    @GetMapping(value = "{restaurantId}")
    public List<FoodArticle> getRestaurantFoodArticle(@PathParam("restaurantId") Long restaurantId) {
        return foodArticleService.findFoodArticlesByRestaurantId(restaurantId);
    }

    @PostMapping(value = "{restaurantId}")
    public FoodArticle createFoodArticle(Authentication authentication,
                                         @PathParam("restaurantId") Long restaurantId,
                                         @RequestBody FoodArticle foodArticle) {
        Restaurant restaurant = restaurantService.findRestaurantByIdAndOwnerUsername(
                restaurantId, authentication.getName()).orElseThrow(() -> new EntityNotFoundException("Restaurant not found or you are not restaurant owner."));
        return foodArticleService.createFoodArticle(foodArticle, restaurant);
    }

    @PutMapping
    public FoodArticle updateFoodArticle(Authentication authentication,
                                         @RequestBody FoodArticle foodArticleInfo) {
        return foodArticleService
                .findFoodArticleByIdAndRestaurantOwnerUsername(foodArticleInfo.getId(), authentication.getName())
                .map(foodArticle -> foodArticleService.updateFoodArticle(foodArticle, foodArticleInfo))
                .orElseThrow(() -> new EntityNotFoundException("Food article not found."));
    }

    @DeleteMapping
    public ResponseEntity<?> deleteFoodArticle(Authentication authentication,
                                               @RequestBody FoodArticle foodArticleInfo) {
        return foodArticleService
                .findFoodArticleByIdAndRestaurantOwnerUsername(foodArticleInfo.getId(), authentication.getName())
                .map(foodArticle -> {
                    foodArticleService.deleteFoodArticle(foodArticle);
                    return ResponseEntity.ok().build();
                })
                .orElseThrow(() -> new EntityNotFoundException("Food article not found."));
    }

}
