package foro.hub.api.entitites;

import foro.hub.api.dto.DatosActualizarTopico;
import foro.hub.api.dto.DatosCreacionTopico;
import jakarta.persistence.*;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "topicos")
@Entity
public class Topico {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "titulo")
    private String titulo;

    @Column(name = "mensaje")
    private String mensaje;

    @Column(name = "fecha_creacion", insertable = false, updatable = false)
    private LocalDateTime fechaCreacion;

    @Column(name = "status", insertable = false)
//    @Enumerated(EnumType.STRING)
    private String status;

    @ManyToOne
    @JoinColumn(name = "usuario_id")
    private Usuario usuario;

    @ManyToOne
    @JoinColumn(name = "curso_id")
    private Curso curso;

    @OneToMany(mappedBy = "topico")
    private List<Respuesta> respuestas;

    public Topico(@Valid DatosCreacionTopico datosTopico) {
        this.titulo = datosTopico.titulo();
        this.mensaje = datosTopico.mensaje();
    }

    public void actualizarInfo(@Valid DatosActualizarTopico datosActualizacion) {
        if(datosActualizacion.titulo() != null){
            this.titulo = datosActualizacion.titulo();
        }
        if(datosActualizacion.mensaje() != null){
            this.mensaje = datosActualizacion.mensaje();
        }
        if(datosActualizacion.status() != null){
            this.status = datosActualizacion.status();
        }
    }
}
