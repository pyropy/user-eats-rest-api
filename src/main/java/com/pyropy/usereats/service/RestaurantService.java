package com.pyropy.usereats.service;

import com.pyropy.usereats.model.Restaurant;
import com.pyropy.usereats.model.User;
import com.pyropy.usereats.repository.RestaurantRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class RestaurantService {

    private final RestaurantRepository restaurantRepository;

    @Autowired
    public RestaurantService(RestaurantRepository restaurantRepository) {
        this.restaurantRepository = restaurantRepository;
    }

    public void delete(Restaurant restaurant) {
        restaurantRepository.delete(restaurant);
    }

    public Restaurant save(Restaurant restaurant) {
        return restaurantRepository.save(restaurant);
    }

    public List<Restaurant> findAll() {
        List<Restaurant> restaurants = new ArrayList<>();
        restaurantRepository.findAll().forEach(restaurants::add);
        return restaurants;
    }

    /*
     * Find restaurant by given name.
     */
    public List<Restaurant> findByNameLike(String name) {
        List<Restaurant> restaurants = new ArrayList<>();
        restaurantRepository.findByNameLike(name).forEach(restaurants::add);
        return restaurants;
    }

    /*
     * Find restaurants by given owner username.
     */
    public List<Restaurant> findByOwnerUsername(String username) {
        List<Restaurant> restaurants = new ArrayList<>();
        restaurantRepository.findRestaurantByOwnerUsername(username).forEach(restaurants::add);
        return restaurants;
    }

    public Restaurant createRestaurant(Restaurant restaurantInfo, User user) {
        Restaurant restaurant = new Restaurant(restaurantInfo.getName(),
                restaurantInfo.getDescription(), restaurantInfo.getAddress(), user);
        return restaurantRepository.save(restaurant);
    }

    public Optional<Restaurant> findRestaurantById(Long id) {
        return restaurantRepository.findById(id);
    }

    public Optional<Restaurant> findRestaurantByIdAndOwnerUsername(Long id, String username) {
        return restaurantRepository.findRestaurantByIdAndOwnerUsername(id, username);
    }
}
