package com.dev.tomato.hospital_management.service;

import com.dev.tomato.hospital_management.entity.Patient;
import com.dev.tomato.hospital_management.repository.PatientRepository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PatientService {
    private final PatientRepository patientRepository;

    public Patient getPatientById(Long id) {
        return patientRepository.findById(id).orElse(null);
    }
    
}
