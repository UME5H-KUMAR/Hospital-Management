package com.dev.tomato.hospital_management.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@AllArgsConstructor
@NoArgsConstructor
@ToString
@Data
public class BloodGroupCountResponseDto {
    private String bloodGroup;
    private Long count;
}
