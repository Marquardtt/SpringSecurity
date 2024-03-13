package com.teste.controller;

import com.teste.model.UsuarioLogin;
import com.teste.utils.*;
import jakarta.servlet.http.*;
import lombok.*;
import org.springframework.http.*;
import org.springframework.security.authentication.*;
import org.springframework.security.core.*;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
public class AutenticacaoController {

    private final AuthenticationManager authenticationManager;
    private final CookieUtil cookieUtil = new CookieUtil();

    @PostMapping("/auth/login")
    public ResponseEntity<String> authenticate(@RequestBody UsuarioLogin usuarioLogin, HttpServletRequest request, HttpServletResponse response){
        try {

            //Cria um objeto de autenticação com o username e password
            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(usuarioLogin.getUsername(), usuarioLogin.getPassword());

            //Autentica o usuario
            Authentication authentication = authenticationManager.authenticate(authenticationToken);

            //Pega o usuario autenticado
            UserDetails user = (UserDetails) authentication.getPrincipal();

            //Cria um cookie com o token JWT
            Cookie cookie = cookieUtil.gerarCookieJWT(user);

            //Adiciona o cookie na resposta
            response.addCookie(cookie);

            return ResponseEntity.ok("Autenticação bem sucedida");
        }catch (AuthenticationException e){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Falha na autenticação");
        }
    }
    @PostMapping("logout")
    public void logout(HttpServletRequest request, HttpServletResponse response){
        Cookie cookie = cookieUtil.getCookie(request, "JWT");
        cookie.setMaxAge(0);
        response.addCookie(cookie);
    }
}