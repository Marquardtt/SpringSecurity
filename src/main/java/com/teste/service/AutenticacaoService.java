package com.teste.service;

import com.teste.model.Usuario;
import com.teste.repository.UsuarioRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;
@AllArgsConstructor
@Service
public class AutenticacaoService implements UserDetailsService {

    private UsuarioRepository usuarioRepository;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        Optional<Usuario> usuarioSecurity = usuarioRepository.findByUsuarioDetailsEntity_Username(username);
        if (usuarioSecurity.isPresent()){
            return usuarioSecurity.get().getUsuarioDetailsEntity();
        }
        throw new UsernameNotFoundException("Dados invalidos");
    }
}
