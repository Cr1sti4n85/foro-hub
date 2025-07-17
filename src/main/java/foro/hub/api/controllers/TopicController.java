package foro.hub.api.controllers;

import foro.hub.api.dto.*;
import foro.hub.api.entitites.Respuesta;
import foro.hub.api.repositories.CursoRepository;
import foro.hub.api.repositories.RespuestaRepository;
import foro.hub.api.repositories.TopicoRepository;
import foro.hub.api.services.AuthService;
import foro.hub.api.services.TopicoService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@RequestMapping("/topicos")
@SecurityRequirement(name = "bearer-key")
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
    public ResponseEntity<Page<DatosListaTopicos>> listarTopicos(
            @RequestParam(required = false) String nombre,
            Pageable paginacion){

        Page<DatosListaTopicos> topicos;
        if(nombre == null){
            topicos = topicoRepository.findAll(paginacion).map(
                    DatosListaTopicos::new
            );
            return ResponseEntity.ok().body(topicos);
        } else {
            topicos = topicoRepository.findAllByNombreDeCurso(nombre, paginacion).map(
                    DatosListaTopicos::new
            );
        }

        return ResponseEntity.ok().body(topicos);

    }

    @GetMapping("/{id}")
    public ResponseEntity<DatosDetalleTopico> buscarPorId(@PathVariable Long id) {

        var topico = topicoRepository.findById(id).orElse(null);
        if(topico == null){
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok().body(new DatosDetalleTopico(topico));

    }

    @PutMapping("/{id}")
    public ResponseEntity<DatosDetalleTopico> actualizarTopico(
            @PathVariable Long id,
            @Valid @RequestBody DatosActualizarTopico datosActualizacion
    ){
        var topico = topicoRepository.findById(id).orElse(null);
        if(topico == null){
            return ResponseEntity.notFound().build();
        }

        var user = authService.getCurrentUser();

        //revisar si el usuario que actualiza es el propietario del topico
        if(!user.getId().equals(topico.getUsuario().getId())){
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }

        topico.actualizarInfo(datosActualizacion);
        topicoRepository.save(topico);

        return ResponseEntity.ok().body(new DatosDetalleTopico(topico));

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> borrarTopico(
            @PathVariable Long id
    ){
        var topico = topicoRepository.findById(id).orElse(null);
        if(topico == null){
            return ResponseEntity.notFound().build();
        }

        var user = authService.getCurrentUser();

        if(!user.getId().equals(topico.getUsuario().getId())){
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }

        topicoRepository.delete(topico);

        return ResponseEntity.noContent().build();

    }

    @PutMapping("/{topicId}/respuestas/{respId}")
    public ResponseEntity<Void> aceptarRespuestaComoSolucion(
            @PathVariable Long respId,
            @Valid @RequestBody DatosActualizarRespuesta datosActualizacion
    ){

        var respuesta = respuestaRepository.findById(respId).orElse(null);
        if(respuesta == null){
            return ResponseEntity.notFound().build();
        }

        var user = authService.getCurrentUser();

        if(!user.getId().equals(respuesta.getTopico().getUsuario().getId())){
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }

        respuesta.actualizarInfo(datosActualizacion);
        respuestaRepository.save(respuesta);

        return ResponseEntity.ok().build();

    }

    @GetMapping("/{topicoId}/respuestas")
    public ResponseEntity<Page<DatosListaRespuestas>> listarRespuestas(
            @PathVariable Long topicoId,
            Pageable paginacion){

        Page<DatosListaRespuestas> topicos = respuestaRepository
                .findAllByTopicoId(topicoId, paginacion).map(DatosListaRespuestas::new);

        return ResponseEntity.ok().body(topicos);

    }
}
