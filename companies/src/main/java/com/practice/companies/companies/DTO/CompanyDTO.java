package com.practice.companies.companies.DTO;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.URL;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@Builder
public class CompanyDTO {
    private Integer id;

    @NotEmpty(message = "Name is required")
    private String name;
    private String description;
    private Boolean status = true;
    private Integer established_year;
    @Email(message = "Email is not valid")
    @NotEmpty(message = "Email is required")
    private String email;
    @URL(message = "Url is Invalid")
    private String website_url;
    private LocalDate createdDate, updatedDate;
    private List<ProductSummary> products;
}
