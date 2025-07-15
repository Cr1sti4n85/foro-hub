package foro.hub.api.validations;

import foro.hub.api.entitites.Status;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.Arrays;
import java.util.stream.Collectors;

public class StatusValidador implements ConstraintValidator<ValidStatus, String> {
    private String allowedValues;

    @Override
    public void initialize(ValidStatus constraintAnnotation) {
        allowedValues = Arrays.stream(Status.values())
                .map(Enum::name)
                .collect(Collectors.joining(", "));
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value == null || value.isBlank()) {
            return true;
        }
        boolean valid = Arrays
                .stream(Status.values())
                .anyMatch(s -> s.name().equalsIgnoreCase(value));
        if (!valid) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate(
                    "El valor '" + value + "' no es un status válido. Valores permitidos: " + allowedValues
            ).addConstraintViolation();
        }
        return valid;
    }
}
