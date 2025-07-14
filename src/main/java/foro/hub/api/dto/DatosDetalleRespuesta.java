package foro.hub.api.dto;

import foro.hub.api.entitites.Respuesta;

public record DatosDetalleRespuesta(
        Long id,
        String mensaje,
        Long autorRespuesta,
        Long topico
) {
    public DatosDetalleRespuesta(Respuesta nuevaRespuesta) {
        this(
                nuevaRespuesta.getId(),
                nuevaRespuesta.getMensaje(),
                nuevaRespuesta.getAutorRespuesta().getId(),
                nuevaRespuesta.getTopico().getId()
        );
    }
}
