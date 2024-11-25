package com.paul.software2.springboot.app.backend.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.paul.software2.springboot.app.backend.entities.Almacen;
import com.paul.software2.springboot.app.backend.entities.DetalleAlmacen;
import com.paul.software2.springboot.app.backend.entities.DetalleAlmacenId;
import com.paul.software2.springboot.app.backend.entities.Medicamento;
import com.paul.software2.springboot.app.backend.repositories.AlmacenRepository;
import com.paul.software2.springboot.app.backend.repositories.DetalleAlmacenRepository;
import com.paul.software2.springboot.app.backend.repositories.MedicamentoRepository;

@Service
public class AlmacenServiceImp  implements AlmacenService{

    @Autowired
    private AlmacenRepository almacenRepository;

    @Autowired
    private MedicamentoRepository medicamentoRepository;

    //prueba agregar medicamento
     @Autowired
    private DetalleAlmacenRepository detalleRepository;

    @Transactional(readOnly = true)
    @Override
    public List<Almacen> listarTodo() {
        return (List<Almacen>) almacenRepository.findAll();
    }

    @Transactional(readOnly = true)
    @Override
    public Page<Almacen> paginarTodo(Pageable pageable) {
        return this.almacenRepository.findAll(pageable);
    }

    @Transactional(readOnly = true)
    @Override
    public Optional<Almacen> buscarPorId(Long id) {
        return almacenRepository.findById(id);
    }

    @Transactional
    @Override
    public Almacen guardar(Almacen almacen) {
        return almacenRepository.save(almacen);
    }

    
    @Transactional
    @Override
    public Optional<Almacen> actualizar(Long id, Almacen almacen) {
        Optional<Almacen> productOptional = almacenRepository.findById(id);
        if (productOptional.isPresent()) {
            Almacen almacenDB = productOptional.orElseThrow();
    
            // Actualizar todos los campos
            almacenDB.setNombre(almacen.getNombre());
            almacenDB.setDireccion(almacen.getDireccion());
    
            // Guardar los cambios en la base de datos
            return Optional.of(almacenRepository.save(almacenDB));
        }
        return productOptional;
    }

    @Transactional
    @Override
    public Optional<Almacen> eliminar(Long id) {
        Optional<Almacen> aOptional = almacenRepository.findById(id);
        aOptional.ifPresent(alDB -> {
            almacenRepository.delete(alDB);
        });
        return aOptional;
    }

    //prueba
    @Transactional
    public void agregarMedicamento(DetalleAlmacen detalleAlmacen) {
        DetalleAlmacenId id = detalleAlmacen.getId();
    
        // Validar el ID compuesto (almacenId y medicamentoId)
        if (id.getAlmacenId() == null || id.getMedicamentoId() == null) {
            throw new RuntimeException("El ID compuesto debe contener 'almacenId' y 'medicamentoId'.");
        }
    
        // Verificar si el registro ya existe en la base de datos
        Optional<DetalleAlmacen> detalleOpt = detalleRepository.findById(id);
    
        DetalleAlmacen detalle;
        if (detalleOpt.isPresent()) {
            // Si ya existe, incrementar el stock
            detalle = detalleOpt.get();
            detalle.setStock(detalle.getStock() + detalleAlmacen.getStock());
        } else {
            // Si no existe, crear un nuevo registro
            detalle = new DetalleAlmacen();
            detalle.setId(id);
            detalle.setStock(detalleAlmacen.getStock());
    
            // Cargar las entidades relacionadas
            Almacen almacen = almacenRepository.findById(id.getAlmacenId())
                .orElseThrow(() -> new RuntimeException("Almacén no encontrado con ID: " + id.getAlmacenId()));
            Medicamento medicamento = medicamentoRepository.findById(id.getMedicamentoId())
                .orElseThrow(() -> new RuntimeException("Medicamento no encontrado con ID: " + id.getMedicamentoId()));
    
            // Asignar las relaciones
            detalle.setAlmacen(almacen);
            detalle.setMedicamento(medicamento);
        }
    
        // Guardar el detalle
        detalleRepository.save(detalle);
    }
    

}
