package com.practice.companies.companies.Service;

import com.practice.companies.companies.DTO.CompanyDTO;
import com.practice.companies.companies.DTO.DTOUtility;
import com.practice.companies.companies.Entity.Company;
import com.practice.companies.companies.ExectionHandling.AlreadyExistException;
import com.practice.companies.companies.ExectionHandling.NotFoundException;
import com.practice.companies.companies.Repository.CompanyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CompanyService {
    private final CompanyRepository companyRepository;
    private final DTOUtility mapper;

    public CompanyDTO getCompany(Integer id) {
        return mapper.toCompanyDTO(companyRepository.findByIdAndStatusTrue(id).orElseThrow(() -> new NotFoundException("Company not found for ID: " + id)));
    }

    public List<CompanyDTO> getAllCompanies() {
        List<CompanyDTO> dtos = companyRepository.findAllByStatusTrue().stream().map(mapper::toCompanyDTO).toList();
        if(dtos.isEmpty()) {
            throw new NotFoundException("No record found");
        }
        return dtos;
    }

    public CompanyDTO createCompany(CompanyDTO c) {
        companyRepository.findByEmailAndStatusTrue(c.getEmail()).ifPresent(u -> {
            throw new AlreadyExistException("Email already in use: " + c.getEmail());
        });
        Company company = mapper.toCompanyEntity(c);
        return mapper.toCompanyDTO(companyRepository.save(company));
    }

    public List<CompanyDTO> createCompanies(List<CompanyDTO> dtos) {
        List<String> emails = dtos.stream().map(CompanyDTO::getEmail).toList();
        List<Company> companies = companyRepository.findAllByEmailInAndStatusTrue(emails);
        if(!companies.isEmpty()) {
            String error = companies.stream().map(Company::getEmail).collect(Collectors.joining(", "));
            throw new AlreadyExistException("Emails Already Exists: " + error);
        }
        return companyRepository.saveAll(dtos.stream().map(mapper::toCompanyEntity).toList()).stream().map(mapper::toCompanyDTO).toList();
    }

    public CompanyDTO updateCompany(Integer id, CompanyDTO dto) {
        Company company = companyRepository.findByIdAndStatusTrue(id).orElseThrow(() -> new NotFoundException("Company not found for ID: " + id));
        companyRepository.findByEmailAndIdNotAndStatusTrue(dto.getEmail(), id).ifPresent(e -> {
            throw new AlreadyExistException("Email already in use: " + e.getEmail());
        });
        Company updated = mapper.toCompanyEntity(dto);
        updated.setId(id);
        updated.setCreatedDate(company.getCreatedDate());
        return mapper.toCompanyDTO(companyRepository.save(updated));
    }

    public List<CompanyDTO> updateCompanies(List<CompanyDTO> dtos) {
        List<Integer> ids = dtos.stream().map(CompanyDTO::getId).toList();
        List<String> emails = dtos.stream().map(CompanyDTO::getEmail).toList();

        List<Company> companies = companyRepository.findAllByIdInAndStatusTrue(ids);
        Map<Integer, Company> idToEntity = companies.stream().collect(Collectors.toMap(Company::getId, c -> c));

        List<Company> availableEmails = companyRepository.findAllByEmailInAndStatusTrue(emails);
        Map<String, Integer> emailToId = availableEmails.stream().filter(e -> !ids.contains(e.getId())).collect(Collectors.toMap(Company::getEmail, Company::getId));

        if(idToEntity.size() != dtos.size()) {
            List<Integer> missing = ids.stream().filter(id -> !idToEntity.containsKey(id)).toList();
            throw new NotFoundException("Company Not Found with IDs: " + missing);
        }

        List<Company> updates = new ArrayList<>();
        for(CompanyDTO c : dtos) {
            Integer id = c.getId();
            String email = c.getEmail();

            if(emailToId.containsKey(email) && !Objects.equals(emailToId.get(email), id)) {
                throw new AlreadyExistException("Emails already exists with another Company: " + email);
            }

            Company current = idToEntity.get(id);
            Company updated = mapper.toCompanyEntity(c);
            updated.setId(current.getId());
            updated.setCreatedDate(current.getCreatedDate());

            updates.add(updated);
        }

        return companyRepository.saveAll(updates).stream().map(mapper::toCompanyDTO).toList();
    }

    public void deleteCompany(Integer id) {
        Company c = companyRepository.findByIdAndStatusTrue(id).orElseThrow(() -> new NotFoundException("User Not Found"));
        c.setStatus(false);
        c.setUpdatedDate(LocalDate.now());
        companyRepository.save(c);
    }

    public void deleteAllCompany(List<Integer> ids) {
        List<Company> companies = companyRepository.findAllByIdInAndStatusTrue(ids);
        Set<Integer> found = companies.stream().map(Company::getId).collect(Collectors.toSet());
        List<Integer> missing = ids.stream().filter(id -> !found.contains(id)).toList();

        if(!missing.isEmpty()) {
            throw new NotFoundException("Companies not found for IDs: " + missing);
        }

        companies.forEach(company -> {
            company.setStatus(false);
            company.setUpdatedDate(LocalDate.now());
        });

        companyRepository.saveAll(companies);
    }
}
