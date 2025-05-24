package com.playlist.infrastructure.entrypoints.web.controller;

import com.playlist.domain.model.Usuario;
import com.playlist.domain.service.UsuarioService;
import com.playlist.infrastructure.entrypoints.web.dto.LoginRequest;
import com.playlist.infrastructure.entrypoints.web.dto.LoginResponse;
import com.playlist.infrastructure.entrypoints.web.dto.RegisterRequest;
import com.playlist.infrastructure.entrypoints.web.scurity.JwtUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class AuthControllerTest {

    private AuthenticationManager authenticationManager;
    private UsuarioService usuarioService;
    private PasswordEncoder passwordEncoder;
    private JwtUtil jwtUtil;
    private AuthController authController;

    @BeforeEach
    void setUp() {
        authenticationManager = mock(AuthenticationManager.class);
        usuarioService = mock(UsuarioService.class);
        passwordEncoder = mock(PasswordEncoder.class);
        jwtUtil = mock(JwtUtil.class);
        authController = new AuthController(authenticationManager, usuarioService, passwordEncoder, jwtUtil);
    }

    @Test
    void login_success() {
        LoginRequest request = LoginRequest.builder().username("user").password("pass").build();

        Authentication authentication = mock(Authentication.class);
        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class)))
                .thenReturn(authentication);
        when(authentication.getName()).thenReturn("user");
        when(jwtUtil.generateToken("user")).thenReturn("jwt-token");

        ResponseEntity<LoginResponse> response = authController.login(request);

        assertEquals(200, response.getStatusCode().value());
        assertNotNull(response.getBody());
        assertEquals("jwt-token", response.getBody().getToken());
    }

    @Test
    void login_failure() {
        LoginRequest request = LoginRequest.builder().build();
        request.setUsername("user");
        request.setPassword("wrong");

        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class)))
                .thenThrow(new AuthenticationException("Bad credentials") {});

        ResponseEntity<LoginResponse> response = authController.login(request);

        assertEquals(400, response.getStatusCode().value());
        assertNull(response.getBody());
    }

    @Test
    void register_success() {
        RegisterRequest request = RegisterRequest.builder().build();
        request.setUsername("newuser");
        request.setPassword("newpass");

        when(passwordEncoder.encode("newpass")).thenReturn("encodedpass");
        when(usuarioService.crearUsuario(any(Usuario.class))).thenReturn(Usuario.builder().build());

        ResponseEntity<String> response = authController.register(request);

        assertEquals(200, response.getStatusCode().value());
        assertEquals("Usuario registrado exitosamente", response.getBody());
    }

    @Test
    void register_failure() {
        RegisterRequest request = RegisterRequest.builder().build();
        request.setUsername("existing");
        request.setPassword("pass");

        when(passwordEncoder.encode("pass")).thenReturn("encodedpass");
        doThrow(new IllegalArgumentException("Usuario ya existe")).when(usuarioService)
                .crearUsuario(any(Usuario.class));

        ResponseEntity<String> response = authController.register(request);

        assertEquals(400, response.getStatusCode().value());
        assertEquals("Usuario ya existe", response.getBody());
    }

}