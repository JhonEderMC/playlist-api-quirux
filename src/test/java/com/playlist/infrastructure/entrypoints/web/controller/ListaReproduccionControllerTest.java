package com.playlist.infrastructure.entrypoints.web.controller;

import com.playlist.domain.model.ListaReproduccion;
import com.playlist.domain.service.ListaReproduccionService;
import com.playlist.infrastructure.entrypoints.web.dto.ListaReproduccionDto;
import com.playlist.infrastructure.entrypoints.web.mapper.ListaReproduccionDtoMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.doThrow;

class ListaReproduccionControllerTest {

    private ListaReproduccionService service;
    private ListaReproduccionDtoMapper mapper;
    private ListaReproduccionController controller;

    @BeforeEach
    void setUp() {
        service = mock(ListaReproduccionService.class);
        mapper = mock(ListaReproduccionDtoMapper.class);
        controller = new ListaReproduccionController(service, mapper);
    }

    @Test
    void crearListaBadRequest() {
        ListaReproduccionDto dto = new ListaReproduccionDto();
        ListaReproduccion domain = new ListaReproduccion();

        when(mapper.toDomain(dto)).thenReturn(domain);
        when(service.crearLista(domain)).thenThrow(new IllegalArgumentException("error"));

        ResponseEntity<ListaReproduccionDto> response = controller.crearLista(dto);

        assertEquals(400, response.getStatusCode().value());
        assertNull(response.getBody());
    }

    @Test
    void obtenerTodasLasListasCorrectamente() {
        ListaReproduccion domain = new ListaReproduccion();
        ListaReproduccionDto dto = new ListaReproduccionDto();

        when(service.obtenerTodasLasListas()).thenReturn(List.of(domain));
        when(mapper.toDto(domain)).thenReturn(dto);

        ResponseEntity<List<ListaReproduccionDto>> response = controller.obtenerTodasLasListas();

        assertEquals(200, response.getStatusCode().value());
        assertEquals(1, response.getBody().size());
        assertEquals(dto, response.getBody().get(0));
    }

    @Test
    void obtenerListaPorNombreCorrectamente() {
        ListaReproduccion domain = new ListaReproduccion();
        ListaReproduccionDto dto = new ListaReproduccionDto();

        when(service.obtenerListaPorNombre("MyList")).thenReturn(Optional.of(domain));
        when(mapper.toDto(domain)).thenReturn(dto);

        ResponseEntity<ListaReproduccionDto> response = controller.obtenerListaPorNombre("MyList");

        assertEquals(200, response.getStatusCode().value());
        assertEquals(dto, response.getBody());
    }

    @Test
    void obtenerListaPorNombreNoEncontrada() {
        when(service.obtenerListaPorNombre("Unknown")).thenReturn(Optional.empty());

        ResponseEntity<ListaReproduccionDto> response = controller.obtenerListaPorNombre("Unknown");

        assertEquals(404, response.getStatusCode().value());
        assertNull(response.getBody());
    }

    @Test
    void eliminarListaCorrectamente() {
        doNothing().when(service).eliminarLista("MyList");

        ResponseEntity<Void> response = controller.eliminarLista("MyList");

        assertEquals(204, response.getStatusCode().value());
    }

    @Test
    void eliminarListaNoEncontrada() {
        doThrow(new IllegalArgumentException("not found")).when(service).eliminarLista("Unknown");

        ResponseEntity<Void> response = controller.eliminarLista("Unknown");

        assertEquals(404, response.getStatusCode().value());
    }

}