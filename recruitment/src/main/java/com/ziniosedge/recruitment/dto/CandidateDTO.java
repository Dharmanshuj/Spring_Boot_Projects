package com.ziniosedge.recruitment.dto;

import com.ziniosedge.recruitment.entity.ENUM.Gender;
import com.ziniosedge.recruitment.entity.ENUM.MaritalStatus;
import lombok.Data;

import java.time.LocalDate;

@Data
public class CandidateDTO {
    private String name;
    private String email;
    private Long recruiterId;
    private String phoneNumber;
    private Gender gender;
    private LocalDate dob;
    private MaritalStatus maritalStatus;
    private String city;
    private String state;
    private String country;
    private String pinCode;
}
