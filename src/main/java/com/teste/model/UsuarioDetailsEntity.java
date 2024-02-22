package com.teste.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import lombok.*;
import org.hibernate.validator.constraints.Length;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

@Data
@NoArgsConstructor
@Entity
@AllArgsConstructor
@Builder

public class UsuarioDetailsEntity implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(unique = true, nullable = false, updatable = false)
    @Email
    private String username;
    @Column(nullable = false)
    @Length(min = 6)
    private String password;
    private boolean enabled;
    private Collection<Autorizacao> authorities;
    private boolean accountNonExpired;
    private boolean accountNonLocked;
    private boolean credentialsNonExpired;
    @OneToOne(mappedBy = "usuarioDetailsEntity")
    private Usuario usuario;
}