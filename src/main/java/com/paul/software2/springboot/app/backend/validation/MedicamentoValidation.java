package com.paul.software2.springboot.app.backend.validation;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.paul.software2.springboot.app.backend.entities.Medicamento;

@Component
public class MedicamentoValidation implements Validator {

    @Override
    public boolean supports(Class<?> clazz) {
        // Indica si esta clase es compatible con la validación de Medicamento
        return Medicamento.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        Medicamento medicamento = (Medicamento) target;

        // Validación de nombre: No debe ser vacío ni tener menos de 3 caracteres
        if (medicamento.getNombre() == null || medicamento.getNombre().trim().isEmpty()) {
            errors.rejectValue("nombre", "nombre.empty", "El nombre del medicamento no puede estar vacío.");
        } else if (medicamento.getNombre().length() < 3) {
            errors.rejectValue("nombre", "nombre.short", "El nombre del medicamento debe tener al menos 3 caracteres.");
        }

        // Validación de precio: No puede ser nulo ni negativo
        if (medicamento.getPrecio() == null) {
            errors.rejectValue("precio", "precio.null", "El precio no puede ser nulo.");
        } else if (medicamento.getPrecio() < 0) {
            errors.rejectValue("precio", "precio.negative", "El precio no puede ser negativo.");
        }
    }
}
