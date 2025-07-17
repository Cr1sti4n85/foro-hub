package foro.hub.api.repositories;

import foro.hub.api.entitites.Respuesta;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RespuestaRepository extends JpaRepository<Respuesta, Long> {
    Page<Respuesta> findAllByTopicoId(Long topicoId, Pageable paginacion);
}
