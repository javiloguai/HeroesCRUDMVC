package com.w2m.heroestest.restapi.services.impl;

import com.w2m.heroestest.model.enums.Role;
import com.w2m.heroestest.restapi.persistence.entities.AuthUser;
import com.w2m.heroestest.restapi.persistence.repositories.UserRepository;
import com.w2m.heroestest.restapi.server.requests.AuthenticationRequest;
import com.w2m.heroestest.restapi.server.requests.RegisterRequest;
import com.w2m.heroestest.restapi.server.responses.AuthenticationResponse;
import com.w2m.heroestest.restapi.services.jwt.JwtService;
import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.io.IOException;
import java.util.Optional;

import static com.w2m.heroestest.model.enums.Role.USER;
import static org.mockito.Mockito.verify;

/**
 * @author jruizh
 */
@ExtendWith({ SpringExtension.class, MockitoExtension.class })
//@WithMockUser(username = "admin", roles = { "USER", "ADMIN" })
class AuthenticationServiceImplTests {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private JwtService jwtService;

    @Mock
    private AuthenticationManager authenticationManager;

    @InjectMocks
    private AuthenticationServiceImpl authenticationService;

    @Test
    @Disabled
    void testRegister() {
        RegisterRequest request = new RegisterRequest("foouser", "password", Role.USER);
        AuthUser savedUser = AuthUser.builder().id(1).username("foouser").password("password").role(USER).build();

        Mockito.when(userRepository.save(Mockito.any(AuthUser.class))).thenReturn(savedUser);

        AuthenticationResponse response = authenticationService.register(request);

        verify(userRepository).save(Mockito.any(AuthUser.class));
    }

    @Test
    @Disabled
    void testAuthenticate() {
        AuthenticationRequest request = new AuthenticationRequest("foouser", "password");
        AuthUser user = AuthUser.builder().id(1).username("foouser").password("password").role(USER).build();

        Mockito.when(userRepository.findByUsername("foouser")).thenReturn(Optional.of(user));

        AuthenticationResponse response = authenticationService.login(request);

        verify(userRepository).findByUsername("foouser");

    }

    @Test
    @Disabled
    void testRefreshToken() throws IOException {
        HttpServletRequest request = Mockito.mock(HttpServletRequest.class);
        HttpServletResponse response = Mockito.mock(HttpServletResponse.class);
        AuthUser user = AuthUser.builder().id(1).username("foouser").password("password").role(USER).build();

        Mockito.when(request.getHeader(HttpHeaders.AUTHORIZATION)).thenReturn("Bearer refresh_token");
        Mockito.when(userRepository.findByUsername("foouser")).thenReturn(Optional.of(user));

        ServletOutputStream outputStream = Mockito.mock(ServletOutputStream.class);
        Mockito.when(response.getOutputStream()).thenReturn(outputStream);

        verify(userRepository).findByUsername("foouser");
        verify(response).getOutputStream();
    }

    @Test
    @Disabled
    void testRefreshTokenNull() throws IOException {
        HttpServletRequest request = Mockito.mock(HttpServletRequest.class);
        HttpServletResponse response = Mockito.mock(HttpServletResponse.class);
        Mockito.when(request.getHeader(HttpHeaders.AUTHORIZATION)).thenReturn(null);
        verify(request).getHeader(HttpHeaders.AUTHORIZATION);
    }
}