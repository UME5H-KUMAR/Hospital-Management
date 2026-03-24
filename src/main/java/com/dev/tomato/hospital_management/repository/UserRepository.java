package com.dev.tomato.hospital_management.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.dev.tomato.hospital_management.entity.User;
import com.dev.tomato.hospital_management.entity.type.AuthProviderType;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByUsername(String username);

    Optional<User> findByProviderTypeAndProviderId(AuthProviderType providerType, String providerId);



}
