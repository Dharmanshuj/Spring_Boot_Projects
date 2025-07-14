package com.practice.companies.companies.Controllers;

import com.practice.companies.companies.DTO.ProductDTO;
import com.practice.companies.companies.Service.ProductService;
import com.practice.companies.companies.Wrapper.ProductWrapper;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.ErrorResponse;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/product")
@RequiredArgsConstructor
public class ProductController {
    private final ProductService service;

    @GetMapping
    public ResponseEntity<List<ProductDTO>> getAll() {
        return ResponseEntity.ok(service.getAllProducts());
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<ProductDTO> getById(@PathVariable Integer id) {
        return ResponseEntity.ok(service.getProduct(id));
    }

    @PostMapping(path = "/create")
    public ResponseEntity<ProductDTO> createProduct(@Valid @RequestBody ProductDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.saveProduct(dto));
    }

    @PostMapping(path = "/createAll")
    public ResponseEntity<List<ProductDTO>> createAllProducts(@Valid @RequestBody ProductWrapper w) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.saveAllProducts(w.getProducts()));
    }

    @PutMapping(path = "/update")
    public ResponseEntity<ProductDTO> updateProduct(@RequestParam Integer id, @Valid @RequestBody ProductDTO dto) {
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(service.updateProduct(id, dto));
    }

    @PutMapping(path = "/updateAll")
    public ResponseEntity<List<ProductDTO>> updateAllProducts(@Valid @RequestBody ProductWrapper w) {
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(service.updateAllProducts(w.getProducts()));
    }

    @DeleteMapping(path = "/delete")
    public ResponseEntity<String> deleteProduct(@RequestParam Integer id) {
        service.deleteProduct(id);
        return ResponseEntity.ok("Deleted Product with ID: " + id);
    }

    @DeleteMapping(path = "/deleteAll")
    public ResponseEntity<String> deleteAllProducts(@RequestBody List<Integer> ids) {
        service.deleteAllProducts(ids);
        return ResponseEntity.ok("Deleted all Products with IDs: " + ids);
    }
}
