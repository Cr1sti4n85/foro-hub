package foro.hub.api.validations;

import foro.hub.api.dto.DatosCreacionTopico;
import foro.hub.api.exceptions.TopicoDuplicadoException;
import foro.hub.api.repositories.TopicoRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.Map;

@Component
@AllArgsConstructor
public class ValidadorCreacionTopico implements ValidaTopico{
    private final TopicoRepository topicoRepository;

    @Override
    public void validar(DatosCreacionTopico datos) {
        var existeTopico = topicoRepository.existsByTituloAndMensaje(datos.titulo(), datos.mensaje());

        if (existeTopico){
            throw new TopicoDuplicadoException();
        }

    }
}
