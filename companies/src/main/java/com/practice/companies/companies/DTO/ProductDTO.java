package com.practice.companies.companies.DTO;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@Builder
public class ProductDTO {
    private Integer id;

    @NotEmpty(message = "Name is required")
    private String name;
    private String description;
    private Boolean status = true;
    private String type;
    private Integer price;

    @NotNull
    private Integer companyId;

    private LocalDate createdDate, updatedDate;
    private List<ProductItemSummary> items;
}
