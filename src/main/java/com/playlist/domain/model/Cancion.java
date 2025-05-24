package com.playlist.domain.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class Cancion {

    private String titulo;
    private String artista;
    private String album;
    private String anno;
    private String genero;

    public Cancion() {
    }

    public Cancion(String titulo, String artista, String album, String anno, String genero) {
        this.titulo = titulo;
        this.artista = artista;
        this.album = album;
        this.anno = anno;
        this.genero = genero;
    }
}