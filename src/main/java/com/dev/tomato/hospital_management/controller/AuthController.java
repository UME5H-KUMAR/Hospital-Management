package com.dev.tomato.hospital_management.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dev.tomato.hospital_management.dto.LoginRequestDto;
import com.dev.tomato.hospital_management.dto.LoginResponseDto;
import com.dev.tomato.hospital_management.dto.SignupRequestDto;
import com.dev.tomato.hospital_management.dto.SignupResponseDto;
import com.dev.tomato.hospital_management.security.AuthService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDto> login(@RequestBody LoginRequestDto loginRequest){
        return ResponseEntity.ok(authService.login(loginRequest));
    }

    @PostMapping("/signup")
    public ResponseEntity<SignupResponseDto> signup(@RequestBody SignupRequestDto signupRequest) {
        return ResponseEntity.ok(authService.signup(signupRequest));
    }
    



}
