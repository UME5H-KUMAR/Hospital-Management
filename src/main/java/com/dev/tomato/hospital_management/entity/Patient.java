package com.dev.tomato.hospital_management.entity;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@ToString
@Getter
@Setter
@Table(name = "patient", 
    uniqueConstraints = {
        @UniqueConstraint(name= "unique_patient_name_dob", columnNames = {"name", "dateOfBirth"}),
        // @UniqueConstraint(name= "unique_patient_email", columnNames = {"email"})
    },
    indexes={
        @Index(name="idx_patient_dob", columnList = "dateOfBirth")
    }
)

public class Patient {

    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String email;
    @Column(nullable = false)
    private String name;
    
    private LocalDate dateOfBirth;
    private String gender;

    @Column(nullable = false)
    private String bloodGroup;
}
