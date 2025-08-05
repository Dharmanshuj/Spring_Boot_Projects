package com.ziniosedge.recruitment.dto;

import com.ziniosedge.recruitment.entity.ENUM.CurrentStatus;
import lombok.Data;

import java.time.LocalDate;

@Data
public class CandidateProDetailsDTO {
    private Long candidateId;
    private String currentOrg;
    private String currCTC;
    private String noticePeriod;
    private LocalDate lastWD;
    private String designation;
    private String expCTC;
    private CurrentStatus currStatus;
    private String skills;
    private String totalEXP;
    private String projects;
    private String prevOrg;
    private String prevOrgManagerEmail;
}
