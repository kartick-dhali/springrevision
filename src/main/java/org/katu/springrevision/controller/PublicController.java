package org.katu.springrevision.controller;

import org.katu.springrevision.DTO.LoginRequest;
import org.katu.springrevision.entity.UserDetailsServiceImp;
import org.katu.springrevision.entity.UserEntity;
import org.katu.springrevision.jwtservices.JwtUtil;
import org.katu.springrevision.service.UserEntryService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/public")
public class PublicController {


    private final UserEntryService userEntryService;
    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;
    private final UserDetailsServiceImp userDetailsServiceImp;

    public PublicController(UserEntryService userEntryService, AuthenticationManager authenticationManager, JwtUtil jwtUtil, UserDetailsServiceImp userDetailsServiceImp) {
        this.userEntryService = userEntryService;
        this.authenticationManager = authenticationManager;
        this.jwtUtil = jwtUtil;
        this.userDetailsServiceImp = userDetailsServiceImp;
    }

    @GetMapping("/health-check")
    public String  healthCheck(){
        return "Ok";
    }

    @PostMapping("/enroll")
    public void CreateUser(@RequestBody UserEntity user){
        userEntryService.saveEntry(user);
    }

    @PostMapping("/login")
    public String login(@RequestBody LoginRequest loginRequest) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));
        UserDetails user = userDetailsServiceImp.loadUserByUsername(loginRequest.getUsername());
        return jwtUtil.generateToken(user);
    }
}
