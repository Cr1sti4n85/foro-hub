package foro.hub.api.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
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

}
