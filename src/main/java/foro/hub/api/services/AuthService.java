package foro.hub.api.services;

import foro.hub.api.entitites.Usuario;
import foro.hub.api.exceptions.CuentaEliminadaException;
import foro.hub.api.exceptions.UnauthorizedNotFoundUserException;
import foro.hub.api.repositories.UsuarioRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class AuthService {

    private final UsuarioRepository usuarioRepository;
    private final JwtService jwtService;

    public Usuario getCurrentUser() {
        var authentication = SecurityContextHolder.getContext().getAuthentication();
        var userId = (Long) authentication.getPrincipal();

        var user = usuarioRepository.findById(userId).orElse(null);
        if(user == null){
            throw new UnauthorizedNotFoundUserException();
        }
        return user;
    }

    public Usuario validarCuenta(String email){
        var user = usuarioRepository.findByEmail(email).orElseThrow();
        //validar si cuenta esta activa
        if(!user.getActivo()){
            throw new CuentaEliminadaException();
        }
        return user;

    }
}
