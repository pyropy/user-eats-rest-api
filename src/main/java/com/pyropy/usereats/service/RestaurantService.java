package com.pyropy.usereats.service;

import com.pyropy.usereats.model.Restaurant;
import com.pyropy.usereats.repository.RestaurantRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RestaurantService {

    private final RestaurantRepository restaurantRepository;

    @Autowired
    public RestaurantService(RestaurantRepository restaurantRepository) {
        this.restaurantRepository = restaurantRepository;
    }

    public Iterable<Restaurant> findAll() {
        return restaurantRepository.findAll();
    }
}
