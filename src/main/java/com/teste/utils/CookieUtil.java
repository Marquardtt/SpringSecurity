package com.teste.utils;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.util.WebUtils;

public class CookieUtil {
    public Cookie gerarCookieJWT(UserDetails userDetails){
        String token = new JwtUtil().gerarToken(userDetails);
        System.out.println(token);
        Cookie cookie = new Cookie("JWT", token);
        cookie.setPath("/");
        cookie.setMaxAge(300);
        return cookie;
    }


    //Buscar o cookie no front
    public Cookie getCookie(HttpServletRequest request, String name){
        return WebUtils.getCookie(request, name);
    }
}
