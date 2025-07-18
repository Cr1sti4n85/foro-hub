package foro.hub.api.entitites;

import foro.hub.api.dto.DatosCreacionCurso;
import jakarta.persistence.*;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "cursos")
@Entity
public class Curso {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nombre")
    private String nombre;

    @Column(name = "categoria")
    private String categoria;

    @OneToMany(mappedBy = "curso")
    private List<Topico> topicos;

    public Curso(@Valid DatosCreacionCurso datosCurso) {
        this.nombre = datosCurso.nombre();
        this.categoria = datosCurso.categoria();
    }

    public void actualizarInfo(@Valid DatosCreacionCurso datosCurso) {

        if (datosCurso.nombre() != null){
            this.nombre = datosCurso.nombre();
        }
        if (datosCurso.categoria() != null){
            this.categoria = datosCurso.categoria();
        }
    }
}
