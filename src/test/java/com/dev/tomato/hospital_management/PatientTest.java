package com.dev.tomato.hospital_management;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;
import java.util.List;

import com.dev.tomato.hospital_management.dto.BloodGroupCountResponseDto;
import com.dev.tomato.hospital_management.entity.Patient;
import com.dev.tomato.hospital_management.repository.PatientRepository;
import com.dev.tomato.hospital_management.service.PatientService;

@SpringBootTest
public class PatientTest {

    @Autowired    
    private PatientService patientService;
    @Autowired
    private PatientRepository patientRepository;

    @Test
    public void testPatientRepository() {
        List<Patient> patientList = patientRepository.findAll();
        System.out.println(patientList);
    }

    @Test
    public void testTransactionMethods(){
        // Patient patient = patientService.getPatientById(1L);
        // System.out.println(patient);
        // List<Patient> patientList = patientRepository.findByNameOrDateOfBirth("Diya Patel",LocalDate.of(1988, 3, 15));
        // System.out.println(patientList);

        // List<Patient> patientList= patientRepository.findByNameContainingOrderByIdDesc("Di");
        // System.out.println(patientList);

        List<Patient> patientList= patientRepository.findByBornAfterDate(LocalDate.of(1990, 1, 1));
        System.out.println(patientList);

        // List<Object[]> bloodGroupCount = patientRepository.countEachBloodGroupType();
        // for (Object[] row : bloodGroupCount) {
        //     System.out.println("Blood Group: " + row[0] + ", Count: " + row[1]);
        // }

        List<BloodGroupCountResponseDto> bloodGroupCountList= patientRepository.countEachBloodGroupType();
        for (BloodGroupCountResponseDto dto : bloodGroupCountList) {
            System.out.println(dto);
        }

        Page<Patient> patientPage= patientRepository.findAllPatients(PageRequest.of(2,2));
        for(Patient patient: patientPage){
            System.out.println(patient);
        }
    }

}
