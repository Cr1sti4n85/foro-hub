package foro.hub.api.controllers;

import foro.hub.api.dto.DatosDetalleUsuario;
import foro.hub.api.dto.DatosRegistroUsuario;
import foro.hub.api.entitites.Role;
import foro.hub.api.entitites.Usuario;
import foro.hub.api.repositories.UsuarioRepository;
import foro.hub.api.services.AuthService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Map;

@RestController
@RequestMapping("/usuarios")
@AllArgsConstructor
public class UsuarioController {

    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passEncoder;
    private final AuthService authService;

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
        usuario.setRole(Role.USER);
        usuarioRepository.save(usuario);
        var uri = ucb.path("usuarios/{id}").buildAndExpand(usuario.getId()).toUri();

        return ResponseEntity.created(uri).body(new DatosDetalleUsuario(usuario));

    }

    @GetMapping("/perfil")
    @SecurityRequirement(name = "bearer-key")
    public ResponseEntity<?> getProfile(){

        var user = authService.getCurrentUser();
        if(user == null){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(new DatosDetalleUsuario(user));
    }

    @DeleteMapping("/perfil")
    @SecurityRequirement(name = "bearer-key")
    public ResponseEntity<Void> desactivarCuenta(){
        var user = authService.getCurrentUser();
        if(user == null){
            return ResponseEntity.notFound().build();
        }
        user.desactivar();
        usuarioRepository.save(user);
        return ResponseEntity.ok().build();
    }

    
}
