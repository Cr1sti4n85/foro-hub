package foro.hub.api.services;

import foro.hub.api.dto.DatosCreacionTopico;
import foro.hub.api.dto.DatosDetalleTopico;
import foro.hub.api.entitites.Status;
import foro.hub.api.entitites.Topico;
import foro.hub.api.exceptions.CourseNotFoundException;
import foro.hub.api.repositories.CursoRepository;
import foro.hub.api.repositories.TopicoRepository;
import foro.hub.api.validations.ValidaTopico;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@AllArgsConstructor
@Service
public class TopicoService {
    private final CursoRepository cursoRepository;
    private final AuthService authService;
    private final TopicoRepository topicoRepository;
    private List<ValidaTopico> validarTopicos;

    public DatosDetalleTopico crear(DatosCreacionTopico datosTopico){
        var user = authService.getCurrentUser();
        if(user == null){
           throw new RuntimeException("Usuario no autenticado");
        }
        var curso = cursoRepository.findById(datosTopico.cursoId()).orElse(null);
        if(curso == null){
            throw new CourseNotFoundException();
        }

        //validar datos duplicados
        validarTopicos.forEach(v -> v.validar(datosTopico));

        var nuevoTopico = new Topico(datosTopico);
        nuevoTopico.setUsuario(user);
        nuevoTopico.setCurso(curso);
        nuevoTopico.setStatus(Status.ABIERTO.name());
        topicoRepository.save(nuevoTopico);

        return new DatosDetalleTopico(nuevoTopico);
    }


}
