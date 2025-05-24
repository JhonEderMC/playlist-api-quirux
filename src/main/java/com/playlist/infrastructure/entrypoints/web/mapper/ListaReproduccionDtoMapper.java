package com.playlist.infrastructure.entrypoints.web.mapper;

import com.playlist.domain.model.ListaReproduccion;
import com.playlist.infrastructure.entrypoints.web.dto.ListaReproduccionDto;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class ListaReproduccionDtoMapper {
    private final CancionDtoMapper cancionMapper;

    public ListaReproduccionDtoMapper(CancionDtoMapper cancionMapper) {
        this.cancionMapper = cancionMapper;
    }

    public ListaReproduccionDto toDto(ListaReproduccion lista) {
        if (lista == null) return null;

        ListaReproduccionDto dto = new ListaReproduccionDto(
                lista.getNombre(),
                lista.getDescripcion(),
                null
        );

        if (lista.getCanciones() != null) {
            dto.setCanciones(
                    lista.getCanciones().stream()
                            .map(cancionMapper::toDto)
                            .collect(Collectors.toList())
            );
        }

        return dto;
    }

    public ListaReproduccion toDomain(ListaReproduccionDto dto) {
        if (dto == null) return null;

        ListaReproduccion lista = new ListaReproduccion(
                dto.getNombre(),
                dto.getDescripcion()
        );

        if (dto.getCanciones() != null) {
            lista.setCanciones(
                    dto.getCanciones().stream()
                            .map(cancionMapper::toDomain)
                            .collect(Collectors.toList())
            );
        }

        return lista;
    }
}
