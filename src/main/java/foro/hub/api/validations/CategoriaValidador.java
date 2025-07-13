package foro.hub.api.validations;

import foro.hub.api.entitites.Categoria;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.Arrays;
import java.util.stream.Collectors;

public class CategoriaValidador implements ConstraintValidator<ValidCategoria, String> {
    private String allowedValues;

    @Override
    public void initialize(ValidCategoria constraintAnnotation) {
        allowedValues = Arrays.stream(Categoria.values())
                .map(Enum::name)
                .collect(Collectors.joining(", "));
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value == null || value.isBlank()) {
            return false;
        }
        boolean valid = Arrays
                .stream(Categoria.values())
                .anyMatch(c -> c.name().equalsIgnoreCase(value));
        if (!valid) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate(
                    "El valor '" + value + "' no es una categoría válida. Valores permitidos: " + allowedValues
            ).addConstraintViolation();
        }
        return valid;
    }
}
