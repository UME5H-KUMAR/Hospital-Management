package com.dev.tomato.hospital_management.security;

import static com.dev.tomato.hospital_management.entity.type.PermissionType.APPOINTMENT_DELETE;
import static com.dev.tomato.hospital_management.entity.type.PermissionType.USER_MANAGE;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.servlet.HandlerExceptionResolver;

import com.dev.tomato.hospital_management.entity.type.PermissionType;
import com.dev.tomato.hospital_management.entity.type.RoleType;
import com.dev.tomato.hospital_management.entity.type.PermissionType.*;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Configuration
@RequiredArgsConstructor
@Slf4j
@EnableMethodSecurity
public class WebSecurityConfig {



    private final JwtAuthFilter jwtAuthFilter;


    private final OAuth2SuccessHandler oAuth2SuccessHandler;

    private final HandlerExceptionResolver handlerExceptionResolver;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity){
        httpSecurity
            .csrf(csrfConfig -> csrfConfig.disable())
            .sessionManagement(sessionConfig ->
                sessionConfig.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
             )
            .authorizeHttpRequests(auth -> auth
                .requestMatchers("/public/**","/auth/**").permitAll()
                .requestMatchers(HttpMethod.DELETE, "/admin/**")
                    .hasAnyAuthority(APPOINTMENT_DELETE.name(), USER_MANAGE.name())
                .requestMatchers("/doctor/**").hasAnyRole(RoleType.ADMIN.name(), RoleType.DOCTOR.name())
                .anyRequest().authenticated()
            )
            .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class)
            .oauth2Login(oAuth2-> oAuth2
                .failureHandler((request, response, exception) -> {
                    log.error("OAuth2 error: {}",exception.getMessage());
                    handlerExceptionResolver.resolveException(request,response,null, exception);
                })
                .successHandler(oAuth2SuccessHandler)
            )
            .exceptionHandling(exceptionHandlingConfig ->
                exceptionHandlingConfig.accessDeniedHandler((request,response,accessDeniedException) -> {
                    
                    handlerExceptionResolver.resolveException(request,response,null, accessDeniedException);
                })
            );
    
        return httpSecurity.build();
    }

    

    
}
