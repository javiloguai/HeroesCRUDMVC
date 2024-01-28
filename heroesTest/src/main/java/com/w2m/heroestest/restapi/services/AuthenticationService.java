package com.w2m.heroestest.restapi.services;

import com.w2m.heroestest.restapi.server.requests.AuthenticationRequest;
import com.w2m.heroestest.restapi.server.requests.RegisterRequest;
import com.w2m.heroestest.restapi.server.responses.AuthenticationResponse;

/**
 * @author jruizh
 */
public interface AuthenticationService {


    AuthenticationResponse login(AuthenticationRequest request);
    AuthenticationResponse register(RegisterRequest request);

    //void refreshToken(HttpServletRequest request, HttpServletResponse response) throws IOException;

}
