package com.dev.tomato.hospital_management.controller;

import org.springframework.web.bind.annotation.RestController;

import com.dev.tomato.hospital_management.dto.DoctorResponseDto;
import com.dev.tomato.hospital_management.dto.PatientResponseDto;
import com.dev.tomato.hospital_management.dto.onBoardDoctorRequestDto;
import com.dev.tomato.hospital_management.service.DoctorService;
import com.dev.tomato.hospital_management.service.PatientService;

import lombok.RequiredArgsConstructor;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;



@RestController
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AdminController {

    private final PatientService patientService;
    private final DoctorService doctorService;

    

    @GetMapping("/patients")
    public ResponseEntity<List<PatientResponseDto>> getAllPatients(
            @RequestParam(value = "page", defaultValue = "0") Integer pageNumber,
            @RequestParam(value = "size", defaultValue = "10") Integer pageSize
    ) {
        return ResponseEntity.ok(patientService.getAllPatients(pageNumber, pageSize));
    }


    @PostMapping("/onBoardNewDoctor")
    public ResponseEntity<DoctorResponseDto> onBoardNewDoctor(@RequestBody onBoardDoctorRequestDto doctorRequestDto){

        return ResponseEntity.status(HttpStatus.CREATED).body(doctorService.onBoardNewDoctor(doctorRequestDto));
        
    }

    
    
    
    
}
