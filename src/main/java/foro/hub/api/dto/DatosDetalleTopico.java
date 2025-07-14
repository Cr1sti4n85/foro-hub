package foro.hub.api.dto;

import foro.hub.api.entitites.Topico;

public record DatosDetalleTopico(
        Long id,
        String titulo,
        String mensaje
) {
    public DatosDetalleTopico(Topico nuevoTopico) {
        this(nuevoTopico.getId(),
                nuevoTopico.getTitulo(),
                nuevoTopico.getMensaje());
    }
}
