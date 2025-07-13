package foro.hub.api.controllers;


import foro.hub.api.dto.DatosCreacionCurso;
import foro.hub.api.dto.DatosDetalleUsuario;
import foro.hub.api.entitites.Curso;
import foro.hub.api.repositories.CursoRepository;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

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
