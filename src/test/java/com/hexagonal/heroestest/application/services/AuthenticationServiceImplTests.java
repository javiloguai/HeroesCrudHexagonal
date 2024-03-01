package com.hexagonal.heroestest.application.services;

import com.hexagonal.heroestest.application.ports.out.AuthUserRepositoryPort;
import com.hexagonal.heroestest.application.services.impl.AuthenticationApplicationServiceImpl;
import com.hexagonal.heroestest.application.services.jwt.JwtService;
import com.hexagonal.heroestest.domain.enums.Role;
import com.hexagonal.heroestest.infraestructure.db.entities.AuthUserEntity;
import com.hexagonal.heroestest.infraestructure.restapi.model.requests.AuthenticationRequest;
import com.hexagonal.heroestest.infraestructure.restapi.model.requests.RegisterRequest;
import com.hexagonal.heroestest.infraestructure.restapi.model.responses.AuthenticationResponse;
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

import static org.mockito.Mockito.verify;

/**
 * @author jruizh
 */
@ExtendWith({ SpringExtension.class, MockitoExtension.class })
//@WithMockUser(username = "admin", roles = { "USER", "ADMIN" })
class AuthenticationServiceImplTests {

    @Mock
    private AuthUserRepositoryPort userRepositoryPort;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private JwtService jwtService;

    @Mock
    private AuthenticationManager authenticationManager;

    @InjectMocks
    private AuthenticationApplicationServiceImpl authenticationApplicationService;

    @Test
    @Disabled
    void testRegister() {
        RegisterRequest request = new RegisterRequest("foouser", "password", Role.USER);
        AuthUserEntity savedUser = AuthUserEntity.builder().id(1).username("foouser").password("password")
                .role(Role.USER).build();

        Mockito.when(userRepositoryPort.save(Mockito.any(AuthUserEntity.class))).thenReturn(savedUser);

        AuthenticationResponse response = authenticationApplicationService.register(request);

        verify(userRepositoryPort).save(Mockito.any(AuthUserEntity.class));
    }

    @Test
    @Disabled
    void testAuthenticate() {
        AuthenticationRequest request = new AuthenticationRequest("foouser", "password");
        AuthUserEntity user = AuthUserEntity.builder().id(1).username("foouser").password("password").role(Role.USER)
                .build();

        Mockito.when(userRepositoryPort.findByUsername("foouser")).thenReturn(Optional.of(user));

        AuthenticationResponse response = authenticationApplicationService.login(request);

        verify(userRepositoryPort).findByUsername("foouser");

    }

    @Test
    @Disabled
    void testRefreshToken() throws IOException {
        HttpServletRequest request = Mockito.mock(HttpServletRequest.class);
        HttpServletResponse response = Mockito.mock(HttpServletResponse.class);
        AuthUserEntity user = AuthUserEntity.builder().id(1).username("foouser").password("password").role(Role.USER)
                .build();

        Mockito.when(request.getHeader(HttpHeaders.AUTHORIZATION)).thenReturn("Bearer refresh_token");
        Mockito.when(userRepositoryPort.findByUsername("foouser")).thenReturn(Optional.of(user));

        ServletOutputStream outputStream = Mockito.mock(ServletOutputStream.class);
        Mockito.when(response.getOutputStream()).thenReturn(outputStream);

        verify(userRepositoryPort).findByUsername("foouser");
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