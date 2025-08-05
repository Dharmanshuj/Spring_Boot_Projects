package com.ziniosedge.recruitment.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;

import java.time.LocalDateTime;

@Entity
public class CandidateDoc {
    @Id
    @GeneratedValue
    private Long id;

    @OneToOne
    private Candidate candidate;

    private String uanNo;
    private String panNo;
    private String aadharNo;

    private String createdBy;
    private String updatedBy;
    private LocalDateTime createdTime;
    private LocalDateTime updatedTime;
}

