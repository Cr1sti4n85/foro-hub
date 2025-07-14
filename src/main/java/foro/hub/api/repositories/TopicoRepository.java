package foro.hub.api.repositories;

import foro.hub.api.entitites.Topico;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface TopicoRepository extends JpaRepository<Topico, Long> {

    Boolean existsByTituloAndMensaje(String titulo, String mensaje);

    //buscar por nombre de curso
    @Query("SELECT t FROM Topico t WHERE t.curso.nombre = :nombre")
    List<Topico> findAllByNombreDeCurso(@Param("nombre") String nombre);

}
