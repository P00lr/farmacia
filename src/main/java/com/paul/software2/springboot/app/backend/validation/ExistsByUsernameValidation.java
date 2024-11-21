package com.paul.software2.springboot.app.backend.validation;

import org.springframework.beans.factory.annotation.Autowired;

import com.paul.software2.springboot.app.backend.services.UsuarioService;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class ExistsByUsernameValidation implements ConstraintValidator<ExistsByUsername, String> {

    @Autowired
    private UsuarioService usuarioService;

    @Override
    public boolean isValid(String username, ConstraintValidatorContext context) {
        if (usuarioService == null) {
            return true;
        }
        return !usuarioService.existeElUsername(username);
    }
}