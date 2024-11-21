package com.paul.software2.springboot.app.backend.services;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.paul.software2.springboot.app.backend.entities.Usuario;
import com.paul.software2.springboot.app.backend.repositories.UsuarioRepository;

@Service
public class JpaDetallesDeUsuariosService implements UserDetailsService{
    @Autowired
    private UsuarioRepository usuarioRepository;

    @Transactional(readOnly = true)
    @Override//el username que es el que viene del login
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
       Optional<Usuario> usuarOptional = usuarioRepository.findByUsername(username);
       if (usuarOptional.isEmpty()) {
            throw new UsernameNotFoundException(String.format("Username %s no existe en el sistema", username));
       }

       Usuario usuario = usuarOptional.orElseThrow();

       //convertimos los roles de la clase entity roles a una lista GrantedAuthority usando la api stream
       //el tipo de la lista es una interfaz
       List<GrantedAuthority> authorities = usuario.getRoles().stream()
       .map(role -> new SimpleGrantedAuthority(role.getNombre()))
       .collect(Collectors.toList());

       return new User( //aqui se usa el User pero de spring security details
        usuario.getUsername(),
         usuario.getPassword(),
          usuario.isEnabled(),
          true,//ahi me indica que hace cada uno de estos
          true,
          true,
          authorities);
    }
}
