package foro.hub.api.entitites;

import foro.hub.api.dto.DatosRegistroUsuario;
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
@Table(name = "usuarios")
@Entity
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nombre")
    private String nombre;

    @Column(name = "email")
    private String email;

    @Column(name = "password")
    private String password;

    @Column(name = "role")
    @Enumerated(EnumType.STRING)
    private Role role;

    @Column(name = "activo", insertable = false)
    private Boolean activo;

    @OneToMany(mappedBy = "usuario")
    private List<Topico> topicos;

    @OneToMany(mappedBy = "autorRespuesta")
    private List<Respuesta> respuestas;


    public Usuario(@Valid DatosRegistroUsuario userData) {
        this.nombre = userData.nombre();
        this.email = userData.email();
        this.password = userData.password();
    }


    public void desactivar() {
        this.activo = false;
    }
}
