package com.pyropy.usereats.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrderFoodArticleDto {

    @JsonProperty
    private Long foodArticleId;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private String foodArticleName;

    @JsonProperty
    private Integer quantity;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private Long orderId;
}
