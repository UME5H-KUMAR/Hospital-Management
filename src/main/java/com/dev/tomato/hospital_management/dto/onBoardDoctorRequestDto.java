package com.dev.tomato.hospital_management.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor

public class onBoardDoctorRequestDto {


    private Long userId;
    private String name;
    private String email;
    private String specialization;
}
