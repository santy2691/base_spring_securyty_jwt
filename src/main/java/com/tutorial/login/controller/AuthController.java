package com.tutorial.login.controller;

import org.springframework.beans.factory.annotation.Autowired;
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

@RestController
@RequestMapping(value = "/auth")
public class AuthController {

    @Autowired
    private AuthenticationServices autenticationServices;
    
    @PostMapping(value = "/registrer")
    public ResponseEntity<AuthenticationResponse> registrer(@RequestBody RegisterRequest request) {
        return ResponseEntity.ok(autenticationServices.registrer(request));
    }

    @PostMapping(value = "/login")
    public ResponseEntity<AuthenticationResponse> login(@RequestBody AuthenticationRequest request) {
        
        return ResponseEntity.ok(autenticationServices.autentication(request));
    }

}
