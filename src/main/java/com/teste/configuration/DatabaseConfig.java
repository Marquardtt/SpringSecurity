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
    //Cria um usuário com as informações para que ele seja criado
    public void init(){

        //Cria um objeto de usuário
        Usuario usuario = new Usuario();

        //Seta as informações do usuário
        usuario.setUsername("Teste");

        //Cria um objeto de UserDetailsEntity
        usuario.setUsuarioDetailsEntity(

                //Seta as informações do UserDetailsEntity
                UsuarioDetailsEntity.builder()
                        .usuario(usuario)
                        .enabled(true)
                        .accountNonExpired(true)
                        .accountNonLocked(true)
                        .credentialsNonExpired(true)
                        .username("teste@gmail.com")

                        //Faz a encriptação da senha
                        .password(new BCryptPasswordEncoder().encode("teste123"))

                        //Seta as autorizações do usuário
                        .authorities(List.of(Autorizacao.GET))

                        .build());
        usuarioRepository.save(usuario);
    }
}
