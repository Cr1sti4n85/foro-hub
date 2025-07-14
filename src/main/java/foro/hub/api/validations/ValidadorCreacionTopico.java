package foro.hub.api.validations;

import foro.hub.api.dto.DatosCreacionTopico;
import foro.hub.api.repositories.TopicoRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class ValidadorCreacionTopico implements ValidaTopico{
    private final TopicoRepository topicoRepository;

    @Override
    public void validar(DatosCreacionTopico datos) {
        var existeTopico = topicoRepository.existsByTituloAndMensaje(datos.titulo(), datos.mensaje());

        if (existeTopico){
            throw new RuntimeException("Ya existe un topico con ese titulo y mensaje");
        }

    }
}
