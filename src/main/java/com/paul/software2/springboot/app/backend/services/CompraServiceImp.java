package com.paul.software2.springboot.app.backend.services;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.paul.software2.springboot.app.backend.entities.Compra;
import com.paul.software2.springboot.app.backend.entities.DetalleAlmacen;
import com.paul.software2.springboot.app.backend.entities.DetalleAlmacenId;
import com.paul.software2.springboot.app.backend.entities.DetalleCompra;
import com.paul.software2.springboot.app.backend.entities.Medicamento;
import com.paul.software2.springboot.app.backend.repositories.CompraRepository;
import com.paul.software2.springboot.app.backend.repositories.DetalleAlmacenRepository;
import com.paul.software2.springboot.app.backend.repositories.MedicamentoRepository;

@Service
public class CompraServiceImp implements CompraService {

    @Autowired
    private CompraRepository compraRepository;

    @Autowired
    private MedicamentoRepository medicamentoRepository;

    @Autowired
    private DetalleAlmacenRepository detalleAlmacenRepository;

    @Override
    public List<Compra> listarTodo() {
        return (List<Compra>) compraRepository.findAll();
    }

    @Override
    public Page<Compra> paginarTodo(Pageable pageable) {
        return this.compraRepository.findAll(pageable);

    }

    @Override
    public Optional<Compra> buscarPorId(Long id) {
        return compraRepository.findById(id);
    }

    @Override
    public Compra guardar(Compra compra) {
        return compraRepository.save(compra);
    }

    @Override
    public Compra crearCompraConDetalle(Compra compra) {
        double montoTotal = 0.0;
        int cantidadTotal = 0;

        // Usar un conjunto para verificar duplicados
        Set<Long> medicamentoIds = new HashSet<>();

        for (DetalleCompra detalle : compra.getDetalleCompras()) {
            // Validar si el medicamentoId ya fue procesado
            if (!medicamentoIds.add(detalle.getId().getMedicamentoId())) {//si es true lo agrega pero se niega para que no entre
                throw new IllegalArgumentException(
                        "Error: El medicamento con ID " + detalle.getId().getMedicamentoId()
                                + " está duplicado en la compra.");
            }

            // Buscar el medicamento en la base de datos
            Medicamento medicamento = medicamentoRepository.findById(detalle.getId().getMedicamentoId())
                    .orElseThrow(() -> new RuntimeException(
                            "Medicamento no encontrado con ID: " + detalle.getId().getMedicamentoId()));

            DetalleAlmacenId detalleAlmacenId = new DetalleAlmacenId(2L, medicamento.getId());
            
            DetalleAlmacen detalleAlmacen = detalleAlmacenRepository.findById(detalleAlmacenId)
            .orElseThrow(() -> new RuntimeException("Medicamento con ID: " + medicamento.getId() + " no existe en el almacen secundario, primero registrelo en el almacen e intente agregar los medicamentos"));

            //actualizamos el stock del medicamento agregado
            detalleAlmacen.setStock(detalleAlmacen.getStock() + detalle.getCantidadTipo());
            
            // Establecer la relación y calcular el monto por detalle
            detalle.setCompra(compra);
            detalle.setMedicamento(medicamento);
            
            double montoTipo = medicamento.getPrecio() * detalle.getCantidadTipo();
            detalle.setMontoTipo(montoTipo);

            // Actualizar los totales de la compra
            montoTotal += montoTipo;
            cantidadTotal += detalle.getCantidadTipo();
        }

        // Establecer los valores calculados en la compra
        compra.setMontoTotal(montoTotal);
        compra.setCantidadTotal(cantidadTotal);

        // Persistir la compra junto con los detalles
        return compraRepository.save(compra);
    }

    @Override
    public Optional<Compra> eliminar(Long id) {
        Optional<Compra> cOptional = compraRepository.findById(id);
        cOptional.ifPresent(cDB -> {
            compraRepository.delete(cDB);
        });
        return cOptional;
    }
}
