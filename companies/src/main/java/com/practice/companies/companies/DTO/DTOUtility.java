package com.practice.companies.companies.DTO;

import com.practice.companies.companies.Entity.Company;
import com.practice.companies.companies.Entity.Product;
import com.practice.companies.companies.Entity.ProductItem;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class DTOUtility {
    public CompanyDTO toCompanyDTO(Company entity) {
        return CompanyDTO.builder()
                .id(entity.getId())
                .name(entity.getName())
                .description(entity.getDescription())
                .status(entity.getStatus())
                .established_year(entity.getEstablished_year())
                .email(entity.getEmail())
                .website_url(entity.getWebsite_url())
                .createdDate(entity.getCreatedDate())
                .updatedDate(entity.getUpdatedDate())
                .products(entity.getProducts() != null ?
                        entity.getProducts().stream()
                                .filter(Product::getStatus)
                                .map(this::toProductSummary)
                                .toList() :
                        List.of()).build();
    }

    public Company toCompanyEntity(CompanyDTO dto) {
        return Company.builder()
                .id(dto.getId())
                .name(dto.getName())
                .description(dto.getDescription())
                .status(dto.getStatus() != null ? dto.getStatus() : true)
                .established_year(dto.getEstablished_year())
                .email(dto.getEmail())
                .website_url(dto.getWebsite_url())
                .build();
    }

    public ProductDTO toProductDTO(Product entity) {
        return ProductDTO.builder()
                .id(entity.getId())
                .name(entity.getName())
                .description(entity.getDescription())
                .status(entity.getStatus())
                .type(entity.getType())
                .price(entity.getPrice())
                .companyId(entity.getCompany() != null ? entity.getCompany().getId() : null)
                .createdDate(entity.getCreatedDate())
                .updatedDate(entity.getUpdatedDate())
                .items(entity.getItems() != null ?
                        entity.getItems().stream()
                                .filter(ProductItem::getStatus)
                                .map(this::toProductItemSummary)
                                .toList() :
                        null)
                .build();
    }

    public Product toProductEntity(ProductDTO dto, Company company) {
        return Product.builder()
                .id(dto.getId())
                .name(dto.getName())
                .description(dto.getDescription())
                .status(dto.getStatus() != null ? dto.getStatus() : true)
                .type(dto.getType())
                .price(dto.getPrice())
                .company(company)
                .build();
    }

    public ProductItemDTO toProductItemDTO(ProductItem entity) {
        return ProductItemDTO.builder()
                .id(entity.getId())
                .name(entity.getName())
                .description(entity.getDescription())
                .status(entity.getStatus())
                .productId(entity.getProduct() != null ? entity.getProduct().getId() : null)
                .createdDate(entity.getCreatedDate())
                .updatedDate(entity.getUpdatedDate())
                .build();
    }

    public ProductItem toProductItemEntity(ProductItemDTO dto, Product product) {
        return ProductItem.builder()
                .id(dto.getId())
                .name(dto.getName())
                .description(dto.getDescription())
                .status(dto.getStatus() != null ? dto.getStatus() : true)
                .product(product)
                .build();
    }

    public ProductSummary toProductSummary(Product p) {
        return new ProductSummary(p.getId(), p.getName());
    }

    public ProductItemSummary toProductItemSummary(ProductItem item) {
        return new ProductItemSummary(item.getId(), item.getName());
    }

    public ProductWithItemSummary toProductWithItemSummary(Product product) {
        return new ProductWithItemSummary(
                product.getId(),
                product.getName(),
                product.getItems() != null ?
                        product.getItems().stream()
                                .filter(ProductItem::getStatus)
                                .map(this::toProductItemSummary)
                                .toList()
                        : List.of()
        );
    }

}
