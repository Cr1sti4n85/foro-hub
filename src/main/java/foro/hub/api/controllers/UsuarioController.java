package foro.hub.api.controllers;

import foro.hub.api.dto.DatosDetalleUsuario;
import foro.hub.api.dto.DatosRegistroUsuario;
import foro.hub.api.entitites.Usuario;
import foro.hub.api.repositories.UsuarioRepository;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Map;

@RestController
@RequestMapping("/usuarios")
@AllArgsConstructor
public class UsuarioController {

    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passEncoder;

    @PostMapping
    public ResponseEntity<?> registrarUsuario(
            @RequestBody @Valid DatosRegistroUsuario userData,
            UriComponentsBuilder ucb
            ){
        var usuario = new Usuario(userData);
        //validating business rules (unique email)
        if (usuarioRepository.existsByEmail(userData.email())){
            return ResponseEntity.badRequest().body(
                    Map.of("email", "El email ya está registrado")
            );
        }
        usuario.setPassword(passEncoder.encode(usuario.getPassword()));
        usuarioRepository.save(usuario);
        var uri = ucb.path("usuarios/{id}").buildAndExpand(usuario.getId()).toUri();

        return ResponseEntity.created(uri).body(new DatosDetalleUsuario(usuario));

    }
    
}
