package com.teste.utils;

import io.jsonwebtoken.*;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Date;

public class JwUtil {
    public String gerarToken(UserDetails userDetails){
        return Jwts.builder().issuer("WEG").issuedAt(new Date()).expiration(new Date(new Date().getTime()+30000)).signWith(SignatureAlgorithm.NONE, "senha123").subject(userDetails.getUsername()).compact();
    }

    private Jws<Claims> validarToken(String token){
        return getParser().parseSignedClaims(token);
    }

    public JwtParser getParser(){
        return Jwts.parser().setSigningKey("senha123").build();
    }
    public String getUsername(String token){
        return validarToken(token).getPayload().getSubject();
    }
}
