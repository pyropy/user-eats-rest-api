package com.pyropy.usereats.repository;

import com.pyropy.usereats.model.Restaurant;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RestaurantRepository extends CrudRepository<Restaurant, Long> {
    Iterable<Restaurant> findByNameLike(String name);
    Iterable<Restaurant> findRestaurantByOwnerId(String id);
    Iterable<Restaurant> findRestaurantByOwnerUsername(String username);
}
