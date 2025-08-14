package com.example.saml_client_demo.config;

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
            .authorizeHttpRequests(authz -> authz
                .requestMatchers("/", "/home", "/error", "/login/saml2/**", "/saml2/**", "/debug").permitAll()
                .anyRequest().authenticated()
            )
            .saml2Login(saml2 -> saml2
                .defaultSuccessUrl("/dashboard", true)
            )
            .csrf(csrf -> csrf
                .ignoringRequestMatchers("/login/saml2/**", "/saml2/**")
            )
            .logout(logout -> logout
                .logoutUrl("/logout")
                .logoutSuccessUrl("/")
                .invalidateHttpSession(true)
                .deleteCookies("JSESSIONID")
            );

        return http.build();
    }
}
