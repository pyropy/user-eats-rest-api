package com.pyropy.usereats.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.pyropy.usereats.model.OrderStatus;

import java.util.List;

public class OrderDto {
    @JsonProperty
    private Long orderId;

    @JsonProperty
    private Float subtotal;

    @JsonProperty
    private Float total;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private OrderStatus status;

    @JsonProperty
    private List<OrderArticleDto> orderArticles;

    @JsonProperty
    private String deliveryAddress;

    public Float getSubtotal() {
        return subtotal;
    }

    public void setSubtotal(Float subtotal) {
        this.subtotal = subtotal;
    }

    public Float getTotal() {
        return total;
    }

    public void setTotal(Float total) {
        this.total = total;
    }

    public OrderStatus getStatus() {
        return status;
    }

    public void setStatus(OrderStatus status) {
        this.status = status;
    }

    public List<OrderArticleDto> getOrderArticles() {
        return orderArticles;
    }

    public void setOrderArticles(List<OrderArticleDto> orderArticles) {
        this.orderArticles = orderArticles;
    }

    public String getDeliveryAddress() {
        return deliveryAddress;
    }

    public void setDeliveryAddress(String deliveryAddress) {
        this.deliveryAddress = deliveryAddress;
    }

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }
}
