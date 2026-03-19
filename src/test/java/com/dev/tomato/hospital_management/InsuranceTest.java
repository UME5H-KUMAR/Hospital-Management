package com.dev.tomato.hospital_management;

import java.time.LocalDate;
import java.time.LocalDateTime;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.dev.tomato.hospital_management.entity.Appointment;

import com.dev.tomato.hospital_management.entity.Insurance;
import com.dev.tomato.hospital_management.entity.Patient;
import com.dev.tomato.hospital_management.service.AppointmentService;
import com.dev.tomato.hospital_management.service.InsuranceService;

@SpringBootTest
public class InsuranceTest {


    @Autowired
    private InsuranceService insuranceService;


    @Test
    public void testInsurance(){
        Insurance insurance = Insurance.builder()
                .policyNumber("SBI_123")
                .validUntil(LocalDate.of(2030,12,17))
                .provider("SBI")
                .build();

        Patient patient =insuranceService.assignInsuranceToPatient(insurance, 1L);
        System.out.println(patient);

        Patient newPatient= insuranceService.disassociateInsuranceFromPatient(1L);
        System.out.println(newPatient);
    }


    @Autowired
    private AppointmentService appointmentService;
    @Test
    public void testCreateNewAppointment(){
        Appointment appointment = Appointment.builder()
            .appointmentTime(LocalDateTime.of(2026, 5, 1, 14,0))
            .reason("cancer")
            .build();
        
        Appointment savedAppointment = appointmentService.createNewAppointment(appointment, 1L, 2L);
        System.out.println(savedAppointment);

        Appointment reAssignedAppointment = appointmentService.reAssignAppointmentToAnotherDoctor(savedAppointment.getId(), 3L);
        System.out.println(reAssignedAppointment);
    }


}
