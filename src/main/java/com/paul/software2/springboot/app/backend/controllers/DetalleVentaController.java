package com.paul.software2.springboot.app.backend.controllers;

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

import com.paul.software2.springboot.app.backend.entities.DetalleVenta;
import com.paul.software2.springboot.app.backend.entities.DetalleVentaId;
import com.paul.software2.springboot.app.backend.entities.Medicamento;
import com.paul.software2.springboot.app.backend.entities.Venta;
import com.paul.software2.springboot.app.backend.services.DetalleVentaService;

@RestController
@RequestMapping("/api/detalles_ventas")
public class DetalleVentaController {

    @Autowired
    private DetalleVentaService detallesService;

    @GetMapping
    public ResponseEntity<Iterable<DetalleVenta>> listarTodos() {
        return ResponseEntity.ok(detallesService.listarTodos());
    }

    @GetMapping("/{ventaId}/{medicamentoId}")
    public ResponseEntity<DetalleVenta> buscarPorId(
            @PathVariable Long ventaId,
            @PathVariable Long medicamentoId) {
        DetalleVentaId id = new DetalleVentaId(ventaId, medicamentoId);
        Optional<DetalleVenta> detalleVenta = detallesService.buscarPorId(id);
        return detalleVenta.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<DetalleVenta> crear(@RequestBody DetalleVenta detalleVenta) {
        if (detalleVenta.getId() == null) {
            DetalleVentaId id = new DetalleVentaId();
            id.setMedicamentoId(detalleVenta.getMedicamento().getId());
            id.setVentaId(detalleVenta.getVenta().getId());
            detalleVenta.setId(id);
        }
        DetalleVenta nuevoDetalle = detallesService.guardar(detalleVenta);
        return ResponseEntity.status(HttpStatus.CREATED).body(nuevoDetalle);
    }

    @PutMapping("/{ventaId}/{medicamentoId}")
    public ResponseEntity<DetalleVenta> actualizar(
            @PathVariable Long ventaId,
            @PathVariable Long medicamentoId,
            @RequestBody DetalleVenta detalleVenta) {
        DetalleVentaId id = new DetalleVentaId(ventaId, medicamentoId);
        if (!detallesService.buscarPorId(id).isPresent()) {
            return ResponseEntity.notFound().build();
        }
        detalleVenta.setVenta(new Venta(ventaId));
        detalleVenta.setMedicamento(new Medicamento(medicamentoId));
        DetalleVenta detalleActualizado = detallesService.guardar(detalleVenta);
        return ResponseEntity.ok(detalleActualizado);
    }

    @DeleteMapping("/{ventaId}/{medicamentoId}")
    public ResponseEntity<Void> eliminarPorId(
            @PathVariable Long ventaId,
            @PathVariable Long medicamentoId) {
        DetalleVentaId id = new DetalleVentaId(ventaId, medicamentoId);
        if (!detallesService.buscarPorId(id).isPresent()) {
            return ResponseEntity.notFound().build();
        }
        detallesService.eliminarPorId(id);
        return ResponseEntity.noContent().build();
    }
}
