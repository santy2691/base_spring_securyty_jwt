package com.tutorial.login.config;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Iterator;

import org.json.JSONObject;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

class AuthenticationEntryPointCustom implements AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response,
            AuthenticationException authException) throws IOException, ServletException {
                
                String mensaje = authException.getMessage();
                Iterator<String> it = response.getHeaders("error").iterator();
                if (it.hasNext()){
                    mensaje = it.next();
                } 

                JSONObject json = new JSONObject();
                    json.put("timestamp", LocalDateTime.now())
                    .put("message",mensaje)
                    .put("error",authException.getMessage());

                response.setContentType("application/json;charset=UTF-8");
                response.setStatus(HttpServletResponse.SC_FORBIDDEN);
                response.getWriter().write(json.toString());
        
    }

    
}