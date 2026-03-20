package com.dev.tomato.hospital_management.service;


import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import com.dev.tomato.hospital_management.dto.AppointmentResponseDto;
import com.dev.tomato.hospital_management.dto.CreateAppointmentRequestDto;
import com.dev.tomato.hospital_management.entity.Appointment;
import com.dev.tomato.hospital_management.entity.Doctor;
import com.dev.tomato.hospital_management.entity.Patient;
import com.dev.tomato.hospital_management.repository.AppointmentRepository;
import com.dev.tomato.hospital_management.repository.DoctorRepository;
import com.dev.tomato.hospital_management.repository.PatientRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AppointmentService {

    private final AppointmentRepository appointmentRepository;
    private final PatientRepository patientRepository;
    private final DoctorRepository doctorRepository;
    private final ModelMapper modelMapper;

    @Transactional
    public AppointmentResponseDto createNewAppointment(CreateAppointmentRequestDto createAppointmentRequestDto) {
        Long patientid = createAppointmentRequestDto.getPatientId();
        Long doctorId = createAppointmentRequestDto.getDoctorId();
        
        Patient patient = patientRepository.findById(patientid).
            orElseThrow(() -> new IllegalArgumentException("Patient not found with id: " + patientid));

        Doctor doctor = doctorRepository.findById(doctorId).
            orElseThrow(() -> new IllegalArgumentException("Doctor not found with id: " + doctorId));
       
        Appointment appointment = Appointment.builder()
            .reason(createAppointmentRequestDto.getReason())
            .appointmentTime(createAppointmentRequestDto.getAppointmentTime())
            .build();

        appointment.setPatient(patient);
        appointment.setDoctor(doctor);

        patient.getAppointments().add(appointment);

        appointmentRepository.save(appointment);

        return modelMapper.map(appointment, AppointmentResponseDto.class);
    }

    @Transactional
    public Appointment reAssignAppointmentToAnotherDoctor(Long appointmentId, Long doctorId) {
        Appointment appointment= appointmentRepository.findById(appointmentId).orElseThrow();
        Doctor doctor = doctorRepository.findById(doctorId).orElseThrow();

        appointment.setDoctor(doctor);  // this will automatically update the doctor coz it is dirty and dirty checcking will happen

        doctor.getAppointments().add(appointment); // this is just for bidirectional consistency
        
        return appointment;
    }

    @PreAuthorize("hasRole('ADMIN') or hasRole('DOCTOR') and #doctorId ==authentication.principal.id")
    public List<AppointmentResponseDto> getAllAppointmentsOfDoctor(Long doctorId) {
        Doctor doctor = doctorRepository.findById(doctorId).orElseThrow();
        return doctor.getAppointments().stream()
            .map(appointment -> modelMapper.map(appointment, AppointmentResponseDto.class))
            .collect(Collectors.toList());
    }

}
