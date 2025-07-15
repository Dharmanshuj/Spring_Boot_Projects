package com.practice.companies.companies.Controllers;

import com.practice.companies.companies.DTO.ProductItemDTO;
import com.practice.companies.companies.Service.ProductItemService;
import com.practice.companies.companies.Wrapper.ProductItemWrapper;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/items")
public class ProductItemController {
    private final ProductItemService service;

    @GetMapping
    public ResponseEntity<List<ProductItemDTO>> getAll() {
        return ResponseEntity.ok(service.getAll());
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<ProductItemDTO> getByID(@PathVariable Integer id) {
        return ResponseEntity.ok(service.getById(id));
    }

    @PostMapping(path = "/create")
    public ResponseEntity<ProductItemDTO> saveItem(@Valid @RequestBody ProductItemDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.saveItem(dto));
    }

    @PostMapping(path = "/createAll")
    public ResponseEntity<List<ProductItemDTO>> saveAllItems(@Valid @RequestBody ProductItemWrapper w) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.saveAllItems(w.getItems()));
    }

    @PutMapping(path = "/update")
    public ResponseEntity<ProductItemDTO> updateItem(@RequestParam Integer id, @Valid @RequestBody ProductItemDTO dto) {
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(service.updateItem(id, dto));
    }

    @PutMapping(path = "/updateAll")
    public ResponseEntity<List<ProductItemDTO>> updateAllItems(@Valid @RequestBody ProductItemWrapper w) {
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(service.updateAllItems(w.getItems()));
    }

    @DeleteMapping(path = "/delete")
    public ResponseEntity<String> deleteItem(@RequestParam Integer id) {
        service.deleteItem(id);
        return ResponseEntity.ok("Deleted the Item with ID: " + id);
    }

    @DeleteMapping(path = "/deleteAll")
    public ResponseEntity<String> deleteAllItems(@RequestBody List<Integer> ids) {
        service.deleteAllItems(ids);
        return ResponseEntity.ok("Deleted all items with IDs: " + ids);
    }
}
