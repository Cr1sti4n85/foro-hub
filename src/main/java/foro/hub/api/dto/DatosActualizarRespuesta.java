package foro.hub.api.dto;

import jakarta.validation.constraints.NotNull;

public record DatosActualizarRespuesta(
        @NotNull
        Boolean solucion
) {
}
