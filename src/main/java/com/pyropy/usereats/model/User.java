package com.pyropy.usereats.model;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.BatchSize;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import javax.persistence.*;
import java.util.*;

@Getter
@Setter
@Entity
@Table(name = "USER")
public class User {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID", nullable = false, updatable = false)
    private Long id;

    @Column(nullable = false, unique = true, length = 100, name = "EMAIL")
    private String email;

    @Column(nullable = false, unique = true, length = 50, name = "USERNAME")
    private String username;

    @Column(length = 100, name = "PASSWORD")
    private String password;

    @Column(length = 50, nullable = false, name = "FIRST_NAME")
    private String firstName;

    @Column(length = 50, nullable = false, name = "LAST_NAME")
    private String lastName;

    @Column(nullable = false, name = "ADDRESS")
    private String address;

    @Column(nullable = false, name = "ACTIVATED")
    private boolean activated = true; // todo: implement email confirmation

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "USER_ROLE",
            joinColumns = {@JoinColumn(name = "USER_ID", referencedColumnName = "ID")},
            inverseJoinColumns = {@JoinColumn(name = "ROLE_NAME", referencedColumnName = "NAME")})
    @BatchSize(size = 20)
    private List<Role> roles = new ArrayList<>();

    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "ORDERS_ID")
    private List<Order> orders;

    public User() {
    }

    public User(String firstName,
                String lastName,
                String address,
                String password,
                String email,
                String username) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
        this.password = password;
        this.email = email;
        this.username = username;
    }

    public Collection<GrantedAuthority> getAuthorities() {
        List<GrantedAuthority> authorities = new ArrayList<>();
        roles.forEach(r -> {
            authorities.add(new SimpleGrantedAuthority(r.getName()));
        });
        return authorities;
    }

}
