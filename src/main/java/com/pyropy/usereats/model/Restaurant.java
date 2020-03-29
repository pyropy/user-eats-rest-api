package com.pyropy.usereats.model;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;

@Entity
@Table(name = "RESTURAUNT")
public class Restaurant {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ID")
    private long id;

    @Column(nullable = false, length = 100, name = "NAME")
    private String name;

    @Column(nullable = false, name = "ADDRESS")
    private String address;

    @Column(name = "DESCRIPTION")
    private String description;

    @Column(nullable = false)
    private boolean active;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "USER_ID", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private User owner;

    public Restaurant() {
    }

    public Restaurant(String name, String description, String address, User owner) {
        this.name = name;
        this.description = description;
        this.owner = owner;
        this.address = address;
        this.active = true; // todo: update changing restaurant status
    }

    public User getOwner() {
        return owner;
    }

    public void setOwner(User owner) {
        this.owner = owner;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public long getId() {
        return id;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
