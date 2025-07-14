package foro.hub.api.dto;

import foro.hub.api.entitites.Curso;
import foro.hub.api.entitites.Status;
import foro.hub.api.entitites.Topico;
import foro.hub.api.entitites.Usuario;

import java.time.LocalDateTime;

public record DatosListaTopicos(
        Long id,
        String titulo,
        String mensaje,
        LocalDateTime fechaCreacion,
        Status status,
        DatosDetalleUsuario autor,
        DatosDetalleCurso curso
) {
    public DatosListaTopicos(Topico t) {
        this(
                t.getId(),
                t.getTitulo(),
                t.getMensaje(),
                t.getFechaCreacion(),
                t.getStatus(),
                new DatosDetalleUsuario(t.getUsuario()),
                new DatosDetalleCurso(t.getCurso())
        );
    }
}
