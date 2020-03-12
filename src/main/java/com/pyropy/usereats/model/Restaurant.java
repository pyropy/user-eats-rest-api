package com.pyropy.usereats.model;


import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.*;

@Entity
@Table(name = "RESTURAUNT")
public class Restaurant {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ID")
    @JsonProperty
    private long id;

    @Column(nullable = false, length = 100, name = "NAME")
    @JsonProperty
    private String name;

    @Column(length = 255, name = "DESCRIPTION")
    private String description;

    @Column(nullable = false)
    private boolean active;

    public Restaurant() {
    }

    public Restaurant(String name, String description, boolean active) {
        this.name = name;
        this.description = description;
        this.active = active;
    }
}
