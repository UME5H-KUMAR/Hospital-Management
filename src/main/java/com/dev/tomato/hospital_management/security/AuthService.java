package com.dev.tomato.hospital_management.security;

import java.util.Set;


import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import com.dev.tomato.hospital_management.dto.LoginRequestDto;
import com.dev.tomato.hospital_management.dto.LoginResponseDto;
import com.dev.tomato.hospital_management.dto.SignupRequestDto;
import com.dev.tomato.hospital_management.dto.SignupResponseDto;
import com.dev.tomato.hospital_management.entity.Patient;
import com.dev.tomato.hospital_management.entity.User;
import com.dev.tomato.hospital_management.entity.type.AuthProviderType;
import com.dev.tomato.hospital_management.entity.type.RoleType;
import com.dev.tomato.hospital_management.repository.PatientRepository;
import com.dev.tomato.hospital_management.repository.UserRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final AuthenticationManager authenticationManager;
    private final AuthUtil authUtil;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final PatientRepository patientRepository;

    

    public LoginResponseDto login(LoginRequestDto loginRequestDto) {
        Authentication authentication= authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(loginRequestDto.getUsername(), loginRequestDto.getPassword())
        );

        User user = (User) authentication.getPrincipal();

        String accessToken = authUtil.generateAccessToken(user);

        return new LoginResponseDto(accessToken, user.getId());

    }


    public User signupInternal(SignupRequestDto signupRequest, AuthProviderType providerType, String providerId) {
    
        User user = userRepository.findByUsername(signupRequest.getUsername()).orElse(null);
        if(user != null) throw new IllegalArgumentException("Username already exists");

        user= User.builder()
            .username(signupRequest.getUsername())
            .providerId(providerId)
            .providerType(providerType)
            .roles(signupRequest.getRoles()) // though we should not allow user to set roles themselves
            .build();
        
        if(providerType == AuthProviderType.EMAIL){
            user.setPassword(passwordEncoder.encode(signupRequest.getPassword()));
        }

        user=userRepository.save(user);

        Patient patient = Patient.builder()
            .name(signupRequest.getName())
            .email(signupRequest.getUsername())
            .user(user)
            .build();

        patientRepository.save(patient);

        return user;

    }

        


    public SignupResponseDto signup(SignupRequestDto signupRequest) {

        User user = signupInternal(signupRequest, AuthProviderType.EMAIL, null );
        return new SignupResponseDto(user.getId(), user.getUsername());
    }


    @Transactional
    public ResponseEntity<LoginResponseDto> handleOAuth2LoginRequest(OAuth2User oAuth2User, String registrationId) {
        
        //fetch provider type and provider id from oAuth2User
        AuthProviderType providerType= authUtil.getProvideTypeFromFRegistrationId(registrationId);

        String providerId= authUtil.getProviderIdFromOAuth2User(oAuth2User, registrationId);

        User user = userRepository.findByProviderTypeAndProviderId(providerType, providerId).orElse(null);

        String email= oAuth2User.getAttribute("email");
        String name= oAuth2User.getAttribute("name");

        User emailUser= userRepository.findByUsername(email).orElse(null);

        if(user == null && emailUser == null){
            String username = authUtil.getUsernameFromOAuth2User(oAuth2User, registrationId, providerId);
            user= signupInternal(new SignupRequestDto(username,null, name, Set.of(RoleType.PATIENT)), providerType, providerId);
        }
        else if( user != null){
            if(email != null && !email.isBlank() && !email.equals(user.getUsername())){
                user.setUsername(email);
                userRepository.save(user);
            }
        }
        else {
            throw new BadCredentialsException("This email is already registered with provider: " + emailUser.getProviderType());
        }

        LoginResponseDto loginResponseDto= new LoginResponseDto(authUtil.generateAccessToken(user), user.getId());

        return ResponseEntity.ok(loginResponseDto);
        // save the provider type and provide id with the user

        //if user present the login

        //otherwise signup then login
    }

}
