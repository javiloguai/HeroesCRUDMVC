package com.w2m.heroestest.core.restapi.services.auth;

import com.w2m.heroestest.core.model.enums.Role;
import com.w2m.heroestest.core.restapi.persistence.entities.User;
import com.w2m.heroestest.core.restapi.repositories.UserRepository;
import com.w2m.heroestest.core.restapi.server.requests.auth.LoginRequest;
import com.w2m.heroestest.core.restapi.server.requests.auth.RegisterRequest;
import com.w2m.heroestest.core.restapi.server.responses.auth.AuthResponse;
import com.w2m.heroestest.core.restapi.services.jwt.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthService {

    @Autowired
    private final UserRepository userRepository;

    private final JwtService jwtService;

    private final PasswordEncoder passwordEncoder;

    private final AuthenticationManager authenticationManager;

    public AuthResponse login(LoginRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));
        UserDetails user = userRepository.findByUsername(request.getUsername()).orElseThrow();
        String token = jwtService.getToken(user);
        return AuthResponse.builder().token(token).build();

    }

    public AuthResponse register(RegisterRequest request) {
        User user = User.builder().username(request.getUsername())
                .password(passwordEncoder.encode(request.getPassword())).firstname(request.getFirstname())
                .lastname(request.getLastname()).country(request.getCountry()).role(Role.USER).build();

        userRepository.save(user);

        return AuthResponse.builder().token(jwtService.getToken(user)).build();

    }

}