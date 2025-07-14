package com.practice.companies.companies.Controllers;

import com.practice.companies.companies.DTO.CompanyDTO;
import com.practice.companies.companies.Service.CompanyService;
import com.practice.companies.companies.Wrapper.CompanyWrapper;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/company")
public class CompanyController {
    @Autowired
    CompanyService service;

    @GetMapping(path = "/{id}")
    public ResponseEntity<CompanyDTO> getById(@PathVariable Integer id) {
        return ResponseEntity.ok(service.getCompany(id));
    }

    @GetMapping
    public ResponseEntity<List<CompanyDTO>> getAll() {
        return ResponseEntity.ok(service.getAllCompanies());
    }

    @PostMapping(path = "/create")
    public ResponseEntity<CompanyDTO> saveCompany(@Valid @RequestBody CompanyDTO c) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.createCompany(c));
    }

    @PostMapping(path = "/createAll")
    public ResponseEntity<List<CompanyDTO>> saveCompanies(@Valid @RequestBody CompanyWrapper w) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.createCompanies(w.getCompanies()));
    }

    @PutMapping(path = "/update")
    public ResponseEntity<CompanyDTO> updateCompany(@RequestParam Integer id, @Valid @RequestBody CompanyDTO c) {
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(service.updateCompany(id, c));
    }

    @PutMapping(path = "/updateAll")
    public ResponseEntity<List<CompanyDTO>> updateCompanies(@Valid @RequestBody CompanyWrapper w) {
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(service.updateCompanies(w.getCompanies()));
    }

    @DeleteMapping(path = "/delete")
    public ResponseEntity<String> deleteCompany(@RequestParam Integer id) {
        service.deleteCompany(id);
        return ResponseEntity.ok("Deleted the Company with ID:" + id);
    }

    @DeleteMapping(path = "/deleteAll")
    public ResponseEntity<String> deleteCompanies(@RequestBody List<Integer> ids) {
        service.deleteAllCompany(ids);
        return ResponseEntity.ok("Deleted All Companies with IDs: " + ids);
    }
}
