package com.dev.tomato.hospital_management.service;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import com.dev.tomato.hospital_management.dto.DoctorResponseDto;
import com.dev.tomato.hospital_management.repository.DoctorRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class DoctorService {

    private final DoctorRepository doctorRepository;
    private final ModelMapper modelMapper;

    public List<DoctorResponseDto> getAllDoctors() {
        return doctorRepository.findAll().stream()
            .map(doctor -> modelMapper.map(doctor, DoctorResponseDto.class))
            .collect(Collectors.toList());
    }
}
