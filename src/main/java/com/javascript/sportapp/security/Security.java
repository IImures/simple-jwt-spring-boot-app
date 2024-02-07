package com.javascript.sportapp.security;

import com.javascript.sportapp.user.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfiguration;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@AllArgsConstructor
public class Security{

    private final UserService userService;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{
        return http
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(
                        auth ->
                                auth
//                                        .requestMatchers("/login", "/token").permitAll()
//                                        .requestMatchers("/home").hasRole("USER")
//                                        .requestMatchers("/register").permitAll()
                                        .anyRequest().permitAll()
//                                        .requestMatchers("/account").hasRole("USER")
//                                        .requestMatchers("/admin-panel").hasRole("ADMIN")
//                                        .requestMatchers("/home").hasRole("USER")
//                                        .requestMatchers("/hello").permitAll()
//                                        .requestMatchers("/errors").permitAll()
//                                        .requestMatchers("/").permitAll()
                )
                .userDetailsService(userService)
                .build();
    }

}
