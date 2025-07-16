package foro.hub.api.dto;

import foro.hub.api.entitites.Curso;

import java.util.List;

public record DatosListaCursos(
        Long id,
        String nombre,
        String categoria

) {

    public DatosListaCursos(Curso curso) {
        this(curso.getId(), curso.getNombre(), curso.getCategoria());
    }
}
