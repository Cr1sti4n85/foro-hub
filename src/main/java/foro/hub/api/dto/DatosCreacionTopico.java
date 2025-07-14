package foro.hub.api.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;


public record DatosCreacionTopico(
        @NotBlank(message = "El titulo es requerido")
        String titulo,
        @NotBlank(message = "El mensaje es requerido")
        String mensaje,
        @NotNull(message = "El id del curso es requerido")
        Long cursoId
) {
}
