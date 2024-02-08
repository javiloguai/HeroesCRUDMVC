package com.w2m.heroestest.config.auth;

import com.w2m.heroestest.constants.RequestMappings;
import com.w2m.heroestest.restapi.services.jwt.JwtAuthenticationFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import static org.springframework.boot.autoconfigure.security.servlet.PathRequest.toH2Console;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtAuthenticationFilter jwtAuthenticationFilter;

    private final AuthenticationProvider authProvider;

    @Bean
    @Order(1)
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf(csrf -> csrf.ignoringRequestMatchers("/h2-ui/**").disable())
                .headers(header -> header.frameOptions((fo) -> fo.sameOrigin())).authorizeHttpRequests(
                        authRequest -> authRequest.requestMatchers(RequestMappings.AUTH + "/**").permitAll()
                                //.requestMatchers(RequestMappings.AUTH + "/login").permitAll()
                                .requestMatchers(toH2Console()).permitAll()
                                .requestMatchers("/actuator/**", "/api-docs/**", "/v2/api-docs/**", "/v3/api-docs/**")
                                .permitAll().requestMatchers(RequestMappings.AUTH + "/**").permitAll()
                                .requestMatchers("/h2-ui/**").permitAll().requestMatchers("/doc/**", "/index.html").permitAll()
                                .requestMatchers("/api-documentation", "/swagger-api-docs", "/swagger-api-docs/**",
                                        "/swagger-ui/index.html", "/swagger-resources", "/swagger-resources/**",
                                        "/configuration/ui", "/configuration/security", "/swagger-ui**", "/swagger-ui/**",
                                        "/webjars/**", "/swagger-resources/**", "/swagger-ui.html").permitAll()
                                .requestMatchers("/").permitAll()

//                        .requestMatchers(GET,RequestMappings.API+RequestMappings.SUPERHEROES).hasAnyRole(ADMIN.name(), USER.name())
//                        .requestMatchers(GET,RequestMappings.API+RequestMappings.SUPERHEROES+"/**").hasAnyRole(ADMIN.name(), USER.name())
//                        .requestMatchers(POST,RequestMappings.API+RequestMappings.SUPERHEROES).hasAnyRole(ADMIN.name())
//                        .requestMatchers(POST,RequestMappings.API+RequestMappings.SUPERHEROES+"/**").hasAnyRole(ADMIN.name())
//                        .requestMatchers(PUT, RequestMappings.API+RequestMappings.SUPERHEROES).hasAnyRole(ADMIN.name())
//                        .requestMatchers(PUT, RequestMappings.API+RequestMappings.SUPERHEROES+"/**").hasAnyRole(ADMIN.name())
//                        .requestMatchers(PATCH, RequestMappings.API+RequestMappings.SUPERHEROES).hasAnyRole(ADMIN.name(), USER.name())
//                        .requestMatchers(PATCH, RequestMappings.API+RequestMappings.SUPERHEROES+"/**").hasAnyRole(ADMIN.name(), USER.name())
//                        .requestMatchers(DELETE, RequestMappings.API+RequestMappings.SUPERHEROES).hasAnyRole(ADMIN.name())
//                        .requestMatchers(DELETE, RequestMappings.API+RequestMappings.SUPERHEROES+"/**").hasAnyRole(ADMIN.name())

                                .anyRequest().authenticated()).sessionManagement(
                        sessionManager -> sessionManager.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authenticationProvider(authProvider)
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();

    }

}
