package com.playlist.domain.port;

import com.playlist.domain.model.Usuario;

import java.util.Optional;

public interface UsuarioRepository {

    Usuario save(Usuario usuario);
    Optional<Usuario> findByUsername(String username);
    boolean existsByUsername(String username);

}
