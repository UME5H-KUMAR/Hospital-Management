package com.dev.tomato.hospital_management.config;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;


@Configuration
public class AppConfig {

    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    // @Bean
    // UserDetailsService userDetailsService() {

    //     UserDetails user1= User.withUsername("tomato")
    //         .password(passwordEncoder().encode("being"))
    //         .roles("ADMIN")
    //         .build();


    //     UserDetails user2= User.withUsername("potato")
    //         .password(passwordEncoder().encode("being"))
    //         .roles("PATIENT")
    //         .build();
     
    //     return new InMemoryUserDetailsManager(user1,user2);
    // }
}