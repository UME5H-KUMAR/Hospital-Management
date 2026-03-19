package com.dev.tomato.hospital_management.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.dev.tomato.hospital_management.entity.Department;

public interface DepartmentRepository extends JpaRepository<Department, Long> {


}