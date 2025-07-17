package foro.hub.api.dto;

import foro.hub.api.entitites.Respuesta;

import java.time.LocalDateTime;

public record DatosListaRespuestas(
        Long id,
        String mensaje,
        LocalDateTime fechaCreacion,
        String autor
) {
    public DatosListaRespuestas(Respuesta r) {
        this(   r.getId(),
                r.getMensaje(),
                r.getFechaCreacion(),
                r.getAutorRespuesta().getNombre());
    }


}
