package foro.hub.api.repositories;

import foro.hub.api.entitites.Curso;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CursoRepository extends JpaRepository<Curso, Long> {
}
