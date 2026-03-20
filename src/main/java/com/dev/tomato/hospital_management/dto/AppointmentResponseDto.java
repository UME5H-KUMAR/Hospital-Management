package com.dev.tomato.hospital_management.dto;

import java.time.LocalDateTime;



import lombok.Data;

@Data
public class AppointmentResponseDto {

    private Long id;
    private LocalDateTime appointmentTime;
    private String reason;
    private DoctorResponseDto doctor;
    
}
