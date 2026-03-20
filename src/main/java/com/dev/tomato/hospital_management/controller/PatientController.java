package com.dev.tomato.hospital_management.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dev.tomato.hospital_management.dto.AppointmentResponseDto;
import com.dev.tomato.hospital_management.dto.CreateAppointmentRequestDto;
import com.dev.tomato.hospital_management.dto.PatientResponseDto;
import com.dev.tomato.hospital_management.service.AppointmentService;
import com.dev.tomato.hospital_management.service.PatientService;

import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.GetMapping;



@RestController
@RequestMapping("/patients")
@RequiredArgsConstructor
public class PatientController {

    private final  AppointmentService appointmentService;
    private final PatientService patientService;

    @PostMapping("/appointments")
    public ResponseEntity<AppointmentResponseDto> createNewAppointment(@RequestBody CreateAppointmentRequestDto createAppointmentRequestDto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(appointmentService.createNewAppointment(createAppointmentRequestDto));
    }


    @GetMapping("/profile")
    public ResponseEntity<PatientResponseDto> getPatientProfile() {
        Long patientId = 1L; 
        return ResponseEntity.ok(patientService.getPatientById(patientId));
    }
    
    
    
}
