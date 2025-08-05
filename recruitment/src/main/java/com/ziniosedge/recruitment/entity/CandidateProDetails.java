package com.ziniosedge.recruitment.entity;

import com.ziniosedge.recruitment.entity.ENUM.CurrentStatus;
import jakarta.persistence.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
public class CandidateProDetails {
    @Id
    @GeneratedValue
    private Long id;

    @OneToOne
    private Candidate candidate;

    private String currentOrg;
    private String currCTC;
    private String noticePeriod;
    private LocalDate lastWD;
    private String designation;
    private String expCTC;

    @Enumerated(EnumType.STRING)
    private CurrentStatus currStatus;

    @Lob
    private String skills;

    private String totalEXP;

    @Column(columnDefinition = "jsonb")
    private String projects; // JSON list

    @Column(columnDefinition = "jsonb")
    private String prevOrg; // JSON object

    private String prevOrgManagerEmail;

    private String createdBy;
    private String updatedBy;
    private LocalDateTime createdTime;
    private LocalDateTime updatedTime;
}
