package foro.hub.api.controllers;


import foro.hub.api.dto.DatosCreacionCurso;
import foro.hub.api.dto.DatosListaCursos;
import foro.hub.api.entitites.Curso;
import foro.hub.api.repositories.CursoRepository;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;

@RestController
@RequestMapping("/cursos")
@SecurityRequirement(name = "bearer-key")
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

    @GetMapping
    public ResponseEntity<List<DatosListaCursos>> listarCursos(){
        var cursos = cursoRepository.findAll()
                .stream().map(DatosListaCursos::new).toList();
        return ResponseEntity.ok().body(cursos);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> actualizarCurso(
            @PathVariable Long id,
            @Valid @RequestBody DatosCreacionCurso datosCurso
    ){

        var curso = cursoRepository.findById(id).orElse(null);
        if(curso == null){
            return ResponseEntity.notFound().build();
        }
        curso.actualizarInfo(datosCurso);
        cursoRepository.save(curso);

        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> borrarCurso(@PathVariable Long id){
        var curso = cursoRepository.findById(id).orElse(null);
        if(curso == null){
            return ResponseEntity.notFound().build();
        }
        cursoRepository.delete(curso);
        return ResponseEntity.noContent().build();
    }


}
