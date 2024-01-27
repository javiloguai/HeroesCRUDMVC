package com.w2m.heroestest.core.config.auth;

import com.w2m.heroestest.core.constants.RequestMappings;
import com.w2m.heroestest.core.restapi.repositories.UserRepository;
import com.w2m.heroestest.core.restapi.services.jwt.JwtAuthenticationFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import lombok.RequiredArgsConstructor;

@Configuration
@EnableWebSecurity
//@EnableMethodSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtAuthenticationFilter jwtAuthenticationFilter;

    private final AuthenticationProvider authProvider;

    @Bean
    @Order(1)
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http.csrf(csrf -> csrf.ignoringRequestMatchers("/h2-ui/**").disable()).authorizeHttpRequests(
                        authRequest -> authRequest.requestMatchers(RequestMappings.AUTH + "/**").permitAll()
                                .requestMatchers("/actuator/**", "/api-docs/**", "/v2/api-docs/**", "/v3/api-docs/**")
                                .permitAll().requestMatchers("/h2-ui/**").permitAll().requestMatchers("/doc/**", "/index.html")
                                .permitAll()
                                .requestMatchers("/swagger-ui**", "/swagger-ui/**", "/webjars/**", "/swagger-resources/**")
                                .permitAll().requestMatchers("/").permitAll().anyRequest().authenticated()).sessionManagement(
                        sessionManager -> sessionManager.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authenticationProvider(authProvider)
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class).build();

    }

}
