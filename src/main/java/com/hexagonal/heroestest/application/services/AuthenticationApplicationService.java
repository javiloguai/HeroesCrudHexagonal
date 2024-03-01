package com.hexagonal.heroestest.application.services;

import com.hexagonal.heroestest.infraestructure.restapi.model.requests.AuthenticationRequest;
import com.hexagonal.heroestest.infraestructure.restapi.model.requests.RegisterRequest;
import com.hexagonal.heroestest.infraestructure.restapi.model.responses.AuthenticationResponse;

/**
 * @author jruizh
 */

public interface AuthenticationApplicationService {

    AuthenticationResponse login(AuthenticationRequest request);

    AuthenticationResponse register(RegisterRequest request);

}
