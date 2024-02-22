package com.teste.configuration;

import com.teste.service.AutenticacaoService;
import lombok.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.*;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.security.web.context.SecurityContextRepository;

@Configuration
@AllArgsConstructor
public class ApiConfig {

    private SecurityContextRepository repo;

    @Bean
    public SecurityFilterChain config(HttpSecurity httpSecurity) throws Exception{
        httpSecurity.authorizeHttpRequests(authorizeRequests -> authorizeRequests
                .requestMatchers(HttpMethod.GET, "/teste").hasAnyAuthority("GET")
                .requestMatchers(HttpMethod.GET, "/teste/users").permitAll()
                .anyRequest().authenticated());
        httpSecurity.securityContext((context) -> context
                .securityContextRepository(repo)
        );
        httpSecurity.formLogin(Customizer.withDefaults());

        httpSecurity.logout(Customizer.withDefaults());
        return httpSecurity.build();
    }
}
