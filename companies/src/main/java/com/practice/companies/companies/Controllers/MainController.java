package com.practice.companies.companies.Controllers;

import com.practice.companies.companies.DTO.ProductItemSummary;
import com.practice.companies.companies.DTO.ProductWithItemSummary;
import com.practice.companies.companies.Service.ProductItemService;
import com.practice.companies.companies.Service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/masterAPI")
public class MainController {
    private final ProductService productService;
    private final ProductItemService itemService;

    @GetMapping(path = "/productsWithItems")
    public ResponseEntity<List<ProductWithItemSummary>> getProductsWithItems(@RequestParam Integer id) {
        return ResponseEntity.ok(productService.productWithItems(id));
    }

    @GetMapping(path = "/Items")
    public ResponseEntity<List<ProductItemSummary>> getItems(@RequestParam Integer id) {
        return ResponseEntity.ok(itemService.getAllItems(id));
    }
}
