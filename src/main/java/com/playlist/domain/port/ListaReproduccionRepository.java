package com.playlist.domain.port;

import com.playlist.domain.model.ListaReproduccion;

import java.util.List;
import java.util.Optional;

public interface ListaReproduccionRepository {

    ListaReproduccion save(ListaReproduccion lista);
    Optional<ListaReproduccion> findByNombre(String nombre);
    List<ListaReproduccion> findAll();
    void deleteByNombre(String nombre);
    boolean existsByNombre(String nombre);

}
