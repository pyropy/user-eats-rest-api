package com.pyropy.usereats.repository;

import com.pyropy.usereats.model.FoodArticleOrder;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface FoodArticleOrderRepository extends CrudRepository<FoodArticleOrder, Long> {
    Optional<FoodArticleOrder> findFoodArticleOrderByFoodArticleIdAndOrderId(Long foodArticleId, Long orderId);
}
