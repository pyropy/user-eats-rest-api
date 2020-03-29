package com.pyropy.usereats.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class RestaurantDto {
    @JsonProperty
    private String name;

    @JsonProperty
    private String address;

    @JsonProperty
    private String description;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
