package com.dev.tomato.hospital_management.dto;

import java.time.LocalDateTime;

import lombok.Data;


@Data
public class CreateAppointmentRequestDto {

    private Long patientId;
    private Long doctorId;
    private String reason;
    private LocalDateTime appointmentTime;
}
