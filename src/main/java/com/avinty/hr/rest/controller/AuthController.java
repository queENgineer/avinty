package com.avinty.hr.rest.controller;

import com.avinty.hr.auth.TokenManager;
import com.avinty.hr.model.dto.request.LoginRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

import static com.avinty.hr.util.ConstantUtil.encode;

@RestController
@Slf4j
@RequestMapping("/API/V1/login")
//@CrossOrigin(origins = {"http://localhost:5000"})
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private TokenManager tokenManager;

    @PostMapping
    public ResponseEntity<String> login(@RequestBody LoginRequest loginRequest){
        String txnId = UUID.randomUUID().toString();
        try{
            log.info(encode("{} AuthController - login() start.", txnId));
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getUsername(),loginRequest.getPassword(),loginRequest.getAuthority()));
            log.info(encode("{} AuthController - login() end.", txnId));
            return ResponseEntity.ok(tokenManager.generateToken(loginRequest.getUsername()));
        }catch(BadCredentialsException ex){
            log.info(encode("{} AuthController - login() Exception occured :", ex));
            return new ResponseEntity<String>("Bad Credentials", HttpStatus.UNAUTHORIZED);
        }catch (Exception ex){
            log.info(encode("{} AuthController - login() Exception occured :", ex));
            return new ResponseEntity<String>("Internal Server Error", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
