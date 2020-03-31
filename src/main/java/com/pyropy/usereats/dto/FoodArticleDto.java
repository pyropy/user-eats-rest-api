package com.pyropy.usereats.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FoodArticleDto {

    @JsonProperty
    private Long id;

    @JsonProperty
    private String name;

    @JsonProperty
    private Float price;

    @JsonProperty
    private String imageUrl;

    @JsonProperty
    private String description;

    @JsonProperty
    private Long restaurantId;
}
