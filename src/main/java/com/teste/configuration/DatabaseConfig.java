package com.teste.configuration;

import com.teste.model.*;
import com.teste.repository.UsuarioRepository;
import jakarta.annotation.*;
import lombok.*;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.List;

@AllArgsConstructor
@Configuration
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
                        .password(new BCryptPasswordEncoder().encode("teste123"))
                        .authorities(List.of(Autorizacao.GET))
                        .build());
        usuarioRepository.save(usuario);
    }
}
