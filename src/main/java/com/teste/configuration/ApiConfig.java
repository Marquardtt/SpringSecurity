package com.teste.configuration;

import com.teste.FiltroAutenticacao;
import com.teste.model.Autorizacao;
import lombok.*;
import org.springframework.context.annotation.*;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.context.SecurityContextRepository;

@Configuration
@AllArgsConstructor
public class ApiConfig {

    private final FiltroAutenticacao filtroAutenticacao;
    private final SecurityContextRepository securityContextRepository;

    @Bean
    //Filtro de authority
    public SecurityFilterChain config(HttpSecurity httpSecurity) throws Exception{

        //Gera um token de verificação para constar se a origem da requisição do cliente é realmente feita pelo mesmo e não fui interceptada por alguém
        httpSecurity.csrf(AbstractHttpConfigurer::disable);

        //Configuração das autorizações
        httpSecurity.authorizeHttpRequests(authorizeRequests -> authorizeRequests

                //Verifica se o usuário possui alguma authority com o nome GET
                .requestMatchers(HttpMethod.GET, "/teste").hasAuthority(Autorizacao.GET.getAuthority())

                //Permite todas as requisições para este url
                .requestMatchers(HttpMethod.GET, "/teste/users").permitAll()

                //Permite todas as requisições para este url
                .requestMatchers(HttpMethod.POST, "/auth/login").permitAll()

                //Caso nenhuma verificação seja concluida ele permite as requisições caso o usuário seja autenticado
                .anyRequest().authenticated());

        //Configuração do SecurityContextRepository
        httpSecurity.securityContext((context) -> context
                .securityContextRepository(securityContextRepository));

        //Desabilita o formLogin
        httpSecurity.formLogin(AbstractHttpConfigurer::disable);

        //Não salva a seção de usuario
        httpSecurity.sessionManagement(config->{
            config.sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        });

        //Faz com que o filtro seja adicinado na lista de filtros que são utilizados antes de cada requisição
        httpSecurity.addFilterBefore(filtroAutenticacao, UsernamePasswordAuthenticationFilter.class);

        //Desabilita o logout
        httpSecurity.logout(AbstractHttpConfigurer::disable);
        return httpSecurity.build();
    }
}
