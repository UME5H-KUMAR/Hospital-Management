package com.dev.tomato.hospital_management.dto;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PatientResponseDto {

    private Long id;
    private String name;
    private String gender;
    private LocalDate dateOfBirth;
    private String bloodGroup;
}
