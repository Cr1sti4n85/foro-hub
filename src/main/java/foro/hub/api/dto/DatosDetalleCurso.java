package foro.hub.api.dto;

import foro.hub.api.entitites.Curso;

public record DatosDetalleCurso(
        Long id,
        String nombre,
        String categoria

) {
    public DatosDetalleCurso(Curso curso) {
        this(curso.getId(), curso.getNombre(), curso.getCategoria());
    }
}
