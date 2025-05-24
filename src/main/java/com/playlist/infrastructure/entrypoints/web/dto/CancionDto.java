package com.playlist.infrastructure.entrypoints.web.dto;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class CancionDto {

    @NotBlank(message = "El título es obligatorio")
    private String titulo;

    @NotBlank(message = "El artista es obligatorio")
    private String artista;

    private String album;
    private String anno;
    private String genero;

}