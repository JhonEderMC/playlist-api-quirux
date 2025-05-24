package com.playlist.domain.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class ListaReproduccion {

    private String nombre;
    private String descripcion;
    private List<Cancion> canciones;

    public ListaReproduccion() {
        this.canciones = new ArrayList<>();
    }

    public ListaReproduccion(String nombre, String descripcion) {
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.canciones = new ArrayList<>();
    }

    public ListaReproduccion(String nombre, String descripcion, List<Cancion> canciones) {
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.canciones = canciones != null ? canciones : new ArrayList<>();
    }


    public void setCanciones(List<Cancion> canciones) {
        this.canciones = canciones != null ? canciones : new ArrayList<>();
    }

    public void addCancion(Cancion cancion) {
        if (this.canciones == null) {
            this.canciones = new ArrayList<>();
        }
        this.canciones.add(cancion);
    }

}
