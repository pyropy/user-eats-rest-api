package com.pyropy.usereats.repository;

import com.pyropy.usereats.model.FoodArticle;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface FoodArticleRepository extends PagingAndSortingRepository<FoodArticle, Long> {
    List<FoodArticle> findFoodArticleByRestaurantId(Long Id);

    Optional<FoodArticle> findFoodArticleByIdAndRestaurantOwnerUsername(Long id, String username);
}
