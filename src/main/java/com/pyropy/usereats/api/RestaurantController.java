package com.pyropy.usereats.api;

import com.pyropy.usereats.model.Restaurant;
import com.pyropy.usereats.model.User;
import com.pyropy.usereats.service.JwtService;
import com.pyropy.usereats.service.RestaurantService;
import com.pyropy.usereats.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityNotFoundException;
import java.util.List;

@RestController
@RequestMapping("/api/v1/restaurants")
public class RestaurantController {

    @Autowired
    private RestaurantService restaurantService;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private UserService userService;

    @GetMapping
    public List<Restaurant> getAllRestaurants() {
        return restaurantService.findAll();
    }

    @GetMapping("/{name}")
    public List<Restaurant> getResturauntsByName(@PathVariable("name") String name) {
        return restaurantService.findByNameLike(name);
    }

    @GetMapping("/me")
    public List<Restaurant> getUserRestaurants(@RequestHeader("Authorization") String token) {
        String username = jwtService.getUsernameFromToken(token);
        return restaurantService.findByOwnerUsername(username);
    }

    @PostMapping
    public Restaurant createRestaurant(@RequestHeader("Authorization") String token, @RequestBody Restaurant restaurantInfo) {
        // todo: Limit action by role
        String username = jwtService.getUsernameFromToken(token);
        User user = userService.findByUsername(username);
        return restaurantService.createRestaurant(restaurantInfo, user);
    }

    @PutMapping("/{id}")
    public Restaurant updateRestaurant(@RequestHeader("Authorization") String token, @PathVariable("id") Long id, @RequestBody Restaurant restaurantInfo) {
        String username = jwtService.getUsernameFromToken(token);
        return restaurantService.findRestaurantByIdAndOwnerUsername(id, username).map(restaurant -> {
            restaurant.setName(restaurantInfo.getName());
            restaurant.setDescription(restaurantInfo.getDescription());
            return restaurantService.save(restaurant);
        }).orElseThrow(() -> new EntityNotFoundException("Restaurant with given id not found or not owned by you."));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> updateRestaurant(@RequestHeader("Authorization") String token, @PathVariable("id") Long id) {
        String username = jwtService.getUsernameFromToken(token);
        return restaurantService.findRestaurantByIdAndOwnerUsername(id, username).map(restaurant -> {
            restaurantService.delete(restaurant);
            return ResponseEntity.ok().build();
        }).orElseThrow(() -> new EntityNotFoundException("Restaurant with given id not found or not owned by you."));
    }
}
