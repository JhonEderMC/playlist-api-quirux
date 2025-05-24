package com.playlist.domain.service;

import com.playlist.domain.model.ListaReproduccion;
import com.playlist.domain.port.ListaReproduccionRepository;

import java.util.List;
import java.util.Optional;

public class ListaReproduccionService {

    public static final String EL_NOMBRE_LISTA_NO_PUEDE_SER_NULO_O_VACIO = "El nombre de la lista no puede ser nulo o vacío";
    public static final String YA_EXISTE_UNA_LISTA_CON_ESE_NOMBRE = "Ya existe una lista con ese nombre";
    public static final String LA_LISTA_NO_EXISTE = "La lista no existe";

    private final ListaReproduccionRepository repository;

    public ListaReproduccionService(ListaReproduccionRepository repository) {
        this.repository = repository;
    }

    public ListaReproduccion crearLista(ListaReproduccion lista) {
        if (lista.getNombre() == null || lista.getNombre().trim().isEmpty()) {
            throw new IllegalArgumentException(EL_NOMBRE_LISTA_NO_PUEDE_SER_NULO_O_VACIO);
        }

        if (repository.existsByNombre(lista.getNombre())) {
            throw new IllegalArgumentException(YA_EXISTE_UNA_LISTA_CON_ESE_NOMBRE);
        }

        return repository.save(lista);
    }

    public List<ListaReproduccion> obtenerTodasLasListas() {
        return repository.findAll();
    }

    public Optional<ListaReproduccion> obtenerListaPorNombre(String nombre) {
        return repository.findByNombre(nombre);
    }

    public void eliminarLista(String nombre) {
        if (!repository.existsByNombre(nombre)) {
            throw new IllegalArgumentException(LA_LISTA_NO_EXISTE);
        }
        repository.deleteByNombre(nombre);
    }
}
