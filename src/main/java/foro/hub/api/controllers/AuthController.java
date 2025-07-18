package foro.hub.api.controllers;

import foro.hub.api.dto.JwtResponse;
import foro.hub.api.dto.LoginRequest;
import foro.hub.api.services.AuthService;
import foro.hub.api.services.JwtService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@AllArgsConstructor
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<JwtResponse> login(@Valid @RequestBody LoginRequest loginData){

        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginData.email(),
                        loginData.password()
                )
        );
        var user = authService.validarCuenta(loginData.email());
        var token = jwtService.generateToken(user);

        return ResponseEntity.ok(new JwtResponse(token));

    }

}
