package foro.hub.api.services;

import foro.hub.api.entitites.Usuario;
import foro.hub.api.repositories.UsuarioRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class AuthService {

    private final UsuarioRepository usuarioRepository;

    public Usuario getCurrentUser() {
        var authentication = SecurityContextHolder.getContext().getAuthentication();
        var userId = (Long) authentication.getPrincipal();

        return usuarioRepository.findById(userId).orElse(null);
    }
}
