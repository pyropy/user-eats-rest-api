package com.pyropy.usereats.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.hibernate.annotations.BatchSize;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import javax.persistence.*;
import java.util.*;

@Entity
@Table(name = "USER")
public class User {
    @Id
    @Column(name = "ID")
    private String id;


    @Column(nullable = false, unique = true, length = 100, name = "EMAIL")
    private String email;

    @Column(nullable = false, unique = true, length = 50, name = "USERNAME")
    private String username;

    @Column(length = 100, name = "PASSWORD")
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;


    @Column(length = 50, nullable = false, name = "FIRST_NAME")
    @JsonProperty
    private String firstName;

    @Column(length = 50, nullable = false, name = "LAST_NAME")
    @JsonProperty
    private String lastName;

    @JsonProperty
    @Column(nullable = false, name = "ADDRESS")
    private String address;

    @JsonIgnore
    @Column(nullable = false, name = "ACTIVATED")
    private boolean activated;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "USER_ROLE",
            joinColumns = {@JoinColumn(name = "USER_ID", referencedColumnName = "ID")},
            inverseJoinColumns = {@JoinColumn(name = "ROLE_NAME", referencedColumnName = "NAME")})
    @BatchSize(size = 20)
    @JsonIgnore
    private List<Role> roles = new ArrayList<>();


    @OneToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "USER_RESTURAUNT",
            joinColumns = {@JoinColumn(name = "USER_ID", referencedColumnName = "ID")},
            inverseJoinColumns = {@JoinColumn(name = "RESTURAUNT_ID", referencedColumnName = "ID")})
    @BatchSize(size = 20)
    @JsonIgnore
    private List<Restaurant> restaurants = new ArrayList<>();

    public User() {
    }

    public User(String firstName,
                String lastName,
                String address,
                String password,
                String email,
                String username,
                List<Role> roles) {
        this.id = UUID.randomUUID().toString();
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
        this.password = password;
        this.email = email;
        this.username = username;
        this.roles = roles;
        this.activated = true; // todo: implement email confirmation
    }


    public String getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public boolean isActivated() {
        return activated;
    }

    public void setActivated(boolean activated) {
        this.activated = activated;
    }

    public List<Role> getRoles() {
        return roles;
    }

    public void setRoles(List<Role> roles) {
        this.roles = roles;
    }

    public List<Restaurant> getRestaurants() {
        return restaurants;
    }

    public void setRestaurants(List<Restaurant> restaurants) {
        this.restaurants = restaurants;
    }

    public Collection<GrantedAuthority> getAuthorities() {
        List<GrantedAuthority> authorities = new ArrayList<>();
        roles.forEach(r -> {
            authorities.add(new SimpleGrantedAuthority(r.getName()));
        });
        return authorities;
    }

}
