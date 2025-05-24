package com.playlist.infrastructure.entrypoints.web.mapper;

import com.playlist.domain.model.Cancion;
import com.playlist.infrastructure.entrypoints.web.dto.CancionDto;
import org.springframework.stereotype.Component;

@Component
public class CancionDtoMapper {

    public CancionDto toDto(Cancion cancion) {
        if (cancion == null) return null;

        return CancionDto.builder()
                .titulo(cancion.getTitulo())
                .artista(cancion.getArtista())
                .album(cancion.getAlbum())
                .anno(cancion.getAnno())
                .genero(cancion.getGenero())
                .build();

    }

    public Cancion toDomain(CancionDto dto) {
        if (dto == null) return null;

        return Cancion.builder()
                .titulo(dto.getTitulo())
                .artista(dto.getArtista())
                .album(dto.getAlbum())
                .anno(dto.getAnno())
                .genero(dto.getGenero())
                .build();
    }

}
