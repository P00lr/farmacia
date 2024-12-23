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

import com.paul.software2.springboot.app.backend.entities.Proveedor;
import com.paul.software2.springboot.app.backend.services.ProveedorService;
import com.paul.software2.springboot.app.backend.validation.ProveedorValidation;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/proveedores")
public class ProveedorController {

    @Autowired
    private ProveedorService proveedorService;

    @Autowired
    private ProveedorValidation validation;

    @GetMapping
    public List<Proveedor> listar() {
        return proveedorService.listarTodo();
    }

    @GetMapping("/page/{page}")
    public ResponseEntity<?> listarPageable(@PathVariable Integer page) {
        Pageable pageable = PageRequest.of(page, 4);
        Page<Proveedor> proveedor = proveedorService.paginarTodo(pageable);
        return ResponseEntity.ok(proveedor);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> ver(@PathVariable Long id) {
        Optional<Proveedor> pOptional = proveedorService.buscarPorId(id);
        if (pOptional.isPresent()) {
            return ResponseEntity.ok(pOptional.orElseThrow());
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping
    public ResponseEntity<?> crear(@Valid @RequestBody Proveedor proveedor, BindingResult result) {
        validation.validate(proveedor, result);
    
        if (result.hasErrors()) {
            return validation(result); 
        }
    
        try {
            // Intenta guardar el proveedor
            Proveedor proveedorCreado = proveedorService.guardar(proveedor);
            return ResponseEntity.status(HttpStatus.CREATED).body(proveedorCreado);
        } catch (RuntimeException ex) {
            // Captura excepciones de negocio y devuelve un mensaje amigable
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of(
                "error", ex.getMessage()
            ));
        }
    }
    

    @PutMapping("/{id}")
    public ResponseEntity<?> actualizar(@PathVariable Long id, @Valid @RequestBody Proveedor proveedor,
            BindingResult result) {
    
        validation.validate(proveedor, result);

        if (result.hasErrors()) {
            return validation(result); 
        }

        Optional<Proveedor> clienteOptional = proveedorService.actualizar(id, proveedor);

        if (clienteOptional.isPresent()) {
            return ResponseEntity.status(HttpStatus.OK).body(clienteOptional.get());
        }

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Proveedor no encontrado");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminar(@PathVariable Long id) {
        Optional<Proveedor> pOptional = proveedorService.eliminar(id);
        if (pOptional.isPresent()) {
            return ResponseEntity.ok(pOptional.orElseThrow());
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
