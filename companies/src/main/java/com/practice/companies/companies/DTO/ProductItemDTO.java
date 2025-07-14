package com.practice.companies.companies.DTO;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@Builder
public class ProductItemDTO {
    private Integer id;

    @NotNull(message = "Name is required")
    private String name;
    private String description;
    private Boolean status = true;

    @NotNull(message = "Product ID is required")
    private Integer productId;

    private LocalDate createdDate, updatedDate;
}
