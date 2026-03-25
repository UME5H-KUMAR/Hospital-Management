package com.dev.tomato.hospital_management.service;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import com.dev.tomato.hospital_management.dto.DoctorResponseDto;
import com.dev.tomato.hospital_management.dto.onBoardDoctorRequestDto;
import com.dev.tomato.hospital_management.entity.Doctor;
import com.dev.tomato.hospital_management.entity.User;
import com.dev.tomato.hospital_management.repository.DoctorRepository;
import com.dev.tomato.hospital_management.repository.UserRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class DoctorService {

    private final DoctorRepository doctorRepository;
    private final ModelMapper modelMapper;
    private final UserRepository userRepository;

    public List<DoctorResponseDto> getAllDoctors() {
        return doctorRepository.findAll().stream()
            .map(doctor -> modelMapper.map(doctor, DoctorResponseDto.class))
            .collect(Collectors.toList());
    }


    @Transactional
    public DoctorResponseDto onBoardNewDoctor(onBoardDoctorRequestDto OnBoardDoctorRequestDto ) {

        User user=  userRepository.findById(OnBoardDoctorRequestDto.getUserId()).orElseThrow();

        if(doctorRepository.existsById(OnBoardDoctorRequestDto.getUserId())) {
            throw new IllegalArgumentException("Doctor with given userId already exists");
        }

        Doctor doctor= Doctor.builder()
            .name(OnBoardDoctorRequestDto.getName())
            .specialization(OnBoardDoctorRequestDto.getSpecialization())
            .user(user)
            .email(OnBoardDoctorRequestDto.getEmail())
            .build();

        return modelMapper.map(doctorRepository.save(doctor), DoctorResponseDto.class);


    }
}
