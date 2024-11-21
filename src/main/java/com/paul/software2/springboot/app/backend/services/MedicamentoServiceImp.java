package com.paul.software2.springboot.app.backend.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.paul.software2.springboot.app.backend.entities.Medicamento;
import com.paul.software2.springboot.app.backend.repositories.MedicamentoRepository;

@Service
public class MedicamentoServiceImp implements MedicamentoService{
    
    @Autowired
    private MedicamentoRepository medicamentoRepository;

    @Transactional(readOnly = true)
    @Override
    public List<Medicamento> listarTodo() {
        return (List<Medicamento>) medicamentoRepository.findAll();
    }

    @Transactional(readOnly = true)
    @Override
    public Page<Medicamento> paginarTodo(Pageable pageable) {
        return this.medicamentoRepository.findAll(pageable);
    }

    @Transactional(readOnly = true)
    @Override
    public Optional<Medicamento> buscarPorId(Long id) {
        return medicamentoRepository.findById(id);
    }

    @Transactional
    @Override
    public Medicamento guardar(Medicamento medicamento) {
        return medicamentoRepository.save(medicamento);
    }

    
    @Transactional
    @Override
    public Optional<Medicamento> actualizar(Long id, Medicamento medicamento) {
        Optional<Medicamento> productOptional = medicamentoRepository.findById(id);
        if (productOptional.isPresent()) {
            Medicamento medicamentDb = productOptional.orElseThrow();
    
            // Actualizar todos los campos
            medicamentDb.setNombre(medicamento.getNombre());
            medicamentDb.setPrecio(medicamento.getPrecio());
    
            // Guardar los cambios en la base de datos
            return Optional.of(medicamentoRepository.save(medicamentDb));
        }
        return productOptional;
    }

    @Transactional
    @Override
    public Optional<Medicamento> eliminar(Long id) {
        Optional<Medicamento> mOptional = medicamentoRepository.findById(id);
        mOptional.ifPresent(medDB -> {
            medicamentoRepository.delete(medDB);
        });
        return mOptional;
    }
}
