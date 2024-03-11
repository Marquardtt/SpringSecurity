package com.teste.model;

import lombok.*;
import org.springframework.security.core.GrantedAuthority;


@AllArgsConstructor
//Descreve as autorizações do sistema
public enum Autorizacao implements GrantedAuthority {

    GET("Get"),
    POST("Post"),
    PUT("Put"),
    DELETE("Delete");
    private final String nome;

    @Override
    public String getAuthority() {
        return name();
    }
}