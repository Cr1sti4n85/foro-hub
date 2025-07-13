package foro.hub.api.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record DatosRegistroUsuario(

        @NotBlank(message = "El campo nombre es obligatorio")//uso de jakarta validation
        @Size(max = 100, message = "El nombre no debe contener más de 100 caracteres")
        String nombre,

        @NotBlank(message = "El campo email es obligatorio")
        @Email
        String email,

        @NotBlank(message = "El campo password es obligatorio")
        @Size(min = 8, max = 20, message = "El pasword debe tener entre 8 y 20 caracteres")
        String password
) {
}
