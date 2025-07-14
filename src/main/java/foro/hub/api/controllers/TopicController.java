package foro.hub.api.controllers;

import foro.hub.api.dto.DatosCreacionTopico;
import foro.hub.api.dto.DatosDetalleTopico;
import foro.hub.api.entitites.Topico;
import foro.hub.api.repositories.CursoRepository;
import foro.hub.api.repositories.TopicoRepository;
import foro.hub.api.services.AuthService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@RequestMapping("/topicos")
@AllArgsConstructor
public class TopicController {

    private final CursoRepository cursoRepository;
    private final AuthService authService;
    private final TopicoRepository topicoRepository;

    @PostMapping
    public ResponseEntity<DatosDetalleTopico> crearTopico(
            @Valid @RequestBody DatosCreacionTopico datosTopico,
            UriComponentsBuilder ucb
    ){
        var user = authService.getCurrentUser();
        if(user == null){
            return ResponseEntity.notFound().build();
        }
        var curso = cursoRepository.findById(datosTopico.cursoId()).orElse(null);
        if(curso == null){
            return ResponseEntity.notFound().build();
        }

        var nuevoTopico = new Topico(datosTopico);
        nuevoTopico.setUsuario(user);
        nuevoTopico.setCurso(curso);
        topicoRepository.save(nuevoTopico);
        var uri = ucb.path("/topicos/{id}").buildAndExpand(nuevoTopico.getId()).toUri();

        return ResponseEntity.created(uri).body(new DatosDetalleTopico(nuevoTopico));

    }
}
