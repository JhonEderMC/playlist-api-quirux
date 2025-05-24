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

}