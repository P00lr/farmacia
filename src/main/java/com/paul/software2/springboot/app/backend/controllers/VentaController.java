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
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.paul.software2.springboot.app.backend.dto.CrearVentaDto;
import com.paul.software2.springboot.app.backend.dto.VentaDto;
import com.paul.software2.springboot.app.backend.entities.Venta;
import com.paul.software2.springboot.app.backend.services.VentaService;

@RestController
@RequestMapping("/api/ventas")
public class VentaController {

    @Autowired
    private VentaService ventaService;

    @GetMapping
    public ResponseEntity<List<VentaDto>> listar() {
        List<VentaDto> ventasDTO = ventaService.listarTodo()
                .stream()
                .map(ventaService::convertirAVentaDTO) // Llamada al método que convierte la entidad a DTO
                .toList();
        return ResponseEntity.ok(ventasDTO);
    }

    // Paginación esta en formato DTO
    @GetMapping("/page/{page}")
    public ResponseEntity<Page<VentaDto>> listarPageable(@PathVariable Integer page) {
        Pageable pageable = PageRequest.of(page, 4);
        Page<VentaDto> ventasDTO = ventaService.paginarTodo(pageable)
                .map(ventaService::convertirAVentaDTO); // Llamada al método que convierte la entidad a DTO
        return ResponseEntity.ok(ventasDTO);
    }

    // Ver detalles de una venta específica en formato DTO
    @GetMapping("/{id}")
    public ResponseEntity<VentaDto> ver(@PathVariable Long id) {
        Optional<Venta> ventaOptional = ventaService.buscarPorId(id);
        if (ventaOptional.isPresent()) {
            VentaDto ventaDTO = ventaService.convertirAVentaDTO(ventaOptional.get());
            return ResponseEntity.ok(ventaDTO);
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping
    public ResponseEntity<?> crearVenta(@RequestBody CrearVentaDto crearVentaDTO) {
        try {
            Venta ventaCreada = ventaService.crearVentaConDetalle(crearVentaDTO);
            VentaDto ventaDTO = ventaService.convertirAVentaDTO(ventaCreada); // Transformar a DTO de salida
            return ResponseEntity.status(HttpStatus.CREATED).body(ventaDTO);
        } catch (RuntimeException ex) {
            // Captura de excepciones personalizadas desde el Service
            Map<String, String> response = new HashMap<>();
            response.put("error", ex.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        } catch (Exception ex) {
            // Captura de errores generales
            Map<String, String> response = new HashMap<>();
            response.put("error", "Ocurrió un error inesperado: " + ex.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
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
