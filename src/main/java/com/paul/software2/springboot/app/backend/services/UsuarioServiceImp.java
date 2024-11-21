package com.paul.software2.springboot.app.backend.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.paul.software2.springboot.app.backend.entities.Rol;
import com.paul.software2.springboot.app.backend.entities.Usuario;
import com.paul.software2.springboot.app.backend.repositories.RolRepository;
import com.paul.software2.springboot.app.backend.repositories.UsuarioRepository;

@Service
public class UsuarioServiceImp implements UsuarioService{

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private RolRepository rolRepository;

     @Autowired
    private PasswordEncoder passwordEncoder;

    
    @Transactional(readOnly = true)
    @Override
    public List<Usuario> listarTodo() {
        return (List<Usuario>) usuarioRepository.findAll();
    }

    @Transactional(readOnly = true)
    @Override
    public Page<Usuario> paginarTodo(Pageable pageable) {
        return this.usuarioRepository.findAll(pageable);
    } 
    
    @Transactional(readOnly = true)
    @Override
    public Optional<Usuario> buscarPorId(Long id) {
        return usuarioRepository.findById(id);
    }

    @Transactional
    @Override
    public Usuario guardar(Usuario usuario) {
        Optional<Rol> rolUserOptional = rolRepository.findByNombre("ROLE_USER");
        
        List<Rol> roles = new ArrayList<>();

        rolUserOptional.ifPresent(roles::add);

        if (usuario.isAdmin()) {
            Optional<Rol> rolAdOptional = rolRepository.findByNombre("ROLE_ADMIN");
            rolAdOptional.ifPresent(roles::add);
        }

        usuario.setRoles(roles);
        usuario.setPassword(passwordEncoder.encode(usuario.getPassword()));

        return usuarioRepository.save(usuario);
    }

    @Transactional
    @Override
    public void eliminarPorId(Long id) {
        usuarioRepository.deleteById(id);
    }

    @Override
    public boolean existeElUsername(String username) {
        return usuarioRepository.existsByUsername(username);
    }

       
}
