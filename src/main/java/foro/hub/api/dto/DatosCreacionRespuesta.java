package foro.hub.api.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record DatosCreacionRespuesta(
        @NotBlank(message = "El mensaje es requerido")
        String mensaje
) {
}
