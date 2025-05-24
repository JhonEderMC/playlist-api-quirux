package com.playlist.infrastructure.persistence.mapper;

import com.playlist.domain.model.Cancion;
import com.playlist.infrastructure.persistence.entity.CancionEntity;
import org.springframework.stereotype.Component;

@Component
public class CancionMapper {

    public CancionEntity toEntity(Cancion cancion) {
        if (cancion == null) return null;

        return new CancionEntity(cancion.getTitulo(), cancion.getArtista(), cancion.getAlbum(), cancion.getAnno(), cancion.getGenero());

    }

    public Cancion toDomain(CancionEntity entity) {
        if (entity == null) return null;

        return Cancion.builder()
                .titulo(entity.getTitulo())
                .artista(entity.getArtista())
                .album(entity.getAlbum())
                .anno(entity.getAnno())
                .genero(entity.getGenero())
                .build();
    }
}
