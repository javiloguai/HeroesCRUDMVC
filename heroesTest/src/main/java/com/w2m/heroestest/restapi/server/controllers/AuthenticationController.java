package com.w2m.heroestest.restapi.server.controllers;

import com.w2m.heroestest.constants.RequestMappings;
import com.w2m.heroestest.restapi.server.requests.AuthenticationRequest;
import com.w2m.heroestest.restapi.server.requests.RegisterRequest;
import com.w2m.heroestest.restapi.server.responses.AuthenticationResponse;
import com.w2m.heroestest.restapi.services.AuthenticationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author jruizh
 */
@Slf4j
@RestController
@RequestMapping(RequestMappings.AUTH)
@Tag(name = "Authentication", description = "Authentication management APIs")
public class AuthenticationController {

    private static final Logger LOGGER = LogManager.getLogger(AuthenticationController.class);

    @Autowired
    private AuthenticationService authenticationService;

    @Operation(summary = "Login", description = "This api used to authenticate user to access secured api's")
    @PostMapping(RequestMappings.LOGIN)
    public ResponseEntity<AuthenticationResponse> login(@RequestBody AuthenticationRequest authenticationRequest) {
        LOGGER.debug(String.format("Authenticating user: %s", authenticationRequest.toString()));
        return ResponseEntity.ok(authenticationService.login(authenticationRequest));
    }

    @Operation(summary = "Register", description = "This api used to register user to access secured api's. Available Roles are USER and ADMIN")
    @PostMapping(RequestMappings.REGISTER)
    public ResponseEntity<AuthenticationResponse> register(@RequestBody RegisterRequest registerRequest) {
        LOGGER.debug(String.format("Registering user: %s", registerRequest.toString()));
        return ResponseEntity.ok(authenticationService.register(registerRequest));
    }

}
