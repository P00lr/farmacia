package com.paul.software2.springboot.app.backend.controllers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.paul.software2.springboot.app.backend.entities.Medicamento;
import com.paul.software2.springboot.app.backend.services.MedicamentoService;
import com.paul.software2.springboot.app.backend.validation.MedicamentoValidation;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/medicamentos")
public class MedicamentoController {

    @Autowired
    private MedicamentoService medicamentoService;

    @Autowired
    private MedicamentoValidation validation;

    @GetMapping
    public List<Medicamento> listar() {
        return medicamentoService.listarTodo();
    }

    @GetMapping("/page/{page}")
    public ResponseEntity<?> listarPageable(@PathVariable Integer page) {
        Pageable pageable = PageRequest.of(page, 4);
        Page<Medicamento> medicamento = medicamentoService.paginarTodo(pageable);
        return ResponseEntity.ok(medicamento);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> ver(@PathVariable Long id) {
        Optional<Medicamento> cOptional = medicamentoService.buscarPorId(id);
        if (cOptional.isPresent()) {
            return ResponseEntity.ok(cOptional.orElseThrow());
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping
    public ResponseEntity<?> crear(@Valid @RequestBody Medicamento medicamento, BindingResult result) {

        validation.validate(medicamento, result);
        if (result.hasErrors()) {
            return validation(result);
        }

        try {
            // Intentar guardar el medicamento
            return ResponseEntity.status(HttpStatus.CREATED).body(medicamentoService.guardar(medicamento));
        } catch (RuntimeException ex) {
            // Capturar excepción de duplicado
            Map<String, String> response = new HashMap<>();
            response.put("error", ex.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        } catch (Exception ex) {
            // Capturar cualquier otra excepción
            Map<String, String> response = new HashMap<>();
            response.put("error", "Ocurrió un error inesperado: " + ex.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> actualizar(@PathVariable Long id, @Valid @RequestBody Medicamento medicamento,
            BindingResult result) {

        validation.validate(medicamento, result);

        if (result.hasErrors()) {
            return validation(result);
        }

        Optional<Medicamento> mOptional = medicamentoService.actualizar(id, medicamento);

        if (mOptional.isPresent()) {
            return ResponseEntity.status(HttpStatus.OK).body(mOptional.get());
        }

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Medicamento no encontrado");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminar(@PathVariable Long id) {
        Optional<Medicamento> mOptional = medicamentoService.eliminar(id);
        if (mOptional.isPresent()) {
            return ResponseEntity.ok(mOptional.orElseThrow());
        }
        return ResponseEntity.notFound().build();
    }

    private ResponseEntity<Map<String, String>> validation(BindingResult result) {
        Map<String, String> errors = new HashMap<>();
        result.getFieldErrors().forEach(error -> {
            errors.put(error.getField(), error.getDefaultMessage());
        });
        return ResponseEntity.badRequest().body(errors);
    }
}
