package foro.hub.api.controllers;

import foro.hub.api.exceptions.CourseNotFoundException;
import foro.hub.api.exceptions.CuentaEliminadaException;
import foro.hub.api.exceptions.TopicoDuplicadoException;
import foro.hub.api.exceptions.UnauthorizedNotFoundUserException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.resource.NoResourceFoundException;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleValidationErrors(
            MethodArgumentNotValidException e)
    {
        Map<String, String> errors = new HashMap<>();

        e.getBindingResult()
                .getFieldErrors()
                .forEach(fe -> {
                    //guardamos los errores en el Map
                    errors.put(fe.getField(), fe.getDefaultMessage());
                });
        return ResponseEntity.badRequest().body(errors);

    }

    @ExceptionHandler(TopicoDuplicadoException.class)
    public ResponseEntity<Map<String, String>> handleTopicoDuplicado(){
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(Map.of("error", "Este tópico ya existe"));
    }

    @ExceptionHandler(CourseNotFoundException.class)
    public ResponseEntity<Map<String, String>> handleCourseNotFound(){
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(Map.of("error", "Curso no encontrado"));
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<Map<String, String>> handleTypeMismatch(){
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(Map.of("error", "El parámetro no es válido"));
    }

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ResponseEntity<Map<String, String>> handleMethodNotSupported(){
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(Map.of("error", "Petición no válida"));
    }

    @ExceptionHandler(NoResourceFoundException.class)
    public ResponseEntity<Map<String, String>> handleNoResource(){
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(Map.of("error", "URL no válida."));
    }

    //este metodo retornara unauthorized al enviar credenciales erroneas
    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<Void> handleBadCredentials(){
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }

    //este metodo retornara unauthorized al enviar credenciales erroneas
    @ExceptionHandler(CuentaEliminadaException.class)
    public ResponseEntity<Map<String, String>> handleCuentaEliminada(){
        return ResponseEntity
                .status(HttpStatus.UNAUTHORIZED)
                .body(Map.of("error", "Esta cuenta está desactivada. Contácta al administrador"));
    }

    @ExceptionHandler(UnauthorizedNotFoundUserException.class)
    public ResponseEntity<Map<String, String>> handleUsuarioNoEncontrado(){
        return ResponseEntity
                .status(HttpStatus.UNAUTHORIZED)
                .body(Map.of("error", "Usuario no válido"));
    }


    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String, String>> handleUnexpectedException(Exception ex) {
        // registrar esta excepcion
        ex.printStackTrace();
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(Map.of("error", "Ocurrió un error inesperado"));
    }


}
