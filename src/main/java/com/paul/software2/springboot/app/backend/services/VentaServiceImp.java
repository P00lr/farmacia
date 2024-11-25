package com.paul.software2.springboot.app.backend.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.paul.software2.springboot.app.backend.entities.DetalleAlmacen;
import com.paul.software2.springboot.app.backend.entities.DetalleAlmacenId;
import com.paul.software2.springboot.app.backend.entities.DetalleVenta;
import com.paul.software2.springboot.app.backend.entities.Medicamento;
import com.paul.software2.springboot.app.backend.entities.Venta;
import com.paul.software2.springboot.app.backend.repositories.DetalleAlmacenRepository;
import com.paul.software2.springboot.app.backend.repositories.MedicamentoRepository;
import com.paul.software2.springboot.app.backend.repositories.VentaRepository;

@Service
public class VentaServiceImp implements VentaService{

    @Autowired
    private VentaRepository ventaRepository;
    
    @Autowired
    private MedicamentoRepository medicamentoRepository; // Asegúrate de inyectar el repositorio para obtener los medicamentos

    @Autowired
    private DetalleAlmacenRepository detalleAlmacenRepository;

    @Override
    public List<Venta> listarTodo() {
        return (List<Venta>) ventaRepository.findAll();
    }
    @Override
    public Page<Venta> paginarTodo(Pageable pageable) {
        return this.ventaRepository.findAll(pageable);

    }
    @Override
    public Optional<Venta> buscarPorId(Long id) {
        return ventaRepository.findById(id);
    }

    @Override
    public Venta guardar(Venta venta) { 
        return ventaRepository.save(venta);
    }

    public Venta crearVentaConDetalle(Venta venta) {
    double montoTotal = 0.0;
    int cantidadTotal = 0;

    if (venta.getDetalleVentas() != null) {
        for (DetalleVenta detalle : venta.getDetalleVentas()) {
            Long medicamentoId = detalle.getMedicamento().getId();

            // Recupera el Medicamento de forma directa
            Medicamento medicamento = medicamentoRepository
                .findById(medicamentoId)
                .orElseThrow(() -> new RuntimeException("Medicamento no encontrado con ID: " + medicamentoId));

            // Verifica el stock disponible del medicamento en el almacén
            DetalleAlmacenId detalleAlmacenId = new DetalleAlmacenId(venta.getUsuario().getId(), medicamentoId);
            DetalleAlmacen detalleAlmacen = detalleAlmacenRepository
                .findById(detalleAlmacenId)
                 .orElseThrow(() -> new RuntimeException("Stock insuficiente para el medicamento " + medicamento.getNombre() + " con ID: " + medicamentoId));

            // Validar que hay suficiente stock
            if (detalleAlmacen.getStock() < detalle.getCantidadTipo()) {
                throw new RuntimeException(
                    "Stock insuficiente para el medicamento con ID: " + medicamentoId +
                    ". Stock actual: " + detalleAlmacen.getStock()
                );
            }

            // Resta el stock consumido
            detalleAlmacen.setStock(detalleAlmacen.getStock() - detalle.getCantidadTipo());
            detalleAlmacenRepository.save(detalleAlmacen);

            // Calcula montoTipo
            double montoTipo = medicamento.getPrecio() * detalle.getCantidadTipo();
            detalle.setMontoTipo(montoTipo); // Asigna el monto calculado

            // Acumula los totales para la venta
            montoTotal += montoTipo;
            cantidadTotal += detalle.getCantidadTipo();

            // Asocia el medicamento y la venta al detalle
            detalle.setMedicamento(medicamento);
            detalle.setVenta(venta);
        }
    }

    // Asigna los totales calculados a la venta
    venta.setMontoTotal(montoTotal);
    venta.setCantidadTotal(cantidadTotal);

    // Guarda la venta
    return guardar(venta);
}

    @Override
    public Optional<Venta> eliminar(Long id) {
        Optional<Venta> vOptional = ventaRepository.findById(id);
        vOptional.ifPresent(vDB -> {
            ventaRepository.delete(vDB);
        });
        return vOptional;
    }

    
}
