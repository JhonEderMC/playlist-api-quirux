package com.playlist.infrastructure.entrypoints.web.controller;

import com.playlist.domain.model.ListaReproduccion;
import com.playlist.domain.service.ListaReproduccionService;
import com.playlist.infrastructure.entrypoints.web.dto.ListaReproduccionDto;
import com.playlist.infrastructure.entrypoints.web.mapper.ListaReproduccionDtoMapper;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/lists")
//@CrossOrigin(origins = "http://localhost:4200") // TODO: configurar luego
public class ListaReproduccionController {

    private final ListaReproduccionService service;
    private final ListaReproduccionDtoMapper mapper;

    public ListaReproduccionController(ListaReproduccionService service,
                                       ListaReproduccionDtoMapper mapper) {
        this.service = service;
        this.mapper = mapper;
    }

    @PostMapping
    public ResponseEntity<ListaReproduccionDto> crearLista(@Valid @RequestBody ListaReproduccionDto listaDto) {
        try {
            ListaReproduccion lista = mapper.toDomain(listaDto);
            ListaReproduccion listaCreada = service.crearLista(lista);

            URI location = ServletUriComponentsBuilder
                    .fromCurrentRequest()
                    .path("/{nombre}")
                    .buildAndExpand(listaCreada.getNombre())
                    .toUri();

            return ResponseEntity.created(location)
                    .body(mapper.toDto(listaCreada));

        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping
    public ResponseEntity<List<ListaReproduccionDto>> obtenerTodasLasListas() {
        List<ListaReproduccion> listas = service.obtenerTodasLasListas();
        List<ListaReproduccionDto> listasDto = listas.stream()
                .map(mapper::toDto)
                .toList();
        return ResponseEntity.ok(listasDto);
    }

    @GetMapping("/{listName}")
    public ResponseEntity<ListaReproduccionDto> obtenerListaPorNombre(@PathVariable String listName) {
        Optional<ListaReproduccion> lista = service.obtenerListaPorNombre(listName);

        return lista.map(listaReproduccion -> ResponseEntity.ok(mapper.toDto(listaReproduccion)))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{listName}")
    public ResponseEntity<Void> eliminarLista(@PathVariable String listName) {
        try {
            service.eliminarLista(listName);
            return ResponseEntity.noContent().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }
}