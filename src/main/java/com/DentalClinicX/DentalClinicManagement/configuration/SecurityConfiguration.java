package com.DentalClinicX.DentalClinicManagement.configuration;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;



@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfiguration {

    private final JwtAuthenticationFilter jwtAuthFilter;
    private final AuthenticationProvider authenticationProvider;

//http://localhost:8080/swagger-ui/index.html#/
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
                    auth.requestMatchers("/v2/api-docs").permitAll();
                    auth.requestMatchers("/swagger-resources").permitAll();
                    auth.requestMatchers("/swagger-resources/**").permitAll();
                    auth.requestMatchers("/configuration/ui").permitAll();
                    auth.requestMatchers("/configuration/security").permitAll();
                    auth.requestMatchers("/swagger-ui.html").permitAll();
                    auth.requestMatchers("/webjars/**").permitAll();
                    auth.requestMatchers("/v3/api-docs/**").permitAll();
                    auth.requestMatchers("/v3/api-docs.yaml").permitAll();
                    auth.requestMatchers("/swagger-ui/**").permitAll();
                    auth.requestMatchers("/api/v1/auth/**").permitAll();
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

    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return (web) -> web.ignoring().requestMatchers("/v3/api-docs/**");
    }

}
