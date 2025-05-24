package com.playlist.infrastructure.persistence.mapper;

import com.playlist.domain.model.Usuario;
import com.playlist.infrastructure.persistence.entity.UsuarioEntity;
import org.springframework.stereotype.Component;

@Component
public class UsuarioMapper {

    public UsuarioEntity toEntity(Usuario usuario) {
        if (usuario == null) return null;

        return new UsuarioEntity(
                usuario.getUsername(),
                usuario.getPassword(),
                usuario.getRole());

    }

    public Usuario toDomain(UsuarioEntity entity) {
        if (entity == null) return null;

        return Usuario.builder()
                .username(entity.getUsername())
                .password(entity.getPassword())
                .role(entity.getRole())
                .build();

    }

}
