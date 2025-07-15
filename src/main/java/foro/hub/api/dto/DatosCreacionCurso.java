package foro.hub.api.dto;

import foro.hub.api.validations.ValidCategoria;
import jakarta.validation.constraints.NotBlank;

public record DatosCreacionCurso(
        @NotBlank(message = "El campo nombre es obligatorio")
        String nombre,
        @ValidCategoria
        String categoria
) {
}
