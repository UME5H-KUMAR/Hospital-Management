package com.dev.tomato.hospital_management.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Configuration
@RequiredArgsConstructor
@Slf4j
@EnableMethodSecurity
public class WebSecurityConfig {


    private final JwtAuthFilter jwtAuthFilter;

    private final HandlerExceptionResolver handlerExceptionResolver;

    private final OAuth2SuccessHandler oAuth2SuccessHandler;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity){
        httpSecurity
            .csrf(csrfConfig -> csrfConfig.disable())
            .sessionManagement(sessionConfig ->
                sessionConfig.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
             )
            .authorizeHttpRequests(auth -> auth
                .requestMatchers("/public/**","/auth/**").permitAll()
                // .requestMatchers("/admin/**").hasRole("ADMIN")
                // .requestMatchers("/doctor/**").hasAnyRole("ADMIN","DOCTOR")
                .anyRequest().authenticated()
            )
            .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class)
            .oauth2Login(oAuth2-> oAuth2
                .failureHandler((request, response, exception) -> {
                    log.error("OAuth2 error: {}",exception.getMessage());
                })
                .successHandler(oAuth2SuccessHandler)
                
            );
    
        return httpSecurity.build();
    }

    

    
}
