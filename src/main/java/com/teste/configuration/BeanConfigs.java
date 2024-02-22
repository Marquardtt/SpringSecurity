package com.teste.configuration;

import com.teste.service.AutenticacaoService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.*;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.security.web.context.SecurityContextRepository;

@AllArgsConstructor
@Configuration
public class BeanConfigs {

    private final AutenticacaoService autenticacaoService;

    @Autowired
    public void config(AuthenticationManagerBuilder auth) throws Exception{
        auth.userDetailsService(autenticacaoService).passwordEncoder(NoOpPasswordEncoder.getInstance());
    }

    @Bean
    public SecurityContextRepository securityContextRepository(){
        return new HttpSessionSecurityContextRepository();
    }

    @Bean
    public UserDetailsService userDetailsService(AutenticacaoService autenticacaoService){
        return autenticacaoService;
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception{
        return configuration.getAuthenticationManager();
    }
}
