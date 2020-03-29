package com.pyropy.usereats.service;

import com.pyropy.usereats.dto.RestaurantDto;
import com.pyropy.usereats.model.Restaurant;
import com.pyropy.usereats.model.User;
import com.pyropy.usereats.repository.RestaurantRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class RestaurantService {

    @Autowired
    private RestaurantRepository restaurantRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private UserService userService;

    /*
     * Converts Restaurant Entity to RestaurantDto class.
     * */
    public RestaurantDto convertToDto(Restaurant restaurant) {
        return modelMapper.map(restaurant, RestaurantDto.class);
    }

    /*
     * Converts RestaurantDto entity to Restaurant class.
     * */
    public Restaurant convertToEntity(RestaurantDto restaurantDto) {
        return modelMapper.map(restaurantDto, Restaurant.class);
    }


    public List<RestaurantDto> convertRestaurantIterableToDtoList(Iterable<Restaurant> restaurants) {
        return StreamSupport
                .stream(restaurants.spliterator(), false)
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    public List<RestaurantDto> findAll() {
        List<Restaurant> restaurants = new ArrayList<>();
        return convertRestaurantIterableToDtoList(restaurantRepository.findAll());
    }

    /*
     * Find restaurant by given name.
     */
    public List<RestaurantDto> findByNameLike(String name) {
        Iterable<Restaurant> restaurants = restaurantRepository.findByNameLike(name);
        return convertRestaurantIterableToDtoList(restaurants);
    }

    /*
     * Find restaurants by given owner username.
     */
    public List<RestaurantDto> findByOwnerUsername(String username) {
        Iterable<Restaurant> restaurants = restaurantRepository.findRestaurantByOwnerUsername(username);
        return convertRestaurantIterableToDtoList(restaurants);
    }

    public RestaurantDto createRestaurant(RestaurantDto restaurantInfo, String username) {
        Restaurant restaurant = convertToEntity(restaurantInfo);
        User user = userService.convertToEntity(userService.findByUsername(username));
        restaurant.setOwner(user);
        restaurantRepository.save(restaurant);
        return convertToDto(restaurant);
    }

    public RestaurantDto updateRestaurant(RestaurantDto restaurantDto, String username) {
        restaurantRepository.findRestaurantByNameAndOwnerUsername(restaurantDto.getName(), username)
                .map(restaurant -> {
                    restaurant.setName(restaurant.getName());
                    restaurant.setDescription(restaurant.getDescription());
                    restaurantRepository.save(restaurant);
                    return convertToDto(restaurant);
                })
                .orElseThrow(() -> new EntityNotFoundException("Restaurant not found."));
        return null;
    }

    public void deleteRestaurant(RestaurantDto restaurantDto, String username) {
        restaurantRepository
                .findRestaurantByNameAndOwnerUsername(restaurantDto.getName(), username)
                .map(restaurant -> {
                    restaurantRepository.delete(restaurant);
                    return null;
                });
    }

}
