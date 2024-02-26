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
    public SecurityFilterChain config(HttpSecurity httpSecurity) throws Exception{
        httpSecurity.csrf(AbstractHttpConfigurer::disable);
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
