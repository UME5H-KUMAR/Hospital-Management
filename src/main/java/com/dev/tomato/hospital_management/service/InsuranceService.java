package com.dev.tomato.hospital_management.service;

import org.springframework.stereotype.Service;

import com.dev.tomato.hospital_management.entity.Insurance;
import com.dev.tomato.hospital_management.entity.Patient;
import com.dev.tomato.hospital_management.repository.PatientRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class InsuranceService {

    private final PatientRepository patientRepository;


    @Transactional
    public Patient assignInsuranceToPatient(Insurance insurance, Long patientId) {
        var patient = patientRepository.findById(patientId)
                .orElseThrow(() -> new RuntimeException("Patient not found with id: " + patientId));
        patient.setInsurance(insurance);
        insurance.setPatient(patient);
        return patient;
    }

}
