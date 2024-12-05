package com.paul.software2.springboot.app.backend.services;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.paul.software2.springboot.app.backend.dto.CrearDetalleVentaDto;
import com.paul.software2.springboot.app.backend.dto.CrearVentaDto;
import com.paul.software2.springboot.app.backend.dto.DetalleVentaDto;
import com.paul.software2.springboot.app.backend.dto.VentaDto;
import com.paul.software2.springboot.app.backend.entities.Cliente;
import com.paul.software2.springboot.app.backend.entities.DetalleAlmacen;
import com.paul.software2.springboot.app.backend.entities.DetalleAlmacenId;
import com.paul.software2.springboot.app.backend.entities.DetalleVenta;
import com.paul.software2.springboot.app.backend.entities.DetalleVentaId;
import com.paul.software2.springboot.app.backend.entities.Medicamento;
import com.paul.software2.springboot.app.backend.entities.Usuario;
import com.paul.software2.springboot.app.backend.entities.Venta;
import com.paul.software2.springboot.app.backend.repositories.ClienteRepository;
import com.paul.software2.springboot.app.backend.repositories.DetalleAlmacenRepository;
import com.paul.software2.springboot.app.backend.repositories.MedicamentoRepository;
import com.paul.software2.springboot.app.backend.repositories.UsuarioRepository;
import com.paul.software2.springboot.app.backend.repositories.VentaRepository;

@Service
public class VentaServiceImp implements VentaService {

    @Autowired
    private VentaRepository ventaRepository;

    @Autowired
    private MedicamentoRepository medicamentoRepository; // Asegúrate de inyectar el repositorio para obtener los
                                                         // medicamentos

    @Autowired
    private DetalleAlmacenRepository detalleAlmacenRepository;

    @Autowired // probando
    private ClienteRepository clienteRepository;

    @Autowired // probando
    private UsuarioRepository usuarioRepository;

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

    @Transactional
    @Override
    public Venta guardar(Venta venta) {
        return ventaRepository.save(venta);
    }

    @Transactional
public Venta crearVentaConDetalle(CrearVentaDto crearVentaDTO) {
    Venta venta = new Venta();
    double montoTotal = 0.0;
    int cantidadTotal = 0;

    // Asignar cliente y usuario
    Cliente cliente = clienteRepository.findById(crearVentaDTO.getClienteId())
            .orElseThrow(() -> new RuntimeException("Cliente no encontrado"));
    Usuario usuario = usuarioRepository.findById(crearVentaDTO.getUsuarioId())
            .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

    venta.setCliente(cliente);
    venta.setUsuario(usuario);

    // Lista para verificar duplicados
    Set<Long> medicamentosProcesados = new HashSet<>();

    // Procesar detalles de la venta
    if (crearVentaDTO.getDetalleVentas() != null) {
        for (CrearDetalleVentaDto detalleDTO : crearVentaDTO.getDetalleVentas()) {
            Long medicamentoId = detalleDTO.getMedicamentoId();

            // Verificar duplicado
            if (medicamentosProcesados.contains(medicamentoId)) {
                throw new RuntimeException(
                        "El medicamento con ID: " + medicamentoId + " ya está incluido en la venta");
            }

            Medicamento medicamento = medicamentoRepository.findById(medicamentoId)
                    .orElseThrow(() -> new RuntimeException(
                            "El medicamento con ID: " + medicamentoId + " no está registrado en la base de datos"));

            // Buscar stock en el almacén 1
            DetalleAlmacenId detalleAlmacenId1 = new DetalleAlmacenId(1L, medicamentoId);
            DetalleAlmacen detalleAlmacen1 = detalleAlmacenRepository.findById(detalleAlmacenId1)
                    .orElseThrow(() -> new RuntimeException(
                            "El medicamento " + medicamento.getNombre() + " no está disponible para la venta en el almacen 1"));

            if (detalleAlmacen1.getStock() < detalleDTO.getCantidadTipo()) {
                throw new RuntimeException("Stock insuficiente para el medicamento " + medicamento.getNombre() + " con ID: " + medicamentoId);
            }

            // Actualizar stock en el almacén 1
            detalleAlmacen1.setStock(detalleAlmacen1.getStock() - detalleDTO.getCantidadTipo());
            detalleAlmacenRepository.save(detalleAlmacen1);

            // Reabastecer si el stock baja de 20
            if (detalleAlmacen1.getStock() < 20) {
                reabastecerAlmacen(detalleAlmacen1, medicamentoId);
            }

            // Crear detalle de venta
            DetalleVenta detalle = new DetalleVenta();
            detalle.setId(new DetalleVentaId(venta.getId(), medicamentoId)); // Crear ID compuesto
            detalle.setMedicamento(medicamento);
            detalle.setVenta(venta);
            detalle.setCantidadTipo(detalleDTO.getCantidadTipo());
            detalle.setMontoTipo(medicamento.getPrecio() * detalleDTO.getCantidadTipo());

            venta.getDetalleVentas().add(detalle);

            // Agregar a la lista de medicamentos procesados
            medicamentosProcesados.add(medicamentoId);

            montoTotal += detalle.getMontoTipo();
            cantidadTotal += detalleDTO.getCantidadTipo();
        }
    }

    // Establecer totales
    venta.setMontoTotal(montoTotal);
    venta.setCantidadTotal(cantidadTotal);

    // Guardar venta
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

    // --------------------------------probando------------------------
    public VentaDto convertirAVentaDTO(Venta venta) {
        List<DetalleVentaDto> detallesDTO = venta.getDetalleVentas().stream()
                .map(detalle -> new DetalleVentaDto(
                        detalle.getMedicamento().getNombre(),
                        detalle.getMedicamento().getPrecio(),
                        detalle.getCantidadTipo(),
                        detalle.getMontoTipo()))
                .toList();

        return new VentaDto(
                venta.getId(),
                venta.getMontoTotal(),
                venta.getCantidadTotal(),
                venta.getFechaVenta(),
                venta.getCliente().getNombre(),
                venta.getUsuario().getNombre(),
                detallesDTO);

    }

    private void reabastecerAlmacen(DetalleAlmacen detalleAlmacen1, Long medicamentoId) {
        // Buscar stock en el almacén 2
        DetalleAlmacenId detalleAlmacenId2 = new DetalleAlmacenId(2L, medicamentoId);
        DetalleAlmacen detalleAlmacen2 = detalleAlmacenRepository.findById(detalleAlmacenId2)
                .orElseThrow(() -> new RuntimeException(
                        "No hay stock disponible para el medicamento con ID: " + medicamentoId + " en el almacen 2"));
    
        // Determinar la cantidad a transferir (50 unidades)
        int cantidadReabastecer = 50;
    
        // Verificar si hay suficiente stock en el almacén 2
        if (detalleAlmacen2.getStock() >= cantidadReabastecer) {
            // Transferir 50 unidades del almacén 2 al almacén 1
            detalleAlmacen1.setStock(detalleAlmacen1.getStock() + cantidadReabastecer);
            detalleAlmacen2.setStock(detalleAlmacen2.getStock() - cantidadReabastecer);
    
            detalleAlmacenRepository.save(detalleAlmacen1);
            detalleAlmacenRepository.save(detalleAlmacen2);
        } else {
            // Si no hay suficiente stock en el almacén 2
            detalleAlmacen1.setEstado("PENDIENTE");
            detalleAlmacenRepository.save(detalleAlmacen1);
        }
    }
    
    

}
