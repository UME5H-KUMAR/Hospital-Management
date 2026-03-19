package com.dev.tomato.hospital_management.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.dev.tomato.hospital_management.dto.BloodGroupCountResponseDto;
import com.dev.tomato.hospital_management.entity.Patient;
import org.springframework.stereotype.Repository;

@Repository
public interface PatientRepository extends JpaRepository<Patient, Long>{

    Patient findByEmail(String email);

    List<Patient> findByNameOrDateOfBirth(String name, LocalDate dateOfBirth);

    List<Patient> findByNameContainingOrderByIdDesc(String name);

    @Query("select p from Patient p where p.dateOfBirth > :dateOfBirth")
    List<Patient> findByBornAfterDate(@Param("dateOfBirth") LocalDate dateOfBirth);

    @Query("select  new com.dev.tomato.hospital_management.dto.BloodGroupCountResponseDto(p.bloodGroup, "+" Count(p)) from Patient p group by p.bloodGroup")
    List<BloodGroupCountResponseDto> countEachBloodGroupType();

    @Query(value = "select * from patient ", nativeQuery = true)
    Page<Patient> findAllPatients(Pageable pageable);

    @Query("select p from Patient p LEFT JOIN FETCH p.appointments")
    List<Patient> findPatientsWithAppointments();
}
