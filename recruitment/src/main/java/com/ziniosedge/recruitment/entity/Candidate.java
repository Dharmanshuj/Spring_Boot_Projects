package com.ziniosedge.recruitment.entity;

import com.ziniosedge.recruitment.entity.ENUM.Gender;
import com.ziniosedge.recruitment.entity.ENUM.MaritalStatus;
import jakarta.persistence.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
public class Candidate {
    @Id
    @GeneratedValue
    private Long id;

    private String name;

    @Column(unique = true)
    private String email;

    private Long recruiterId;

    private String phoneNumber;

    @Enumerated(EnumType.STRING)
    private Gender gender;

    private LocalDate dob;

    @Enumerated(EnumType.STRING)
    private MaritalStatus maritalStatus;

    private String city, state, country, pinCode;

    private String createdBy;
    private String updatedBy;
    private LocalDateTime createdTime;
    private LocalDateTime updatedTime;
}

