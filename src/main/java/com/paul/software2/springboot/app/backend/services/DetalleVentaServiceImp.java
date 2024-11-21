package com.paul.software2.springboot.app.backend.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.paul.software2.springboot.app.backend.entities.DetalleVenta;
import com.paul.software2.springboot.app.backend.entities.DetalleVentaId;
import com.paul.software2.springboot.app.backend.repositories.DetalleVentaRepository;

@Service
public class DetalleVentaServiceImp implements DetalleVentaService{
    @Autowired
    private DetalleVentaRepository repository;

    @Override
    public Iterable<DetalleVenta> listarTodos() {
        return repository.findAll();
    }

    @Override
    public Optional<DetalleVenta> buscarPorId(DetalleVentaId id) {
        return repository.findById(id);
    }

    @Override
    public DetalleVenta guardar(DetalleVenta detalleVenta) {
        return repository.save(detalleVenta);
    }

    @Override
    public void eliminarPorId(DetalleVentaId id) {
        repository.deleteById(id);
    }
}
