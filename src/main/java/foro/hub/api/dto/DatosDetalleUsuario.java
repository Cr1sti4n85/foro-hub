package foro.hub.api.dto;

import foro.hub.api.entitites.Usuario;

public record DatosDetalleUsuario(
        String nombre,
        String email
) {
    public DatosDetalleUsuario(Usuario usuario) {
        this(usuario.getNombre(), usuario.getEmail());
    }
}
