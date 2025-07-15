package foro.hub.api.controllers;

import foro.hub.api.exceptions.CourseNotFoundException;
import foro.hub.api.exceptions.TopicoDuplicadoException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

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
                .body(Map.of("error", "Endpoint no válido, Verifica que la URL sea correcta"));
    }

}
