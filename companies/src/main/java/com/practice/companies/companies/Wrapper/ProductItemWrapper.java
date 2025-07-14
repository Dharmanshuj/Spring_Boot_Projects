package com.practice.companies.companies.Wrapper;

import com.practice.companies.companies.DTO.ProductItemDTO;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class ProductItemWrapper {
    @Valid
    @NotEmpty(message = "ProductItem list cannot be empty")
    private List<ProductItemDTO> Items;

    @Valid
    private ProductItemDTO Item;
}
