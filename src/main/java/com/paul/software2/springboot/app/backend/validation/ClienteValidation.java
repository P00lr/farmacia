package com.paul.software2.springboot.app.backend.validation;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.paul.software2.springboot.app.backend.entities.Cliente;

@Component
public class ClienteValidation implements Validator{

    @Override
    public boolean supports(Class<?> clazz) {
        return Cliente.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        Cliente cliente = (Cliente) target;

        // Validar si el nombre está vacío o es solo espacios en blanco
        ValidationUtils.rejectIfEmptyOrWhitespace(
            errors,
             "nombre",
             "nombre.requerido",
             "El nombre es requerido");

        // Validar si el apellido (paterno) está vacío
        ValidationUtils.rejectIfEmptyOrWhitespace(
            errors,
             "paterno",
             "apellido.requerido",
             "El apellido es requerido");

        // Validar si el email tiene formato válido
        if (cliente.getEmail() != null && !cliente.getEmail().matches("^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$")) {
            errors.rejectValue(
                "email",
                 "email.invalido",
                  "El email tiene un formato inválido");
        }

        // Validar si la edad es positiva
        if (cliente.getEdad() != null && cliente.getEdad() <= 0) {
            errors.rejectValue(
                "edad",
                 "edad.invalida",
                  "La edad debe ser mayor que 0");
        }
    }
}
