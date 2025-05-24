package com.playlist.domain.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Builder
public class ListaReproduccion {

    private String nombre;
    private String descripcion;
    private List<Cancion> canciones;

}
