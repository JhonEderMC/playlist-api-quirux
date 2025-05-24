package com.playlist.domain.service;

import com.playlist.domain.model.Usuario;
import com.playlist.domain.port.UsuarioRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class UsuarioServiceTest {

    private UsuarioRepository repository;
    private UsuarioService service;

    @BeforeEach
    void setUp() {
        repository = mock(UsuarioRepository.class);
        service = new UsuarioService(repository);
    }

    @Test
    void crearUsuarioCorrectamente() {
        Usuario usuario = Usuario.builder()
                .username("user1")
                .password("pass")
                .role("USER")
                .build();

        when(repository.existsByUsername("user1")).thenReturn(false);
        when(repository.save(usuario)).thenReturn(usuario);

        Usuario result = service.crearUsuario(usuario);

        assertEquals(usuario, result);
        verify(repository).save(usuario);
    }

    @Test
    void crearUsuarioUsuarioYaExisteExcepcion() {
        Usuario usuario = Usuario.builder()
                .username("user1")
                .password("pass")
                .role("USER")
                .build();

        when(repository.existsByUsername("user1")).thenReturn(true);

        Exception ex = assertThrows(IllegalArgumentException.class, () -> service.crearUsuario(usuario));
        assertEquals(UsuarioService.EL_USUARIO_YA_EXISTE, ex.getMessage());
        verify(repository, never()).save(any());
    }

    @Test
    void obtenerPorNombreCorrectamente() {
        Usuario usuario = Usuario.builder()
                .username("user1")
                .password("pass")
                .role("USER")
                .build();

        when(repository.findByUsername("user1")).thenReturn(Optional.of(usuario));

        Optional<Usuario> result = service.obtenerPorNombre("user1");

        assertTrue(result.isPresent());
        assertEquals(usuario, result.get());
    }

    @Test
    void obtenerPorNombreNotFound() {
        when(repository.findByUsername("unknown")).thenReturn(Optional.empty());

        Optional<Usuario> result = service.obtenerPorNombre("unknown");

        assertTrue(result.isEmpty());
    }

}