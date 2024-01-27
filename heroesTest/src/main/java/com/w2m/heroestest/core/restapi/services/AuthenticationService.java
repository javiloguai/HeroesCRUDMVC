package com.w2m.heroestest.core.restapi.services;

import com.w2m.heroestest.core.restapi.server.requests.AuthenticationRequest;
import com.w2m.heroestest.core.restapi.server.requests.RegisterRequest;
import com.w2m.heroestest.core.restapi.server.responses.AuthenticationResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

/**
 * @author jruizh
 */
public interface AuthenticationService {

    AuthenticationResponse register(RegisterRequest request);

    AuthenticationResponse authenticate(AuthenticationRequest request);

    void refreshToken(HttpServletRequest request, HttpServletResponse response) throws IOException;
}
