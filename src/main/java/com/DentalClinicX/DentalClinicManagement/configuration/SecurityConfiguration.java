package com.DentalClinicX.DentalClinicManagement.configuration;

import io.jsonwebtoken.Jwt;
import jakarta.servlet.Filter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfiguration {

    private final JwtAuthenticationFilter jwtAuthFilter;
    private final AuthenticationProvider authenticationProvider;


    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())
                .cors(cors -> cors.disable())
                .authorizeHttpRequests(auth -> {
                    auth.requestMatchers("/patients/**").hasAuthority("USER");
                    auth.requestMatchers("/admins/**").hasAuthority("ADMIN");
                    auth.requestMatchers("/landing-page/**").permitAll();
                    auth.requestMatchers("/login.html").permitAll();
                    auth.requestMatchers("/register.html").permitAll();
                    auth.requestMatchers("/scripts/**").permitAll();
                    auth.requestMatchers("/css/**").permitAll();
                    auth.requestMatchers("/node_modules/**").permitAll();
                    auth.requestMatchers("/appointment.html").permitAll();
                    auth.requestMatchers("/patient.html").permitAll();
                    auth.requestMatchers("/dentist.html").permitAll();
                    auth.requestMatchers("/profile.html").permitAll();
                    auth.requestMatchers("/assets/**").permitAll();
                    auth.anyRequest().authenticated();
                });
        http
                .sessionManagement(session -> {
                    session.sessionCreationPolicy(SessionCreationPolicy.STATELESS);
                });
        http
                .authenticationProvider(authenticationProvider);
        http
                .addFilterBefore(jwtAuthFilter,UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }

}
