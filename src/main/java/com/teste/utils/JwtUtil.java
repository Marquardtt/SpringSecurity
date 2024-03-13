package com.teste.utils;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;

public class JwtUtil {
    private final SecretKey key;

    public JwtUtil(){
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String senha = passwordEncoder.encode("senha123");
        this.key =  Keys.hmacShaKeyFor(senha.getBytes());
    }

    public String gerarToken(UserDetails userDetails){
        SecretKey key = Keys.hmacShaKeyFor("senha123".getBytes(StandardCharsets.UTF_8));
        return Jwts.builder()
                .issuer("WEG")
                .issuedAt(new Date())
                .expiration(new Date(new Date().getTime()+30000))
                .signWith(this.key, Jwts.SIG.HS384)
                .subject(userDetails.getUsername())
                .compact();
    }

    private Jws<Claims> validarToken(String token){
        return getParser().parseSignedClaims(token);
    }

    public JwtParser getParser(){
        return Jwts.parser().verifyWith(this.key).build();
    }
    public String getUsername(String token){
        return validarToken(token).getPayload().getSubject();
    }
}
