package com.tutorial.login.services;

import org.apache.catalina.connector.Response;
import org.apache.catalina.mbeans.UserMBean;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.tutorial.login.DTO.AuthenticationRequest;
import com.tutorial.login.DTO.AuthenticationResponse;
import com.tutorial.login.DTO.RegisterRequest;
import com.tutorial.login.bean.Role;
import com.tutorial.login.bean.UsuarioBean;
import com.tutorial.login.repository.UsuarioRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthenticationServices {

    private final PasswordEncoder passwordEncoder;
    private final UsuarioRepository usuarioRepository;
    private final JwtServices jwt;
    private final AuthenticationManager authenticationManager;

    public AuthenticationResponse registrer(RegisterRequest request) {

        AuthenticationResponse response = new AuthenticationResponse();

        if (!usuarioRepository.findByEmail(request.getEmail()).isEmpty()) {
            response.setMessage("el usuario " + request.getEmail() + " ya existe");
            return response;
        }

        UsuarioBean user = new UsuarioBean();
        user.setNombre(request.getNombre());
        user.setApellido(request.getApellido());
        user.setEmail(request.getEmail());
        user.setRole(Role.USER);
        user.setPassword(passwordEncoder.encode(request.getPassword()));

        try {
            usuarioRepository.save(user);
        } catch (Exception e) {
            System.out.print(e.getMessage());
        }

        String token = jwt.generateToken(user);

        response.setToken(token);
        response.setMessage("token generado con exito");
        return response;
    }

    public AuthenticationResponse autentication(AuthenticationRequest request) {
        AuthenticationResponse response = new AuthenticationResponse();
        try {

            // esto realiza la autentificacion de usuario con la BD es de spring security
            authenticationManager
                    .authenticate(new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));
        } catch (BadCredentialsException e) {
            response.setMessage("email o contraseña invalido");
            return response;
        }

        UsuarioBean user = usuarioRepository.findByEmail(request.getEmail()).orElseThrow();
        String token = jwt.generateToken(user);
        
        response.setToken(token);
        return response;
    }

}
