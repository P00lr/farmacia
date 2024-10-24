package com.paul.software2.springboot.proyecto.farmacia.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.paul.software2.springboot.proyecto.farmacia.entities.Medicamento;
import com.paul.software2.springboot.proyecto.farmacia.repositories.MedicamentoRepository;

@Service
public class MedicamentoServiceImp  implements MedicamentoService{

    @Autowired
    private MedicamentoRepository repository;

    @Override
    @Transactional(readOnly = true)
    public List<Medicamento> findAll() {
        return (List<Medicamento>) repository.findAll(); 
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Medicamento> findById(Long id) {
        return repository.findById(id);
    }

    @Override
    public Medicamento save(Medicamento medicamento) {
        return repository.save(medicamento);
    }

    @Override
    public Optional<Medicamento> delete(Medicamento medicamento) {
        Optional<Medicamento> meOptional = repository.findById(medicamento.getId());
        meOptional.ifPresent(medDb->{
            repository.delete(medDb);
        });
        return meOptional;
    }
    
}
