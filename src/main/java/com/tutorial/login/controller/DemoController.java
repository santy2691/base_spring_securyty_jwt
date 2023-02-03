package com.tutorial.login.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tutorial.login.DTO.DemoDto;

@RestController
@RequestMapping(value = "/demo")
public class DemoController {


    @PostMapping(value = "/demo")
    public ResponseEntity<?> registrer(@RequestBody DemoDto request) {
        Authentication auth = SecurityContextHolder
        .getContext()
        .getAuthentication();


        
        return ResponseEntity.ok("funciona");
    }
    
}
