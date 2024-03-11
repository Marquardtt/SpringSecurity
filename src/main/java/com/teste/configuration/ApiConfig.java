package com.teste.configuration;

import lombok.*;
import org.springframework.context.annotation.*;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.context.SecurityContextRepository;

@Configuration
@AllArgsConstructor
public class ApiConfig {

    private SecurityContextRepository repo;

    @Bean
    //Filtro de authority
    public SecurityFilterChain config(HttpSecurity httpSecurity) throws Exception{

        //Gera um token de verificação para constar se a origem da requisição do cliente é realmente feita pelo mesmo e não fui interceptada por alguém
        httpSecurity.csrf(AbstractHttpConfigurer::disable);

        httpSecurity.authorizeHttpRequests(authorizeRequests -> authorizeRequests

                //Verifica se o usuário possui alguma authority com o nome GET
                .requestMatchers(HttpMethod.GET, "/teste").hasAnyAuthority("GET")

                //Permite todas as requisições para este url
                .requestMatchers(HttpMethod.GET, "/teste/users").permitAll()

                //Caso nenhuma verificação seja concluida ele permite as requisições caso o usuário seja autenticado
                .anyRequest().authenticated());

        //Manter a seção usuário ativa, sem que ele precise logar novamente a cada requisição
        httpSecurity.securityContext((context) -> context.securityContextRepository(repo)
        );
        //Gera um formulario de Login
        httpSecurity.formLogin(Customizer.withDefaults());

        //Gera uma forma de executar o Logout
        httpSecurity.logout(Customizer.withDefaults());
        return httpSecurity.build();
    }
}
