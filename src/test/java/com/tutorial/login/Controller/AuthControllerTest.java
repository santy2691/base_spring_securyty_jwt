package com.tutorial.login.Controller;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.tutorial.login.DTO.AuthenticationResponse;
import com.tutorial.login.DTO.RegisterRequest;
import com.tutorial.login.controller.AuthController;
import com.tutorial.login.services.AuthenticationServices;


@ExtendWith(MockitoExtension.class)
public class AuthControllerTest {

    @InjectMocks
    AuthController authController;

    @Mock
    AuthenticationServices autenticationServices;

    @Test
    @DisplayName("TEST 1")
    public void testRegistrer() {
        MockHttpServletRequest request = new MockHttpServletRequest();
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));

        AuthenticationResponse authenticationResponse = new AuthenticationResponse();
        authenticationResponse.setMessage("token generado con exito");

        when(autenticationServices.registrer(any(RegisterRequest.class))).thenReturn(authenticationResponse);

        RegisterRequest req = new RegisterRequest();
        ResponseEntity<?> response = authController.registrer(req);

        assertTrue(!response.getStatusCode().equals(HttpStatus.ACCEPTED));
    }
    
}
