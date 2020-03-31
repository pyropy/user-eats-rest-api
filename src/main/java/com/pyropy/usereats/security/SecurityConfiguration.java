package com.pyropy.usereats.security;

import com.pyropy.usereats.config.JwtConfig;
import com.pyropy.usereats.jwt.JwtTokenVerifier;
import com.pyropy.usereats.jwt.JwtUsernameAndPasswordAuthFilter;
import com.pyropy.usereats.service.UserSecurityDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.crypto.SecretKey;


@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    private final UserSecurityDetailsService userDetailsService;
    private final PasswordEncoder passwordEncoder;
    private final JwtConfig jwtConfig;
    private final SecretKey secretKey;

    @Autowired
    public SecurityConfiguration(UserSecurityDetailsService userDetailsService,
                                 PasswordEncoder passwordEncoder,
                                 JwtConfig jwtConfig,
                                 SecretKey secretKey) {
        this.userDetailsService = userDetailsService;
        this.passwordEncoder = passwordEncoder;
        this.jwtConfig = jwtConfig;
        this.secretKey = secretKey;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(authenticationProvider());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .addFilter(new JwtUsernameAndPasswordAuthFilter(authenticationManager(), jwtConfig, secretKey))
                .addFilterAfter(new JwtTokenVerifier(secretKey, jwtConfig), JwtUsernameAndPasswordAuthFilter.class)
                .authorizeRequests()
//                .antMatchers(HttpMethod.POST, "/login").permitAll()
//                .antMatchers(HttpMethod.GET, "/api/v1/food").hasAnyRole("USER", "RESTAURANT_ADMIN")
//                .antMatchers(HttpMethod.POST, "/api/v1/food").hasRole("RESTAURANT_ADMIN")
//                .antMatchers(HttpMethod.GET, "/api/v1/restaurants/**").hasAnyRole("USER", "RESTAURANT_ADMIN")
//                .antMatchers(HttpMethod.POST, "/api/v1/restaurants/**").hasRole("RESTAURANT_ADMIN")
//                .antMatchers(HttpMethod.DELETE, "/api/v1/restaurants/**").hasRole("RESTAURANT_ADMIN")
//                .antMatchers(HttpMethod.PUT, "/api/v1/restaurants/**").hasRole("RESTAURANT_ADMIN")
                .anyRequest()
                .permitAll();
//                .anyRequest()
//                .authenticated();
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setPasswordEncoder(passwordEncoder);
        provider.setUserDetailsService(userDetailsService);
        return provider;
    }
}
