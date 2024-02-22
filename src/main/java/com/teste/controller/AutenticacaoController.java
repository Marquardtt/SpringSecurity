package com.teste.controller;

import com.teste.model.Autorizacao;
import com.teste.model.Usuario;
import com.teste.model.UsuarioLogin;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.context.SecurityContextRepository;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
public class AutenticacaoController {

    private final AuthenticationManager authenticationManager;
    private SecurityContextRepository securityContextRepository;

    @PostMapping("/login")
    public ResponseEntity<String> authenticate(@RequestBody UsuarioLogin usuarioLogin, HttpServletRequest request, HttpServletResponse response){
        try {
            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(usuarioLogin.getUsername(), usuarioLogin.getPassword());
            Authentication authentication = authenticationManager.authenticate(authenticationToken);
            SecurityContext context = SecurityContextHolder.createEmptyContext();
            context.setAuthentication(authentication);
            securityContextRepository.saveContext(context, request, response);
            return ResponseEntity.ok("Autenticação bem sucedida");
        }catch (AuthenticationException e){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Falha na autenticação");
        }
    }
}