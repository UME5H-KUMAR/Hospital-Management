package com.dev.tomato.hospital_management.service;

import com.dev.tomato.hospital_management.dto.PatientResponseDto;
import com.dev.tomato.hospital_management.entity.Patient;
import com.dev.tomato.hospital_management.repository.PatientRepository;

import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PatientService {
    private final PatientRepository patientRepository;
    private final ModelMapper modelMapper;

    public PatientResponseDto getPatientById(Long PatientId) {
        Patient patient = patientRepository.findById(PatientId).orElseThrow(() -> new IllegalArgumentException("Patient not found with id: " + PatientId));
        return modelMapper.map(patient, PatientResponseDto.class);
    }


    public List<PatientResponseDto> getAllPatients(Integer pageNumber, Integer pageSize) {
        return patientRepository.findAllPatients(PageRequest.of(pageNumber, pageSize)).stream()
            .map(patient -> modelMapper.map(patient, PatientResponseDto.class))
            .collect(Collectors.toList());
    }
    
}
