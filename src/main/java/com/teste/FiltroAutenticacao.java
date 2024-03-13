package com.teste;

import com.teste.service.AutenticacaoService;
import com.teste.utils.*;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import lombok.*;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.*;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.context.SecurityContextRepository;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component

@AllArgsConstructor
public class FiltroAutenticacao extends OncePerRequestFilter {

    private SecurityContextRepository securityContextRepository;
    private final CookieUtil cookieUtil = new CookieUtil();
    private final JwtUtil jwtUtil = new JwtUtil();

    private AutenticacaoService autenticacaoService;
    @Override
    //Filtro que intercepta todas as requisições
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException{
        if(!rotaPublica(request)){
            Cookie cookie;
            try {
                //Coloca o cookie capturado em uma variavel Cookie
                cookie = cookieUtil.getCookie(request, "JWT");
            }catch (Exception e){
                //Se não tiver o cookie, a requisição é negada
                response.sendError(401);
                return;
            }
            //Pega o token do cookie
            String token = cookie.getValue();

            //Pega o username do token
            String username = jwtUtil.getUsername(token);

        //Cria um objeto UserDetails com o username
        UserDetails userDetails = autenticacaoService.loadUserByUsername(username);

        //Cria um objeto de autenticação com o UserDetails
        Authentication authentication = new UsernamePasswordAuthenticationToken(userDetails, userDetails.getPassword(), userDetails.getAuthorities());

        //Salvando o usuario autenticado no security context
        SecurityContext context = SecurityContextHolder.createEmptyContext();

        //Seta a autenticação no contexto
        context.setAuthentication(authentication);

        //Salva o contexto no banco de dados
        securityContextRepository.saveContext(context, request, response);

        //Renovar JWT e Cookie
        Cookie newCookie = cookieUtil.gerarCookieJWT(userDetails);
        response.addCookie(newCookie);
    }
        //Continuação da requisição
        filterChain.doFilter(request, response);
    }

    private boolean rotaPublica(HttpServletRequest request){

        //Verifica se a requisição é para o login
        return request.getRequestURI().equals("/auth/login") && request.getMethod().equals("POST");
    }
}
