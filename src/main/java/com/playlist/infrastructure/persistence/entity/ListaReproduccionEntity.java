package com.playlist.infrastructure.persistence.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "listas_reproduccion")
public class ListaReproduccionEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String nombre;

    private String descripcion;

    @OneToMany(mappedBy = "lista", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<CancionEntity> canciones;

    public ListaReproduccionEntity() {}

    public ListaReproduccionEntity(String nombre, String descripcion) {
        this.nombre = nombre;
        this.descripcion = descripcion;
    }

    public void agregarCancion(CancionEntity cancion) {
        if (canciones == null) {
            canciones = new ArrayList<>();
        }
        canciones.add(cancion);
        cancion.setLista(this);
    }

    public void eliminarCancion(CancionEntity cancion) {
        canciones.remove(cancion);
        cancion.setLista(null);
    }

}
