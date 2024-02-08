package com.w2m.heroestest.restapi.services.impl;

import com.w2m.heroestest.restapi.persistence.entities.AuthUser;
import com.w2m.heroestest.restapi.persistence.repositories.UserRepository;
import com.w2m.heroestest.restapi.server.requests.AuthenticationRequest;
import com.w2m.heroestest.restapi.server.requests.RegisterRequest;
import com.w2m.heroestest.restapi.server.responses.AuthenticationResponse;
import com.w2m.heroestest.restapi.services.AuthenticationService;
import com.w2m.heroestest.restapi.services.jwt.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

/**
 * @author jruizh
 */
@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private AuthenticationManager authenticationManager;

    public AuthenticationResponse login(AuthenticationRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));
        UserDetails user = userRepository.findByUsername(request.getUsername()).orElseThrow();
        String token = jwtService.getToken(user);
        return AuthenticationResponse.builder().token(token).build();

    }

    public AuthenticationResponse register(RegisterRequest request) {
        AuthUser user = AuthUser.builder().username(request.getUsername())
                .password(passwordEncoder.encode(request.getPassword())).role(request.getRole()).build();

        userRepository.save(user);

        return AuthenticationResponse.builder().token(jwtService.getToken(user)).build();

    }

    /**public AuthenticationResponse register(RegisterRequest request) {
     var user = UserWithProfiles.builder().firstname(request.getFirstName()).lastname(request.getLastName())
     .email(request.getEmail()).password(passwordEncoder.encode(request.getPassword()))
     .role(request.getRole()).build();
     var savedUser = userRepository.save(user);
     var jwtToken = jwtService.generateToken(user);
     var refreshToken = jwtService.generateRefreshToken(user);
     //saveUserToken(savedUser, jwtToken);
     return AuthenticationResponse.builder().token(jwtToken).refreshToken(refreshToken).build();
     }

     public AuthenticationResponse login(AuthenticationRequest request) {
     authenticationManager.authenticate(
     new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));
     var user = userRepository.findByEmail(request.getEmail()).orElseThrow();
     var jwtToken = jwtService.generateToken(user);
     var refreshToken = jwtService.generateRefreshToken(user);
     revokeAllUserTokens(user);
     saveUserToken(user, jwtToken);
     return AuthenticationResponse.builder().token(jwtToken).refreshToken(refreshToken).build();
     }

     private void saveUserToken(UserWithProfiles user, String jwtToken) {
     var token = Token.builder().user(user).token(jwtToken).tokenType(TokenType.BEARER).expired(false).revoked(false)
     .build();
     tokenRepository.save(token);
     }

     private void revokeAllUserTokens(UserWithProfiles user) {
     var validUserTokens = tokenRepository.findAllValidTokenByUser(user.getId());
     if (validUserTokens.isEmpty())
     return;
     validUserTokens.forEach(token -> {
     token.setExpired(true);
     token.setRevoked(true);
     });
     tokenRepository.saveAll(validUserTokens);
     }

     public void refreshToken(HttpServletRequest request, HttpServletResponse response) throws IOException {
     final String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
     final String refreshToken;
     final String userEmail;
     if (authHeader == null || !authHeader.startsWith(SecurityConstants.BEARER)) {
     return;
     }
     refreshToken = authHeader.substring(7);

     userEmail = jwtService.extractUsername(refreshToken);
     if (userEmail != null) {
     var user = this.userRepository.findByEmail(userEmail).orElseThrow();
     if (jwtService.isTokenValid(refreshToken, user)) {
     var accessToken = jwtService.generateToken(user);
     revokeAllUserTokens(user);
     saveUserToken(user, accessToken);
     var authResponse = AuthenticationResponse.builder().accessToken(accessToken).refreshToken(refreshToken)
     .build();
     new ObjectMapper().writeValue(response.getOutputStream(), authResponse);
     }
     }
     } **/
}
