package com.paul.software2.springboot.proyecto.farmacia.controllers;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.paul.software2.springboot.proyecto.farmacia.entities.Medicamento;
import com.paul.software2.springboot.proyecto.farmacia.services.MedicamentoService;

@RestController
@RequestMapping("/medicamentos")
public class MedicamentoController {
    
    @Autowired
    private MedicamentoService service;

    @GetMapping
    public List<Medicamento> listar() {
        return service.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> ver(@PathVariable Long id) {
        Optional<Medicamento> medOptional = service.findById(id);
        if (medOptional.isPresent()) {
            return ResponseEntity.ok(medOptional.orElseThrow());
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping
    public ResponseEntity<Medicamento> crear(@RequestBody Medicamento medicamento) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.save(medicamento));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Medicamento> actualizar(@PathVariable Long id, @RequestBody Medicamento medicamento) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.save(medicamento));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminar(@PathVariable Long id) {
        Medicamento medicamento = new Medicamento();
        medicamento.setId(id);
        Optional<Medicamento> medOptional = service.delete(medicamento);
        if (medOptional.isPresent()) {
            return ResponseEntity.ok(medOptional.orElseThrow());
        }
        return ResponseEntity.notFound().build();
    }
}
