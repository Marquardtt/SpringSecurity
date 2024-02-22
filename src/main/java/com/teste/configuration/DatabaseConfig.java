package com.teste.configuration;

import com.teste.model.*;
import com.teste.repository.UsuarioRepository;
import jakarta.annotation.*;
import lombok.*;

@AllArgsConstructor
public class DatabaseConfig {

    private final UsuarioRepository usuarioRepository;

    @PostConstruct
    public void init(){
        Usuario usuario = new Usuario();
        usuario.setUsername("Teste");
        usuario.setUsuarioDetailsEntity(
                UsuarioDetailsEntity.builder()
                        .usuario(usuario)
                        .enabled(true)
                        .accountNonExpired(true)
                        .accountNonLocked(true)
                        .credentialsNonExpired(true)
                        .username("teste@gmail.com")
                        .password("teste123")
                        .build());
        usuarioRepository.save(usuario);
    }
}
