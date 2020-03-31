package com.pyropy.usereats.repository;

import com.pyropy.usereats.model.Restaurant;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RestaurantRepository extends CrudRepository<Restaurant, Long> {
    Iterable<Restaurant> findByNameLike(String name);

    Iterable<Restaurant> findRestaurantByOwnerUsername(String username);

    Optional<Restaurant> findRestaurantByIdAndOwnerUsername(Long id, String username);
}
