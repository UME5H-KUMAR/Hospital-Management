package com.dev.tomato.hospital_management.service;


import org.springframework.stereotype.Service;

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

    @Transactional
    public Appointment createNewAppointment(Appointment appointment,Long patientId, Long doctorId) {
        Patient patient = patientRepository.findById(patientId)
                .orElseThrow();
        Doctor doctor = doctorRepository.findById(doctorId)
                .orElseThrow(); 
                
        if(appointment.getId() != null) throw new IllegalArgumentException("Appointment should not have exissted");

        appointment.setPatient(patient);
        appointment.setDoctor(doctor);

        patient.getAppointments().add(appointment);

        return appointmentRepository.save(appointment);
    }

    @Transactional
    public Appointment reAssignAppointmentToAnotherDoctor(Long appointmentId, Long doctorId) {
        Appointment appointment= appointmentRepository.findById(appointmentId).orElseThrow();
        Doctor doctor = doctorRepository.findById(doctorId).orElseThrow();

        appointment.setDoctor(doctor);  // this will automatically update the doctor coz it is dirty and dirty checcking will happen

        doctor.getAppointments().add(appointment); // this is just for bidirectional consistency
        
        return appointment;
    }

}
