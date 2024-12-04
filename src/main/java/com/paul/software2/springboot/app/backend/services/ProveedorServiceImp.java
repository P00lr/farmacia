package com.paul.software2.springboot.app.backend.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.paul.software2.springboot.app.backend.entities.Proveedor;
import com.paul.software2.springboot.app.backend.repositories.ProveedorRepository;


@Service
public class ProveedorServiceImp implements ProveedorService {

    @Autowired
    private ProveedorRepository proveedorRepository;

    @Transactional(readOnly = true)
    @Override
    public List<Proveedor> listarTodo() {
        return (List<Proveedor>) proveedorRepository.findAll();
    }

    @Transactional(readOnly = true)
    @Override
    public Page<Proveedor> paginarTodo(Pageable pageable) {
        return this.proveedorRepository.findAll(pageable);
    }

    @Transactional(readOnly = true)
    @Override
    public Optional<Proveedor> buscarPorId(Long id) {
        return proveedorRepository.findById(id);
    }

    @Transactional
    @Override
    public Proveedor guardar(Proveedor proveedor) {
        if(existeElProveedor(proveedor.getNombre())) {
            throw new RuntimeException("El proveedor con nombre " + proveedor.getNombre() + " ya existe en la base de datos");
        }
        return proveedorRepository.save(proveedor);
    }

    
    @Transactional
    @Override
    public Optional<Proveedor> actualizar(Long id, Proveedor proveedor) {
        Optional<Proveedor> pOptional = proveedorRepository.findById(id);
        if (pOptional.isPresent()) {
            Proveedor proveedorDB = pOptional.orElseThrow();
    
            // Actualizar todos los campos
            proveedorDB.setNombre(proveedorDB.getNombre());
            proveedorDB.setTelefono(proveedorDB.getTelefono());
            proveedorDB.setEmail(proveedorDB.getEmail());
            proveedorDB.setDireccion(proveedorDB.getDireccion());
           
    
            // Guardar los cambios en la base de datos
            return Optional.of(proveedorRepository.save(proveedorDB));
        }
        return pOptional;
    }

    @Transactional
    @Override
    public Optional<Proveedor> eliminar(Long id) {
        Optional<Proveedor> pOptional = proveedorRepository.findById(id);
        pOptional.ifPresent(proDB -> {
            proveedorRepository.delete(proDB);
        });
        return pOptional;
    }

    @Override
    public boolean existeElProveedor(String proveedorNombre){
        return proveedorRepository.existsByNombre(proveedorNombre);
    }
}
