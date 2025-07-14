package com.practice.companies.companies.Repository;

import com.practice.companies.companies.Entity.ProductItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductItemRepository extends JpaRepository<ProductItem, Integer> {
    List<ProductItem> findAllByStatusTrue();
    Optional<ProductItem> findByIdAndStatusTrue(Integer id);
    List<ProductItem> findAllByIdInAndStatusTrue(List<Integer> ids);
    List<ProductItem> findByProductIdAndStatusTrue(Integer productId);
}
