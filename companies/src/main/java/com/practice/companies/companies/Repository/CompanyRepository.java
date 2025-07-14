package com.practice.companies.companies.Repository;

import com.practice.companies.companies.Entity.Company;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CompanyRepository extends JpaRepository<Company, Integer> {
    Optional<Company> findByEmailAndStatusTrue(String email);
    List<Company> findAllByEmailInAndStatusTrue(List<String> emails);
    Optional<Company> findByEmailAndIdNotAndStatusTrue(String email, Integer id);
    List<Company> findAllByStatusTrue();
    Optional<Company> findByIdAndStatusTrue(Integer id);
    List<Company> findAllByIdInAndStatusTrue(List<Integer> ids);
}
