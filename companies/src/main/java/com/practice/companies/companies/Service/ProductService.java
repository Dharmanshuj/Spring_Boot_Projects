package com.practice.companies.companies.Service;

import com.practice.companies.companies.DTO.DTOUtility;
import com.practice.companies.companies.DTO.ProductDTO;
import com.practice.companies.companies.Entity.Company;
import com.practice.companies.companies.Entity.Product;
import com.practice.companies.companies.ExectionHandling.NotFoundException;
import com.practice.companies.companies.Repository.CompanyRepository;
import com.practice.companies.companies.Repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;
    private final CompanyRepository companyRepository;
    private final DTOUtility mapper;

    public ProductDTO getProduct(Integer id) {
        Product p = productRepository.findByIdAndStatusTrue(id)
                .orElseThrow(() -> new NotFoundException("Product not found with ID: " + id));
        return mapper.toProductDTO(p);
    }

    public List<ProductDTO> getAllProducts() {
        List<Product> products = productRepository.findAllByStatusTrue();
        return products.stream().map(mapper::toProductDTO).toList();
    }

    public ProductDTO saveProduct(ProductDTO p) {
        Company company = companyRepository.findByIdAndStatusTrue(p.getCompanyId())
                .orElseThrow(() -> new NotFoundException("Company not found with ID: " + p.getCompanyId()));
        Product product = mapper.toProductEntity(p, company);
        return mapper.toProductDTO(productRepository.save(product));
    }

    public List<ProductDTO> saveAllProducts(List<ProductDTO> dtos) {
        List<Integer> companyIds = dtos.stream().map(ProductDTO::getCompanyId).toList();
        Map<Integer, Company> companyMap = companyRepository.findAllByIdInAndStatusTrue(companyIds).stream()
                .collect(Collectors.toMap(Company::getId, c->c));
        List<Integer> missing = companyIds.stream().filter(id -> !companyMap.containsKey(id)).toList();
        if(!missing.isEmpty()) {
            throw new NotFoundException("Companies not found with IDs: " + missing);
        }

        List<Product> products = dtos.stream()
                .map(dto -> mapper.toProductEntity(dto, companyMap.get(dto.getCompanyId())))
                .toList();
        return productRepository.saveAll(products).stream().map(mapper::toProductDTO).toList();
    }

    public ProductDTO updateProduct(Integer id, ProductDTO dto) {
        Product product = productRepository.findByIdAndStatusTrue(id)
                .orElseThrow(() -> new NotFoundException("Product not found with ID: " + id));
        Company company = companyRepository.findByIdAndStatusTrue(dto.getCompanyId())
                .orElseThrow(() -> new NotFoundException("Company not found with ID: " + dto.getCompanyId()));
        Product updated = mapper.toProductEntity(dto, company);
        updated.setId(id);
        updated.setCreatedDate(product.getCreatedDate());
        return mapper.toProductDTO(productRepository.save(updated));
    }

    public List<ProductDTO> updateAllProducts(List<ProductDTO> dtos) {
        List<Integer> productIds = dtos.stream().map(ProductDTO::getId).toList();
        List<Integer> companyIds = dtos.stream().map(ProductDTO::getCompanyId).toList();

        Map<Integer, Product> productMap = productRepository.findAllByIdInAndStatusTrue(productIds).stream()
                .collect(Collectors.toMap(Product::getId, p -> p));
        Map<Integer, Company> companyMap = companyRepository.findAllByIdInAndStatusTrue(companyIds).stream()
                .collect(Collectors.toMap(Company::getId, c -> c));

        List<Integer> missingProducts = dtos.stream().map(ProductDTO::getId).filter(id -> !productMap.containsKey(id)).toList();
        if(!missingProducts.isEmpty()) {
            throw new NotFoundException("Products not found with IDs: " + missingProducts);
        }

        List<Integer> missingCompany = dtos.stream().map(ProductDTO::getCompanyId).filter(id -> !companyMap.containsKey(id)).toList();
        if(!missingCompany.isEmpty()) {
            throw new NotFoundException("Companies not found with IDs: " + missingCompany);
        }

        List<Product> updates = dtos.stream().map(dto -> {
            return productMap.get(dto.getId());
        }).toList();

        return productRepository.saveAll(updates).stream().map(mapper::toProductDTO).toList();
    }

    public void deleteProduct(Integer id) {
        Product p = productRepository.findByIdAndStatusTrue(id)
                .orElseThrow(() -> new NotFoundException("Product not found with ID:" + id));
        p.setStatus(false);
        p.setUpdatedDate(LocalDate.now());
        productRepository.save(p);
    }

    public void deleteAllProducts(List<Integer> ids) {
        List<Product> products = productRepository.findAllByIdInAndStatusTrue(ids);
        Set<Integer> found = products.stream().map(Product::getId).collect(Collectors.toSet());
        List<Integer> missing = ids.stream().filter(id -> !found.contains(id)).toList();

        if(!missing.isEmpty()) {
            throw new NotFoundException("Products not found with IDs: " + missing);
        }

        products.forEach(product -> {
            product.setStatus(false);
            product.setUpdatedDate(LocalDate.now());
        });

        productRepository.saveAll(products);
    }

    public List<ProductDTO> getProductsByCompany(Integer companyId) {
        return productRepository.findAllByCompanyIdAndStatusTrue(companyId).stream()
                .map(mapper::toProductDTO).toList();
    }
}
