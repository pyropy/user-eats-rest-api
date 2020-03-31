package com.pyropy.usereats.model;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table(name = "RESTURAUNT")
public class Restaurant {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID", nullable = false, updatable = false)
    private Long id;

    @Column(nullable = false, length = 100, name = "NAME")
    private String name;

    @Column(nullable = false, name = "ADDRESS")
    private String address;

    @Column(name = "DESCRIPTION")
    private String description;

    @Column(nullable = false)
    private boolean active;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.MERGE, optional = false)
    @JoinColumn(name = "USER_ID")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private User owner;

    public Restaurant() {
    }

    public Restaurant(String name, String description, String address, User owner) {
        this.name = name;
        this.description = description;
        this.address = address;
        this.owner = owner;
        this.active = true; // todo: update changing restaurant status
    }
}
