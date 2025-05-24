package com.playlist.domain.service;

import com.playlist.domain.model.ListaReproduccion;
import com.playlist.domain.port.ListaReproduccionRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class ListaReproduccionServiceTest {

    private ListaReproduccionRepository repository;
    private ListaReproduccionService service;

    @BeforeEach
    void setUp() {
        repository = mock(ListaReproduccionRepository.class);
        service = new ListaReproduccionService(repository);
    }

    @Test
    void crearListaCorrectamente() {
        ListaReproduccion lista = new ListaReproduccion("Rock", "Rock playlist");
        when(repository.existsByNombre("Rock")).thenReturn(false);
        when(repository.save(lista)).thenReturn(lista);

        ListaReproduccion result = service.crearLista(lista);

        assertEquals(lista, result);
        verify(repository).save(lista);
    }

    @Test
    void crearListaNombreNullExcepcion() {
        ListaReproduccion lista1 = new ListaReproduccion(null, "desc");
        ListaReproduccion lista2 = new ListaReproduccion("   ", "desc");

        Exception ex1 = assertThrows(IllegalArgumentException.class, () -> service.crearLista(lista1));
        Exception ex2 = assertThrows(IllegalArgumentException.class, () -> service.crearLista(lista2));

        assertEquals(ListaReproduccionService.EL_NOMBRE_LISTA_NO_PUEDE_SER_NULO_O_VACIO, ex1.getMessage());
        assertEquals(ListaReproduccionService.EL_NOMBRE_LISTA_NO_PUEDE_SER_NULO_O_VACIO, ex2.getMessage());
    }

    @Test
    void crearListaNombreDuplicadoExcepcion() {
        ListaReproduccion lista = new ListaReproduccion("Pop", "desc");
        when(repository.existsByNombre("Pop")).thenReturn(true);

        Exception ex = assertThrows(IllegalArgumentException.class, () -> service.crearLista(lista));
        assertEquals(ListaReproduccionService.YA_EXISTE_UNA_LISTA_CON_ESE_NOMBRE, ex.getMessage());
    }

    @Test
    void obtenerTodasLasListas() {
        ListaReproduccion lista = new ListaReproduccion("Jazz", "desc");
        when(repository.findAll()).thenReturn(List.of(lista));

        List<ListaReproduccion> result = service.obtenerTodasLasListas();

        assertEquals(1, result.size());
        assertEquals(lista, result.get(0));
    }

    @Test
    void obtenerListaPorNombreNoEncontrada() {
        ListaReproduccion lista = new ListaReproduccion("Indie", "desc");
        when(repository.findByNombre("Indie")).thenReturn(Optional.of(lista));

        Optional<ListaReproduccion> result = service.obtenerListaPorNombre("Indie");

        assertTrue(result.isPresent());
        assertEquals(lista, result.get());
    }

    @Test
    void obtenerListaPorNombreNotFound() {
        when(repository.findByNombre("Unknown")).thenReturn(Optional.empty());

        Optional<ListaReproduccion> result = service.obtenerListaPorNombre("Unknown");

        assertTrue(result.isEmpty());
    }

    @Test
    void eliminarListaCorrectamente() {
        when(repository.existsByNombre("Rock")).thenReturn(true);
        doNothing().when(repository).deleteByNombre("Rock");

        assertDoesNotThrow(() -> service.eliminarLista("Rock"));
        verify(repository).deleteByNombre("Rock");
    }

    @Test
    void eliminarListaNoExisteExcepcion() {
        when(repository.existsByNombre("NoExiste")).thenReturn(false);

        Exception ex = assertThrows(IllegalArgumentException.class, () -> service.eliminarLista("NoExiste"));
        assertEquals(ListaReproduccionService.LA_LISTA_NO_EXISTE, ex.getMessage());
    }

}