package com.teste;

import com.teste.service.AutenticacaoService;
import com.teste.utils.CookieUtil;
import com.teste.utils.JwUtil;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.context.SecurityContextRepository;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@AllArgsConstructor
public class FiltroAutenticacao extends OncePerRequestFilter {
    private SecurityContextRepository securityContextRepository;

    private final CookieUtil cookieUtil = new CookieUtil();
    private final JwUtil jwUtil = new JwUtil();
    private AutenticacaoService autenticacaoService;
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        //Coloca o cookie capturado em uma variavel Cookie
        Cookie cookie = cookieUtil.getCookie(request, "JWT");
        String token = cookie.getValue();
        String username = jwUtil.getUsername(token);

        //Criacao do usuario autenticado
        UserDetails userDetails = autenticacaoService.loadUserByUsername(username);
        Authentication authentication = new UsernamePasswordAuthenticationToken(userDetails, userDetails.getPassword(), userDetails.getAuthorities());

        //Salvaldo o usuario autenticado no security context
            SecurityContext context = SecurityContextHolder.createEmptyContext();
            context.setAuthentication(authentication);
            securityContextRepository.saveContext(context, request, response);

            //Continuação da requisição
        filterChain.doFilter(request, response);
    }

    private boolean rotaPublica(HttpServletRequest request){
        return request.getRequestURI().equals("/login") && request.getMethod().equals("POST");
    }
}
