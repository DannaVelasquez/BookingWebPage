package com.digitalhouse.digitalexpirience.controller;

import com.digitalhouse.digitalexpirience.dto.request.LoginRequest;
import com.digitalhouse.digitalexpirience.dto.response.ApiResponseDTO;
import com.digitalhouse.digitalexpirience.dto.response.LoginResponse;
import com.digitalhouse.digitalexpirience.exception.AuthenticationException;
import com.digitalhouse.digitalexpirience.jwt.JwtTokenUtil;

import com.digitalhouse.digitalexpirience.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.*;


import java.util.Locale;
import java.util.Objects;

@CrossOrigin
@Slf4j
@RestController
public class AuthenticationController {


    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private UserService userService;

    @RequestMapping(value = "/authenticate", method = RequestMethod.POST)
    public ResponseEntity<?> createAuthenticationToken(@RequestBody LoginRequest authenticationRequest, Locale locale)
            throws AuthenticationException {
        try {
            authenticate(authenticationRequest.getUsername(), authenticationRequest.getPassword());

            final UserDetails userDetails = userDetailsService.loadUserByUsername(authenticationRequest.getUsername());
            var user = userService.getUserByUsername(authenticationRequest.getUsername());
            final String token = jwtTokenUtil.generateToken(userDetails);

            return ResponseEntity.ok().body(ApiResponseDTO.created(LoginResponse.toLoginResponse(token, user), "Success", HttpStatus.OK));


        } catch (RuntimeException e) {
            log.error("Error en el Controller -> authenticate " + e.getMessage());
            throw new AuthenticationException("El email o la contraseña ingresada son incorrectos");
        }
    }


    private void authenticate(String username, String password) {
        Objects.requireNonNull(username);
        Objects.requireNonNull(password);

        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
        } catch (DisabledException e) {
            throw new AuthenticationException("USER_DISABLED");
        } catch (BadCredentialsException e) {
            throw new AuthenticationException("INVALID_CREDENTIALS");
        }
    }
}
