package com.pyropy.usereats.api;

import com.pyropy.usereats.model.Restaurant;
import com.pyropy.usereats.model.User;
import com.pyropy.usereats.service.JwtService;
import com.pyropy.usereats.service.RestaurantService;
import com.pyropy.usereats.service.UserModelDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityNotFoundException;
import javax.websocket.server.PathParam;
import java.util.List;

@RestController
@RequestMapping("/api/v1/restaurants")
public class RestaurantController {

    @Autowired
    RestaurantService restaurantService;

    @Autowired
    JwtService jwtService;

    @Autowired
    UserModelDetailsService userModelDetailsService;

    @GetMapping
    public List<Restaurant> getAllRestaurants() {
        return restaurantService.findAll();
    }

    @GetMapping(value = "{name}")
    public List<Restaurant> getResturauntsByName(@PathParam("name") String name) {
        return restaurantService.findByNameLike(name);
    }

    @GetMapping(path = "me")
    public List<Restaurant> getUserRestaurants(Authentication authentication) {
        return restaurantService.findByOwnerUsername(authentication.getName());
    }

    @PostMapping
    public Restaurant createRestaurant(Authentication authentication, @RequestBody Restaurant restaurantInfo) {
        User user = userModelDetailsService.findUserByAuthentication(authentication);
        return restaurantService.createRestaurant(restaurantInfo, user);
    }

    @PutMapping
    public Restaurant updateRestaurant(Authentication authentication, @RequestBody Restaurant restaurantInfo) {
        return restaurantService.findRestaurantByIdAndOwnerUsername(restaurantInfo.getId(), authentication.getName())
                .map(restaurant -> restaurantService.updateRestaurant(restaurant, restaurantInfo))
                .orElseThrow(() -> new EntityNotFoundException("Restaurant with given id not found or not owned by you."));
    }

    @DeleteMapping
    public ResponseEntity<?> deleteRestaurant(Authentication authentication, @RequestBody Restaurant restaurantInfo) {
        return restaurantService.findRestaurantByIdAndOwnerUsername(restaurantInfo.getId(), authentication.getName())
                .map(restaurant -> {
                    restaurantService.delete(restaurant);
                    return ResponseEntity.ok().build();
                }).orElseThrow(() -> new EntityNotFoundException("Restaurant with given id not found or not owned by you."));
    }
}
