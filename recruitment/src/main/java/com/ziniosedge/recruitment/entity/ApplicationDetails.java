package com.ziniosedge.recruitment.entity;

import com.ziniosedge.recruitment.entity.ENUM.ApplicationStatus;
import jakarta.persistence.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
public class ApplicationDetails {
    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne
    private Candidate candidate;

    private String clientName;
    private LocalDate dateOfJoining;

    @ManyToOne
    private User recruiter;

    @ManyToOne
    private User hr;

    @Lob
    private String comments;

    @Enumerated(EnumType.STRING)
    private ApplicationStatus status;

    @Column(columnDefinition = "jsonb")
    private String interviewDetails; // JSON list

    private String createdBy;
    private String updatedBy;
    private LocalDateTime createdTime;
    private LocalDateTime updatedTime;
}
