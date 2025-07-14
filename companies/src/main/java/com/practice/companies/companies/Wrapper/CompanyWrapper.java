package com.practice.companies.companies.Wrapper;

import com.practice.companies.companies.DTO.CompanyDTO;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class CompanyWrapper {
    @Valid
    @NotEmpty(message = "Company list cannot be empty")
    private List<CompanyDTO> companies;

    @Valid
    private CompanyDTO company;
}
