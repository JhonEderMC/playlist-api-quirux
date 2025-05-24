package com.playlist.infrastructure.entrypoints.web.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class ListaReproduccionDto {

    @NotBlank(message = "El nombre es obligatorio")
    private String nombre;

    private String descripcion;

    @Valid
    private List<CancionDto> canciones;

    public ListaReproduccionDto() {
        this.canciones = new ArrayList<>();
    }

    public ListaReproduccionDto(String nombre, String descripcion, List<CancionDto> canciones) {
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.canciones = canciones != null ? canciones : new ArrayList<>();
    }

    public void setCanciones(List<CancionDto> canciones) {
        this.canciones = canciones != null ? canciones : new ArrayList<>();
    }
}
