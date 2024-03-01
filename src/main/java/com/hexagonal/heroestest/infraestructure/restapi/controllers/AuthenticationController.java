package com.hexagonal.heroestest.infraestructure.restapi.controllers;

import com.hexagonal.heroestest.application.services.AuthenticationApplicationService;
import com.hexagonal.heroestest.infraestructure.constants.RequestMappings;
import com.hexagonal.heroestest.infraestructure.restapi.model.requests.AuthenticationRequest;
import com.hexagonal.heroestest.infraestructure.restapi.model.requests.RegisterRequest;
import com.hexagonal.heroestest.infraestructure.restapi.model.responses.AuthenticationResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
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

    private final AuthenticationApplicationService authenticationApplicationService;

    public AuthenticationController(AuthenticationApplicationService authenticationApplicationService) {
        this.authenticationApplicationService = authenticationApplicationService;
    }

    @Operation(summary = "Login", description = "This api used to authenticate user to access secured api's")
    @PostMapping(RequestMappings.LOGIN)
    public ResponseEntity<AuthenticationResponse> login(@RequestBody AuthenticationRequest authenticationRequest) {
        LOGGER.debug(String.format("Authenticating user: %s", authenticationRequest.toString()));
        return ResponseEntity.ok(authenticationApplicationService.login(authenticationRequest));
    }

    @Operation(summary = "Register", description = "This api used to register user to access secured api's. Available Roles are USER and ADMIN")
    @PostMapping(RequestMappings.REGISTER)
    public ResponseEntity<AuthenticationResponse> register(@RequestBody RegisterRequest registerRequest) {
        LOGGER.debug(String.format("Registering user: %s", registerRequest.toString()));
        return ResponseEntity.ok(authenticationApplicationService.register(registerRequest));
    }

}
