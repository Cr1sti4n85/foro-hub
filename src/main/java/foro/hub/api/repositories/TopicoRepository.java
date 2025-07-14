package foro.hub.api.repositories;

import foro.hub.api.entitites.Topico;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TopicoRepository extends JpaRepository<Topico, Long> {
}
