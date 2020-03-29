package com.pyropy.usereats.api;

import com.pyropy.usereats.dto.RestaurantDto;
import com.pyropy.usereats.service.RestaurantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import javax.websocket.server.PathParam;
import java.util.List;

@RestController
@RequestMapping("/api/v1/restaurants")
public class RestaurantController {

    @Autowired
    private RestaurantService restaurantService;

    @GetMapping
    public List<RestaurantDto> getAllRestaurants() {
        return restaurantService.findAll();
    }

    @GetMapping(value = "{name}")
    public List<RestaurantDto> getResturauntsByName(@PathParam("name") String name) {
        return restaurantService.findByNameLike(name);
    }

    @GetMapping(path = "me")
    @Secured(value = "ROLE_RESTAURANT_ADMIN")
    public List<RestaurantDto> getUserRestaurants(Authentication authentication) {
        return restaurantService.findByOwnerUsername(authentication.getName());
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public RestaurantDto createRestaurant(Authentication authentication, @RequestBody RestaurantDto restaurantInfo) {
        return restaurantService.createRestaurant(restaurantInfo, authentication.getName());
    }

    @PutMapping
    public RestaurantDto updateRestaurant(Authentication authentication, @RequestBody RestaurantDto restaurantInfo) {
        return restaurantService.updateRestaurant(restaurantInfo, authentication.getName());
    }

    @DeleteMapping
    public void deleteRestaurant(Authentication authentication, @RequestBody RestaurantDto restaurantInfo) {
        restaurantService.deleteRestaurant(restaurantInfo, authentication.getName());
    }
}
