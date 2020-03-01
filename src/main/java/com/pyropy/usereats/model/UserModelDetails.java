package com.pyropy.usereats.model;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

public class UserModelDetails implements UserDetails {
    private User user;

    public UserModelDetails(User user) {
        this.user = user;
    }

    // todo: implement UserDetails methods
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return user.getAuthorities();
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getUsername();
    }

    @Override
    public boolean isAccountNonExpired() {
        return user.isActivated();
    }

    @Override
    public boolean isAccountNonLocked() {
        return user.isActivated();
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return false;
    }

    @Override
    public boolean isEnabled() {
        return user.isActivated();
    }
}
