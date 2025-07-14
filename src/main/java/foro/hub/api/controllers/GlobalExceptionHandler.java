package foro.hub.api.controllers;

import foro.hub.api.exceptions.CourseNotFoundException;
import foro.hub.api.exceptions.TopicoDuplicadoException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleValidationErrors(
            MethodArgumentNotValidException e)
    {
        Map<String, String> errors = new HashMap<>();

        //getBindingResult da acceso a detalles de la excepcion
        //getFieldErrors retorna una lista de los errores
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

}
