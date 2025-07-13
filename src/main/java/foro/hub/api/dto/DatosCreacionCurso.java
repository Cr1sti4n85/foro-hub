package foro.hub.api.dto;

import foro.hub.api.entitites.Categoria;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record DatosCreacionCurso(
        @NotBlank(message = "El campo nombre es obligatorio")
        String nombre,
        @NotNull(message = "El campo categoria es obligatorio")
        Categoria categoria
) {
}
