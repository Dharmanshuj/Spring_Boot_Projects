package com.practice.companies.companies.Repository;

import com.practice.companies.companies.Entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product, Integer> {
    List<Product> findAllByStatusTrue();
    Optional<Product> findByIdAndStatusTrue(Integer id);
    List<Product> findAllByIdInAndStatusTrue(List<Integer> ids);
    List<Product> findAllByCompanyIdAndStatusTrue(Integer companyId);
}
