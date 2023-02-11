package com.tutorial.login.controller;

import java.time.LocalDateTime;

import org.apache.catalina.connector.Response;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.tutorial.login.DTO.AuthenticationRequest;
import com.tutorial.login.DTO.AuthenticationResponse;
import com.tutorial.login.DTO.RegisterRequest;
import com.tutorial.login.services.AuthenticationServices;

import jakarta.servlet.http.HttpServletResponse;

@RestController
@RequestMapping(value = "/auth")
public class AuthController {

    @Autowired
    private AuthenticationServices autenticationServices;
    
    @PostMapping(value = "/registrer")
    public ResponseEntity<?> registrer(@RequestBody RegisterRequest request) {
        return ResponseEntity.ok(autenticationServices.registrer(request));
    }

    @PostMapping(value = "/login")
    public ResponseEntity<?> login(@RequestBody AuthenticationRequest request) {
        AuthenticationResponse response = autenticationServices.autentication(request);
       // verifico que no hay errores
        if (response.getError() != null && !response.getError().isEmpty()) {
            // creo el json de devolucion al cliente 
            JSONObject json = new JSONObject();
            json.put("timestamp", LocalDateTime.now())
            .put("message",response.getMessage())
            .put("error", response.getError());
            return new ResponseEntity<>(json.toString(), HttpStatus.FORBIDDEN);
        }
        return ResponseEntity.ok(response);
    }

}
