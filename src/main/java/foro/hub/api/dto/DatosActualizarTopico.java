package foro.hub.api.dto;

import foro.hub.api.validations.ValidStatus;

public record DatosActualizarTopico(
        String titulo,
        String mensaje,
        @ValidStatus
        String status
) {
}
