package foro.hub.api.controllers;


import foro.hub.api.dto.DatosCreacionCurso;
import foro.hub.api.dto.DatosDetalleUsuario;
import foro.hub.api.entitites.Curso;
import foro.hub.api.exceptions.CourseNotFoundException;
import foro.hub.api.repositories.CursoRepository;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Map;

@RestController
@RequestMapping("/cursos")
@AllArgsConstructor
public class CursoController {

    private final CursoRepository cursoRepository;

    @PostMapping
    public ResponseEntity<Void> crearCurso(
            @Valid @RequestBody DatosCreacionCurso datosCurso,
            UriComponentsBuilder ucb
            ){

        var curso = new Curso(datosCurso);
        cursoRepository.save(curso);
        var uri = ucb.path("usuarios/{id}").buildAndExpand(curso.getId()).toUri();

        return ResponseEntity.created(uri).build();
    }


}
