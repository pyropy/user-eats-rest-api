package com.pyropy.usereats.repository;

import com.pyropy.usereats.model.FoodArticle;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FoodArticleRepository extends PagingAndSortingRepository<FoodArticle, Long> {
    List<FoodArticle> findFoodArticleByRestaurantId(Long id);
}
