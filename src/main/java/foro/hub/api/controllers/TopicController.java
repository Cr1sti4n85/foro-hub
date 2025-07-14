package foro.hub.api.controllers;

import foro.hub.api.dto.*;
import foro.hub.api.entitites.Respuesta;
import foro.hub.api.entitites.Topico;
import foro.hub.api.exceptions.CourseNotFoundException;
import foro.hub.api.repositories.CursoRepository;
import foro.hub.api.repositories.RespuestaRepository;
import foro.hub.api.repositories.TopicoRepository;
import foro.hub.api.services.AuthService;
import foro.hub.api.services.TopicoService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/topicos")
@AllArgsConstructor
public class TopicController {

    private final CursoRepository cursoRepository;
    private final TopicoService topicoService;
    private final AuthService authService;
    private final TopicoRepository topicoRepository;
    private final RespuestaRepository respuestaRepository;

    @PostMapping
    public ResponseEntity<DatosDetalleTopico> crearTopico(
            @Valid @RequestBody DatosCreacionTopico datosTopico,
            UriComponentsBuilder ucb
    ){

        var nuevoTopico = topicoService.crear(datosTopico);

        var uri = ucb.path("/topicos/{id}").buildAndExpand(nuevoTopico.id()).toUri();

        return ResponseEntity.created(uri).body(nuevoTopico);

    }

    @PostMapping("/{topicId}/respuestas")
    public ResponseEntity<DatosDetalleRespuesta> crearRespuesta(
            @Valid @RequestBody DatosCreacionRespuesta datosRespuesta,
            @PathVariable Long topicId,
            UriComponentsBuilder ucb
            ){
        var user = authService.getCurrentUser();
        if(user == null){
            return ResponseEntity.notFound().build();
        }

        var topico = topicoRepository.findById(topicId).orElse(null);
        if(topico == null){
            return ResponseEntity.notFound().build();
        }

        var nuevaRespuesta = new Respuesta(datosRespuesta);
        nuevaRespuesta.setAutorRespuesta(user);
        nuevaRespuesta.setTopico(topico);
        respuestaRepository.save(nuevaRespuesta);


        var uri = ucb.path("/topicos/{topicId}/respuestas/{id}")
                .buildAndExpand(topico.getId(), nuevaRespuesta.getId()).toUri();

        return ResponseEntity.created(uri).body(new DatosDetalleRespuesta(nuevaRespuesta));

    }

    @GetMapping
    public ResponseEntity<List<DatosListaTopicos>> listarTopicos(@RequestParam String nombre){

        var topicos = topicoRepository.findAllByNombreDeCurso(nombre)
                .stream().map(DatosListaTopicos::new).toList();

        return ResponseEntity.ok().body(topicos);

    }

}
