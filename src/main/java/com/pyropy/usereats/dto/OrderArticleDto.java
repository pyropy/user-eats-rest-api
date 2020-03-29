package com.pyropy.usereats.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class OrderArticleDto {

    @JsonProperty
    private Long foodArticleId;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private String foodArticleName;

    @JsonProperty
    private Integer quantity;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private Long orderId;


    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public String getFoodArticleName() {
        return foodArticleName;
    }

    public void setFoodArticleName(String foodArticleName) {
        this.foodArticleName = foodArticleName;
    }

    public Long getFoodArticleId() {
        return foodArticleId;
    }

    public void setFoodArticleId(Long foodArticleId) {
        this.foodArticleId = foodArticleId;
    }

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }
}
