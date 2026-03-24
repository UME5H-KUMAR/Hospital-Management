package com.dev.tomato.hospital_management.security;



import java.util.Date;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Component;

import com.dev.tomato.hospital_management.entity.User;
import com.dev.tomato.hospital_management.entity.type.AuthProviderType;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;


@Component
@Slf4j
public class AuthUtil {

    @Value("${jwt.secretKey}")
    private String jwtSecretKey;

    private SecretKey getSecretKey() {
        return Keys.hmacShaKeyFor(jwtSecretKey.getBytes());
    }

    public String generateAccessToken(User user){
        return Jwts.builder()
            .subject(user.getUsername())
            .claim("userId", user.getId().toString())
            .issuedAt(new Date())
            .expiration(new Date(System.currentTimeMillis() + 1000 * 60 * 10)) // 10 minutes
            .signWith(getSecretKey())
            .compact();
    }

    public String getUsernameFromToken(String token) {

        Claims claims= Jwts.parser()
            .verifyWith(getSecretKey())
            .build()
            .parseSignedClaims(token)
            .getPayload();
        return claims.getSubject();
    }



    public AuthProviderType getProvideTypeFromFRegistrationId(String registrationId) {
        return switch (registrationId.toLowerCase()) {
            case "google" -> AuthProviderType.GOOGLE;
            case "github" -> AuthProviderType.GITHUB;
            case "facebook" -> AuthProviderType.FACEBOOK;
            case "email" -> AuthProviderType.EMAIL;
            case "twitter" -> AuthProviderType.TWITTER;
            default -> throw new IllegalArgumentException("Unsupported Provider Type: " + registrationId);
        };
    }


    public String getProviderIdFromOAuth2User(OAuth2User oAuth2User, String registrationId) {
        String providerId= switch(registrationId.toLowerCase()) {
            case "google" -> oAuth2User.getAttribute("sub");
            case "github" -> oAuth2User.getAttribute("id").toString();
            case "facebook" -> oAuth2User.getAttribute("id").toString();
            case "email" -> oAuth2User.getAttribute("email");
            case "twitter" -> oAuth2User.getAttribute("id");
            default -> {
                log.error("Unsupported OAuth2 provider: {}", registrationId);
                throw new IllegalArgumentException("Unsupported OAuth2 provider: " + registrationId);
            }
        };
        return providerId;
    }

    public String  getUsernameFromOAuth2User(OAuth2User oAuth2User, String registrationId, String providerId) {
        String email= oAuth2User.getAttribute("email");
        if(email != null && !email.isBlank()){
            return email;
        }
        return switch(registrationId.toLowerCase()){
            case "google" -> oAuth2User.getAttribute("sub");
            case "github" ->  oAuth2User.getAttribute("login");
            default -> providerId;
        };
    }

}
