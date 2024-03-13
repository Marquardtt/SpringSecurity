package com.teste.controller;

import com.teste.model.Usuario;
import com.teste.repository.UsuarioRepository;
import lombok.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/teste")
@AllArgsConstructor
public class SecurityController {

    private final UsuarioRepository usuarioRepository;

    @GetMapping
    public String teste(){

        //Pega o usuário autenticado
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        System.out.println(auth.getPrincipal());

        return "Teste " + auth.getName() + "!";
    }

    @GetMapping("/users")
    public List<Usuario> getAllUsers(){
        return usuarioRepository.findAll();
    }

    @PostMapping
    public void cadastroUsuario(@RequestBody Usuario usuario){
        usuarioRepository.save(usuario);
    }


}
