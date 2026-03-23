package com.dev.tomato.hospital_management.security;

import org.modelmapper.ModelMapper;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.dev.tomato.hospital_management.dto.LoginRequestDto;
import com.dev.tomato.hospital_management.dto.LoginResponseDto;
import com.dev.tomato.hospital_management.dto.SignupRequestDto;
import com.dev.tomato.hospital_management.dto.SignupResponseDto;
import com.dev.tomato.hospital_management.entity.User;
import com.dev.tomato.hospital_management.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final ModelMapper modelMapper;
    private final AuthenticationManager authenticationManager;
    private final AuthUtil authUtil;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    

    public LoginResponseDto login(LoginRequestDto loginRequestDto) {
        Authentication authentication= authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(loginRequestDto.getUsername(), loginRequestDto.getPassword())
        );

        User user = (User) authentication.getPrincipal();

        String accessToken = authUtil.generateAccessToken(user);

        return new LoginResponseDto(accessToken, user.getId());

    }

    public SignupResponseDto signup(SignupRequestDto signupRequest) {

        User user = userRepository.findByUsername(signupRequest.getUsername()).orElse(null);
        if(user != null) throw new IllegalArgumentException("Username already exists");

        user= userRepository.save(User.builder()
            .username(signupRequest.getUsername())
            .password(passwordEncoder.encode(signupRequest.getPassword()))
            .build()
        );

        return modelMapper.map(user, SignupResponseDto.class);
    }

}
