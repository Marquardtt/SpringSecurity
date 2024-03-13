package com.teste.utils;

import jakarta.servlet.http.*;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.util.WebUtils;

public class CookieUtil {
    public Cookie gerarCookieJWT(UserDetails userDetails){

        //Gera o token JWT
        String token = new JwtUtil().gerarToken(userDetails);

        //Imprime o token
        System.out.println(token);

        //Cria um cookie com o token JWT
        Cookie cookie = new Cookie("JWT", token);

        //Configura o path do cookie
        cookie.setPath("/");

        //Configura o tempo de vida do cookie
        cookie.setMaxAge(300);

        return cookie;
    }


    public Cookie getCookie(HttpServletRequest request, String name) throws Exception {

        //Buscar o cookie no front
        Cookie cookie=  WebUtils.getCookie(request, name);
        if (cookie != null) {
            return cookie;
        }
        throw new Exception("Cookie não encontrado");

    }
}
