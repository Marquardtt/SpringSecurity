package com.teste.configuration;

import com.teste.service.AutenticacaoService;
import lombok.*;
import org.springframework.context.annotation.*;
import org.springframework.security.authentication.*;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.context.*;
import org.springframework.web.cors.*;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;

@AllArgsConstructor
@Configuration
public class BeanConfigs {

    private final AutenticacaoService autenticacaoService;

    @Bean
    public AuthenticationManager authenticationManager() {

        //Cria um objeto de autenticação
        DaoAuthenticationProvider dao = new DaoAuthenticationProvider();

        //Seta o encoder da senha
        dao.setPasswordEncoder(new BCryptPasswordEncoder());

        //Seta o serviço de autenticação
        dao.setUserDetailsService(autenticacaoService);

        return new ProviderManager(dao);
    }

    @Bean
    public SecurityContextRepository securityContextRepository(){

        //Cria um objeto de repositorio de contexto de segurança
        return new HttpSessionSecurityContextRepository();
    }

    @Bean
    public CorsConfigurationSource corsConfig(){
        //Cria um objeto de configuração de cors
        CorsConfiguration corsConfig = new CorsConfiguration();

        //Seta as configurações de cors
        corsConfig.setAllowedOrigins(List.of("http://localhost:3000"));

        //Permite apenas o método POST
        corsConfig.setAllowedMethods(List.of("POST"));

        //Habilita os cookies
        corsConfig.setAllowCredentials(true);

        //Permite todos os headers
        corsConfig.setAllowedHeaders(List.of("*"));

        //Cria um objeto de fonte de configuração de cors
        UrlBasedCorsConfigurationSource corsConfigurationSource = new UrlBasedCorsConfigurationSource();

        //Registra a configuração de cors
        corsConfigurationSource.registerCorsConfiguration("/**", corsConfig);

        return corsConfigurationSource;
    }
}
