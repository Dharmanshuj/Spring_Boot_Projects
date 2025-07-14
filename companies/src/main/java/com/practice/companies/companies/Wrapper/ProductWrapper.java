package com.practice.companies.companies.Wrapper;

import com.practice.companies.companies.DTO.ProductDTO;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class ProductWrapper {
    @Valid
    @NotEmpty(message = "Product list cannot be empty")
    private List<ProductDTO> products;

    @Valid
    private ProductDTO product;
}
