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

import com.paul.software2.springboot.app.backend.entities.Almacen;
import com.paul.software2.springboot.app.backend.entities.DetalleAlmacen;
import com.paul.software2.springboot.app.backend.services.AlmacenService;
import com.paul.software2.springboot.app.backend.validation.AlmacenValidation;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/almacenes")
public class AlmacenController {

    @Autowired
    private AlmacenService almacenService;

    @Autowired
    private AlmacenValidation validation;

    @GetMapping
    public List<Almacen> listar() {
        return almacenService.listarTodo();
    }

    @GetMapping("/page/{page}")
    public ResponseEntity<?> listarPageable(@PathVariable Integer page) {
        Pageable pageable = PageRequest.of(page, 4);
        Page<Almacen> almacen = almacenService.paginarTodo(pageable);
        return ResponseEntity.ok(almacen);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> ver(@PathVariable Long id) {
        Optional<Almacen> aOptional = almacenService.buscarPorId(id);
        if (aOptional.isPresent()) {
            return ResponseEntity.ok(aOptional.orElseThrow());
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping
public ResponseEntity<?> crear(@Valid @RequestBody Almacen almacen, BindingResult result) {
    // Validar los campos del almacen
    validation.validate(almacen, result);

    if (result.hasErrors()) {
        // Si hay errores de validación, devolverlos al cliente
        return validation(result);
    }

    try {
        // Intentar guardar el almacen
        Almacen almacenCreado = almacenService.guardar(almacen);
        return ResponseEntity.status(HttpStatus.CREATED).body(almacenCreado);
    } catch (RuntimeException ex) {
        // Capturar y devolver el mensaje de excepción como respuesta clara
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of(
            "error", ex.getMessage()
        ));
    }
}


    @PutMapping("/{id}")
    public ResponseEntity<?> actualizar(@PathVariable Long id, @Valid @RequestBody Almacen almacen,
            BindingResult result) {

        validation.validate(almacen, result);

        if (result.hasErrors()) {
            return validation(result);
        }

        Optional<Almacen> almacenOptional = almacenService.actualizar(id, almacen);

        if (almacenOptional.isPresent()) {
            return ResponseEntity.status(HttpStatus.OK).body(almacenOptional.get());
        }

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Almacen no encontrado");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminar(@PathVariable Long id) {
        Optional<Almacen> pOptional = almacenService.eliminar(id);
        if (pOptional.isPresent()) {
            return ResponseEntity.ok(pOptional.orElseThrow());
        }
        return ResponseEntity.notFound().build();
    }

    //prueba agrear medicamento
    @PostMapping("/agregar-medicamento")
    public ResponseEntity<?> agregarMedicamento(@RequestBody DetalleAlmacen detalleAlmacen) {
        try {
            // Lógica de negocio para agregar o actualizar el medicamento
            almacenService.agregarMedicamento(detalleAlmacen);

            return ResponseEntity.ok("Medicamento agregado al almacén exitosamente");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error: " + e.getMessage());
        }
    }

    private ResponseEntity<Map<String, String>> validation(BindingResult result) {
        Map<String, String> errors = new HashMap<>();
        result.getFieldErrors().forEach(error -> {
            errors.put(error.getField(), error.getDefaultMessage());
        });
        return ResponseEntity.badRequest().body(errors);
    }
}
