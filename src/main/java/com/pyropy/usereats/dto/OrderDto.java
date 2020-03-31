package com.pyropy.usereats.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.pyropy.usereats.model.OrderStatus;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class OrderDto {
    @JsonProperty
    private Long id;

    @JsonProperty
    private Float subtotal;

    @JsonProperty
    private Float total;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private OrderStatus status;

    @JsonProperty
    private List<OrderFoodArticleDto> foodArticles;

    @JsonProperty
    private String deliveryAddress;
}
