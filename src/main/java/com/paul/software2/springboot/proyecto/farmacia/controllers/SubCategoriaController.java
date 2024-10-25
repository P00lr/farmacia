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

import com.paul.software2.springboot.proyecto.farmacia.entities.SubCategoria;
import com.paul.software2.springboot.proyecto.farmacia.services.SubCategoriaService;

@RestController
@RequestMapping("/subCategorias")
public class SubCategoriaController {

    @Autowired
    private SubCategoriaService service;

    @GetMapping
    public List<SubCategoria> listar() {
        return service.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> ver(@PathVariable Long id) {
        Optional<SubCategoria> sOptional = service.findById(id);
        if(sOptional.isPresent()) {
            return ResponseEntity.ok(sOptional.orElseThrow());
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping
    public ResponseEntity<SubCategoria> crear(@RequestBody SubCategoria subCategoria) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.save(subCategoria));
    }   

    @PutMapping("/{id}")
    public ResponseEntity<SubCategoria> actualizar(@PathVariable Long id, @RequestBody SubCategoria subCategoria) {
        subCategoria.setId(id);;
        return ResponseEntity.status(HttpStatus.CREATED).body(service.save(subCategoria));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminar(@PathVariable Long id) {
        SubCategoria subCategoria = new SubCategoria();
        subCategoria.setId(id);
        Optional<SubCategoria> sOptional = service.delete(subCategoria);
        if(sOptional.isPresent()) {
            return ResponseEntity.ok(sOptional.orElseThrow());
        }
        return ResponseEntity.notFound().build();
    } 
}
