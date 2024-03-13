package com.teste.utils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import org.springframework.security.core.userdetails.UserDetails;
import java.util.Date;

public class JwtUtil {

    public String gerarToken(UserDetails userDetails){

        //Gera o token JWT
        Algorithm algorithm = Algorithm.HMAC256("teste123");

        //Retorna o token JWT
        return JWT.create().withIssuer("WEG")
                .withIssuedAt(new Date())
                .withExpiresAt(new Date(new Date().getTime()))
                .withSubject(userDetails.getUsername())
                .sign(algorithm);
    }

    public String getUsername(String token){

        //Decodifica o token JWT e retorna o username
        return JWT.decode(token).getSubject();
    }
}
