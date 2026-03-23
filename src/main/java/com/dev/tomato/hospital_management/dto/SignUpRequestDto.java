package com.dev.tomato.hospital_management.dto;

import java.util.HashSet;
import java.util.Set;

import com.dev.tomato.hospital_management.entity.type.RoleType;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SignupRequestDto {

    private String username;
    private String password;
    private String name;

    private Set<RoleType> roles= new HashSet<>();
}
