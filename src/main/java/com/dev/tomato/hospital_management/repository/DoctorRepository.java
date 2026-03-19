package com.dev.tomato.hospital_management.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.dev.tomato.hospital_management.entity.Doctor;

public interface DoctorRepository extends JpaRepository<Doctor, Long> {

}
