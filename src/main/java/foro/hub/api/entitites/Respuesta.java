package foro.hub.api.entitites;

import foro.hub.api.dto.DatosActualizarRespuesta;
import foro.hub.api.dto.DatosCreacionRespuesta;
import jakarta.persistence.*;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "respuestas")
@Entity
public class Respuesta {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "mensaje")
    private String mensaje;

    @Column(name = "fecha_creacion", insertable = false, updatable = false)
    private LocalDateTime fechaCreacion;

    @Column(name = "solucion", insertable = false)
    private Boolean solucion;

    @ManyToOne
    @JoinColumn(name = "topico_id")
    private Topico topico;

    @ManyToOne
    @JoinColumn(name = "usuario_id")
    private Usuario autorRespuesta;


    public Respuesta(@Valid DatosCreacionRespuesta datosRespuesta) {

        this.mensaje = datosRespuesta.mensaje();
    }

    public void actualizarInfo(@Valid DatosActualizarRespuesta datosActualizacion) {
        this.solucion = datosActualizacion.solucion();
    }
}
