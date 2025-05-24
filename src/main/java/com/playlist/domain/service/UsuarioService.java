package com.playlist.domain.service;

import com.playlist.domain.model.Usuario;
import com.playlist.domain.port.UsuarioRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UsuarioService {

    public static final String EL_USUARIO_YA_EXISTE = "El usuario ya existe";
    private final UsuarioRepository repository;

    public UsuarioService(UsuarioRepository repository) {
        this.repository = repository;
    }

    public Usuario crearUsuario(Usuario usuario) {
        if (repository.existsByUsername(usuario.getUsername())) {
            throw new IllegalArgumentException(EL_USUARIO_YA_EXISTE);
        }
        return repository.save(usuario);
    }

    public Optional<Usuario> obtenerPorNombre(String username) {
        return repository.findByUsername(username);
    }
}
