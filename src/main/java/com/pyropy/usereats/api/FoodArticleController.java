package com.pyropy.usereats.api;

import com.pyropy.usereats.model.FoodArticle;
import com.pyropy.usereats.model.Restaurant;
import com.pyropy.usereats.service.FoodArticleService;
import com.pyropy.usereats.service.JwtService;
import com.pyropy.usereats.service.RestaurantService;
import org.springframework.beans.factory.annotation.Autowired;
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
        return foodArticleService.findAll();
    }

    @GetMapping(value = "{restaurantId}")
    public List<FoodArticle> getRestaurantFoodArticle(@PathParam("restaurantId") Long restaurantId) {
        return foodArticleService.findFoodArticlesByRestaurantId(restaurantId);
    }

    @PostMapping(value = "{restaurantId}")
    public FoodArticle createFoodArticle(@RequestHeader("Authorization") String token,
                                         @PathParam("restaurantId") Long restaurantId,
                                         @RequestBody FoodArticle foodArticle) {

        String username = jwtService.getUsernameFromToken(token);
        Restaurant restaurant = restaurantService.findRestaurantByIdAndOwnerUsername(
                restaurantId, username).orElseThrow(() -> new EntityNotFoundException("Restaurant not found or you are not restaurant owner."));
        return foodArticleService.save(foodArticle, restaurant);
    }
}
