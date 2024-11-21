package com.paul.software2.springboot.app.backend.services;

import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.paul.software2.springboot.app.backend.entities.Cliente;
import com.paul.software2.springboot.app.backend.repositories.ClienteRepository;

@Service
public class ClienteServiceImp implements ClienteService{

    @Autowired
    private ClienteRepository clienteRepository;

    @Transactional(readOnly = true)
    @Override
    public List<Cliente> listarTodo() {
        return (List<Cliente>) clienteRepository.findAll();
    }

    @Transactional(readOnly = true)
    @Override
    public Page<Cliente> paginarTodo(Pageable pageable) {
        return this.clienteRepository.findAll(pageable);
    }

    @Transactional(readOnly = true)
    @Override
    public Optional<Cliente> buscarPorId(Long id) {
        return clienteRepository.findById(id);
    }

    @Transactional
    @Override
    public Cliente guardar(Cliente cliente) {
        return clienteRepository.save(cliente);
    }

    
    @Transactional
    @Override
    public Optional<Cliente> actualizar(Long id, Cliente cliente) {
        Optional<Cliente> productOptional = clienteRepository.findById(id);
        if (productOptional.isPresent()) {
            Cliente medicamentDb = productOptional.orElseThrow();
    
            // Actualizar todos los campos
            medicamentDb.setNombre(cliente.getNombre());
            medicamentDb.setPaterno(cliente.getPaterno());
            medicamentDb.setTelefono(cliente.getTelefono());
            medicamentDb.setEmail(cliente.getEmail());
            medicamentDb.setGenero(cliente.getGenero());
            medicamentDb.setEdad(cliente.getEdad());
    
            // Guardar los cambios en la base de datos
            return Optional.of(clienteRepository.save(medicamentDb));
        }
        return productOptional;
    }

    @Transactional
    @Override
    public Optional<Cliente> eliminar(Long id) {
        Optional<Cliente> cOptional = clienteRepository.findById(id);
        cOptional.ifPresent(cliDB -> {
            clienteRepository.delete(cliDB);
        });
        return cOptional;
    }


   

}
