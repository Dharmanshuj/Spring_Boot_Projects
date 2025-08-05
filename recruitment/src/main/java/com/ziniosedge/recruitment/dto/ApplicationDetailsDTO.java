package com.ziniosedge.recruitment.dto;

import com.ziniosedge.recruitment.entity.ENUM.ApplicationStatus;
import lombok.Data;

import java.time.LocalDate;

@Data
public class ApplicationDetailsDTO {
    private Long candidateId;
    private String clientName;
    private LocalDate dateOfJoining;
    private Long recruiter;
    private Long hr;
    private String comments;
    private ApplicationStatus status;
    private String interviewDetails;
}
