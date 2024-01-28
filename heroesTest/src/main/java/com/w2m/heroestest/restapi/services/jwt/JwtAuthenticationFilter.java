package com.w2m.heroestest.restapi.services.jwt;

import java.io.IOException;

import com.w2m.heroestest.constants.SecurityConstants;
import com.w2m.heroestest.restapi.persistence.repositories.UserRepository;
import com.w2m.heroestest.utils.ParamUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private static final Logger LOGGER = LoggerFactory.getLogger(JwtAuthenticationFilter.class);

    private final JwtService jwtService;

    private final UserDetailsService userDetailsService;

    private final UserRepository userRepository;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        final String token = getTokenFromRequest(request);
        final String username;

        if (token == null) {
            filterChain.doFilter(request, response);
            return;
        }

        username = jwtService.getUsernameFromToken(token);

        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            UserDetails userDetails = userDetailsService.loadUserByUsername(username);

            if (jwtService.isTokenValid(token, userDetails)) {
                UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(userDetails,
                        null, userDetails.getAuthorities());

                authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                SecurityContextHolder.getContext().setAuthentication(authToken);
            }

        }

        filterChain.doFilter(request, response);
    }

    private String getTokenFromRequest(HttpServletRequest request) {
//        final String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
//
//        if (StringUtils.hasText(authHeader) && authHeader.startsWith("Bearer ")) {
//            return authHeader.substring(7);
//        }
//        return null;
        String token;
        final String bearerToken = request.getHeader(HttpHeaders.AUTHORIZATION);
        if (ParamUtils.paramInformed(bearerToken) && bearerToken.startsWith(SecurityConstants.BEARER)) {
            token = bearerToken.replace(SecurityConstants.BEARER, org.apache.commons.lang3.StringUtils.EMPTY);
        } else {
            token = bearerToken;
            LOGGER.warn("JWT Token does not begin with Bearer String");
        }
        return token;
    }

//    @Bean
//    public UserDetailsService userDetailsService() {
//        return username -> userRepository.findByUsername(username)
//                .orElseThrow(() -> new UsernameNotFoundException("User not fournd"));
//    }

}