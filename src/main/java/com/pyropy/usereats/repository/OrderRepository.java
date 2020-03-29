package com.pyropy.usereats.repository;

import com.pyropy.usereats.model.Order;
import com.pyropy.usereats.model.OrderStatus;
import com.pyropy.usereats.model.User;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface OrderRepository extends PagingAndSortingRepository<Order, Long> {
    Optional<Order> findOrderByUserAndOrderStatus(User user, OrderStatus status);

    Optional<Order> findOrderByIdAndUserUsername(Long id, String username);

    List<Order> findOrderByUserUsername(String username);
}
