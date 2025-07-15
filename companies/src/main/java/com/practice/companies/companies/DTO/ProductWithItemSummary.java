package com.practice.companies.companies.DTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class ProductWithItemSummary {
    private Integer id;
    private String name;

    List<ProductItemSummary> items;
}
