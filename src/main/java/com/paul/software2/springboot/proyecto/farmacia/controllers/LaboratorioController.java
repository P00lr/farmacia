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

import com.paul.software2.springboot.proyecto.farmacia.entities.Laboratorio;
import com.paul.software2.springboot.proyecto.farmacia.services.LaboratorioService;

@RestController
@RequestMapping("/laboratorios")
public class LaboratorioController {
    
    @Autowired
    private LaboratorioService service;

    @GetMapping
    public List<Laboratorio> listar() {
        return service.findAll(); 
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> ver(@PathVariable Long id) {
        Optional<Laboratorio> laborOptional = service.findById(id);
        if(laborOptional.isPresent()) {
            return ResponseEntity.ok(laborOptional.orElseThrow());
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping
    public ResponseEntity<Laboratorio> crear(@RequestBody Laboratorio laboratorio) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.save(laboratorio));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Laboratorio> actualizar(@PathVariable Long id, @RequestBody Laboratorio laboratorio) {
        laboratorio.setId(id);;
        return ResponseEntity.status(HttpStatus.CREATED).body(service.save(laboratorio));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminar(@PathVariable Long id) {
        Laboratorio laboratorio = new Laboratorio();
        laboratorio.setId(id);
        Optional<Laboratorio> labOptional = service.delete(laboratorio);
        if(labOptional.isPresent()) {
            return ResponseEntity.ok(labOptional.orElseThrow());
        }
        return ResponseEntity.notFound().build();
    } 
}
