package com.paul.software2.springboot.app.backend.controllers;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.paul.software2.springboot.app.backend.entities.Venta;
import com.paul.software2.springboot.app.backend.services.VentaService;

@RestController
@RequestMapping("/api/ventas")
public class VentaController {

    @Autowired
    private VentaService ventaService;

    @GetMapping
    public List<Venta> listar() {
        return ventaService.listarTodo();
    }

    @GetMapping("/page/{page}")
    public ResponseEntity<?> listarPageable(@PathVariable Integer page) {
        Pageable pageable = PageRequest.of(page, 4);
        Page<Venta> venta = ventaService.paginarTodo(pageable);
        return ResponseEntity.ok(venta);
    }

    @GetMapping("{id}")
    public ResponseEntity<?> ver(@PathVariable Long id) {
        Optional<Venta> vOptional = ventaService.buscarPorId(id);
        if (vOptional.isPresent()) {
            return ResponseEntity.ok(vOptional.orElseThrow());
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping
    public ResponseEntity<String> crear(@RequestBody Venta venta) {
        try {
            ventaService.crearVentaConDetalle(venta);
            Long id = venta.getId();
            return ResponseEntity.status(HttpStatus.CREATED).body("Venta con ID: " + id + " creada exitosamente");
        } catch (RuntimeException ex) {
            // Maneja excepciones como stock insuficiente
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
        }
    }

    @DeleteMapping("{id}")
    public ResponseEntity<?> eliminar(@PathVariable Long id) {
        Optional<Venta> vOptional = ventaService.eliminar(id);
        if (vOptional.isPresent()) {
            return ResponseEntity.ok(vOptional.orElseThrow());
        }
        return ResponseEntity.notFound().build();
    }
}
