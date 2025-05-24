package com.playlist.infrastructure.persistence.mapper;

import com.playlist.domain.model.ListaReproduccion;
import com.playlist.infrastructure.persistence.entity.ListaReproduccionEntity;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class ListaReproduccionMapper {

    private final CancionMapper cancionMapper;

    public ListaReproduccionMapper(CancionMapper cancionMapper) {
        this.cancionMapper = cancionMapper;
    }

    public ListaReproduccionEntity toEntity(ListaReproduccion lista) {
        if (lista == null) return null;

        ListaReproduccionEntity entity = ListaReproduccionEntity.builder()
                .nombre(lista.getNombre())
                .descripcion(lista.getDescripcion())
                .build();

        if (lista.getCanciones() != null) {
            lista.getCanciones().forEach(cancion -> entity.agregarCancion(cancionMapper.toEntity(cancion)));
        }

        return entity;
    }

    public ListaReproduccion toDomain(ListaReproduccionEntity entity) {
        if (entity == null) return null;

        ListaReproduccion lista = ListaReproduccion.builder()
                .nombre(entity.getNombre())
                .descripcion(entity.getDescripcion())
                .build();

        if (entity.getCanciones() != null) {
            lista.setCanciones(
                    entity.getCanciones().stream()
                            .map(cancionMapper::toDomain)
                            .collect(Collectors.toList())
            );
        }

        return lista;
    }
}
