package com.w2m.heroestest.core.restapi.server.controllers.auth;

import com.w2m.heroestest.core.constants.RequestMappings;
import com.w2m.heroestest.core.restapi.server.controllers.BaseController;
import com.w2m.heroestest.core.restapi.server.requests.auth.LoginRequest;
import com.w2m.heroestest.core.restapi.server.requests.auth.RegisterRequest;
import com.w2m.heroestest.core.restapi.server.responses.auth.AuthResponse;
import com.w2m.heroestest.core.restapi.services.auth.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(RequestMappings.AUTH)
@RequiredArgsConstructor
public class AuthenticationController extends BaseController {

    private static final Logger LOGGER = LogManager.getLogger(AuthenticationController.class);

    //@Autowired
    private final AuthService authService;

    @Operation(summary = "Login a existing user")
    @PostMapping(RequestMappings.LOGIN)
    public ResponseEntity<AuthResponse> login(@RequestBody LoginRequest request) {
        return ResponseEntity.ok(authService.login(request));
    }

    @Operation(summary = "Login a existing user")
    @PostMapping(RequestMappings.REGISTER)
    public ResponseEntity<AuthResponse> register(@RequestBody RegisterRequest request) {
        return ResponseEntity.ok(authService.register(request));
    }

}
