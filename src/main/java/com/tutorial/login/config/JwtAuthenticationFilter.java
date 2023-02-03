package com.tutorial.login.config;

import java.io.Console;
import java.io.IOException;
import java.util.Base64;
import java.util.Date;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.tutorial.login.services.JwtServices;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.io.Decoders;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor // esta anotacion genera el constructor a partir de los atributros
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    
    private final JwtServices jwtServices;
    private final UserDetailsService userDetailsService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        String tokenHeader = request.getHeader("Authorization");
        String jwt;
        String userName;
        // verifico si el el token existe
        if (tokenHeader == null || !tokenHeader.startsWith("Bearer ")) {
            // si el token no existe sigo con el siguiente filtro
            response.addHeader("error","no tiene token");
            filterChain.doFilter(request, response);
            return;
        }

        jwt = tokenHeader.split(" ")[1];
        try {
            userName = jwtServices.extractUserName(jwt);
    
            if (userName != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                UserDetails userDetails = this.userDetailsService.loadUserByUsername(userName);
    
                if (jwtServices.isTokenValid(jwt, userDetails)) {
                    UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(userDetails,
                            null, userDetails.getAuthorities());
    
                    authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
    
                    SecurityContextHolder.getContext().setAuthentication(authToken);
                }
            }            
        } catch (ExpiredJwtException e) {
            System.out.println("token esta expirado " + e.getMessage());
            response.addHeader("error", "token caducado");
        }

        filterChain.doFilter(request, response);
    }

}
