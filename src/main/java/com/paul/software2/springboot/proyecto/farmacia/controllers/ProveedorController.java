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

import com.paul.software2.springboot.proyecto.farmacia.entities.Proveedor;
import com.paul.software2.springboot.proyecto.farmacia.services.ProveedorService;

@RestController
@RequestMapping("/proveedores")
public class ProveedorController {

    @Autowired
    private ProveedorService service;

    @GetMapping
    public List<Proveedor> listar() {
        return service.findAll();
    }

    @GetMapping("/{id}")    
    public ResponseEntity<?> ver(@PathVariable Long id) {
        Optional<Proveedor> pOptional = service.findById(id);
        if(pOptional.isPresent()) {
            return ResponseEntity.ok(pOptional.orElseThrow());
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping
    public ResponseEntity<Proveedor> crear(@RequestBody Proveedor proveedor) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.save(proveedor));
    }   

    @PutMapping("/{id}")
    public ResponseEntity<Proveedor> actualizar(@PathVariable Long id, @RequestBody Proveedor proveedor) {
        proveedor.setId(id);;
        return ResponseEntity.status(HttpStatus.CREATED).body(service.save(proveedor));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminar(@PathVariable Long id) {
        Proveedor proveedor = new Proveedor();
        proveedor.setId(id);
        Optional<Proveedor> pOptional = service.delete(proveedor);
        if(pOptional.isPresent()) {
            return ResponseEntity.ok(pOptional.orElseThrow());
        }
        return ResponseEntity.notFound().build();
    }  
}
