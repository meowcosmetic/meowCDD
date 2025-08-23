package com.meowcdd.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            .csrf(csrf -> csrf.disable())  // Disable CSRF
            .authorizeHttpRequests(auth -> auth
                .anyRequest().permitAll()  // Allow all requests without authentication
            )
            .formLogin(form -> form.disable())  // Disable form login
            .httpBasic(basic -> basic.disable())  // Disable HTTP basic auth
            .logout(logout -> logout.disable())  // Disable logout
            .sessionManagement(session -> session.disable());  // Disable session management
        
        return http.build();
    }
}
