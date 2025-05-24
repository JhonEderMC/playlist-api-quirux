package com.playlist.infrastructure.persistence.adapter;

import com.playlist.domain.model.ListaReproduccion;
import com.playlist.infrastructure.persistence.entity.ListaReproduccionEntity;
import com.playlist.infrastructure.persistence.mapper.ListaReproduccionMapper;
import com.playlist.infrastructure.persistence.repository.ListaReproduccionJpaRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class ListaReproduccionRepositoryAdapterTest {

    private ListaReproduccionJpaRepository jpaRepository;
    private ListaReproduccionMapper mapper;
    private ListaReproduccionRepositoryAdapter adapter;

    @BeforeEach
    void setUp() {
        jpaRepository = mock(ListaReproduccionJpaRepository.class);
        mapper = mock(ListaReproduccionMapper.class);
        adapter = new ListaReproduccionRepositoryAdapter(jpaRepository, mapper);
    }

    @Test
    void guardarCorrectamente() {
        ListaReproduccion domain = new ListaReproduccion();
        ListaReproduccionEntity entity = new ListaReproduccionEntity();
        ListaReproduccionEntity savedEntity = new ListaReproduccionEntity();
        ListaReproduccion mappedDomain = new ListaReproduccion();

        when(mapper.toEntity(domain)).thenReturn(entity);
        when(jpaRepository.save(entity)).thenReturn(savedEntity);
        when(mapper.toDomain(savedEntity)).thenReturn(mappedDomain);

        ListaReproduccion result = adapter.save(domain);

        assertEquals(mappedDomain, result);
        verify(mapper).toEntity(domain);
        verify(jpaRepository).save(entity);
        verify(mapper).toDomain(savedEntity);
    }

    @Test
    void obtenerListaPorNombreCorrectamente() {
        String nombre = "test";
        ListaReproduccionEntity entity = new ListaReproduccionEntity();
        ListaReproduccion domain = new ListaReproduccion();

        when(jpaRepository.findByNombre(nombre)).thenReturn(Optional.of(entity));
        when(mapper.toDomain(entity)).thenReturn(domain);

        Optional<ListaReproduccion> result = adapter.findByNombre(nombre);

        assertTrue(result.isPresent());
        assertEquals(domain, result.get());
    }

    @Test
    void obtenerListaPorNombreNoEncontrado() {
        String nombre = "notfound";
        when(jpaRepository.findByNombre(nombre)).thenReturn(Optional.empty());

        Optional<ListaReproduccion> result = adapter.findByNombre(nombre);

        assertTrue(result.isEmpty());
    }

    @Test
    void obtenerTodasLasListas() {
        ListaReproduccionEntity entity = new ListaReproduccionEntity();
        ListaReproduccion domain = new ListaReproduccion();

        when(jpaRepository.findAll()).thenReturn(List.of(entity));
        when(mapper.toDomain(entity)).thenReturn(domain);

        List<ListaReproduccion> result = adapter.findAll();

        assertEquals(1, result.size());
        assertEquals(domain, result.get(0));
    }

    @Test
    void borrarListaCorrectamente() {
        String nombre = "delete";
        doNothing().when(jpaRepository).deleteByNombre(nombre);

        adapter.deleteByNombre(nombre);

        verify(jpaRepository).deleteByNombre(nombre);
    }

    @Test
    void existeListaCorrectamente() {
        String nombre = "exists";
        when(jpaRepository.existsByNombre(nombre)).thenReturn(true);

        boolean result = adapter.existsByNombre(nombre);

        assertTrue(result);
        verify(jpaRepository).existsByNombre(nombre);
    }

}