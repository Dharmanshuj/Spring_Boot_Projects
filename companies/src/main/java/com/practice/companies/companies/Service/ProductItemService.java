package com.practice.companies.companies.Service;

import com.practice.companies.companies.DTO.DTOUtility;
import com.practice.companies.companies.DTO.ProductItemDTO;
import com.practice.companies.companies.Entity.Product;
import com.practice.companies.companies.Entity.ProductItem;
import com.practice.companies.companies.ExectionHandling.NotFoundException;
import com.practice.companies.companies.Repository.ProductItemRepository;
import com.practice.companies.companies.Repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.mail.MailProperties;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductItemService {
    private final ProductItemRepository itemRepository;
    private final ProductRepository productRepository;
    private final DTOUtility mapper;

    public List<ProductItemDTO> getAll() {
        List<ProductItem> items = itemRepository.findAllByStatusTrue();
        return items.stream().map(mapper::toProductItemDTO).toList();
    }

    public ProductItemDTO getById(Integer id) {
        ProductItem item = itemRepository.findByIdAndStatusTrue(id)
                .orElseThrow(() -> new NotFoundException("No item found with ID: " + id));
        return mapper.toProductItemDTO(item);
    }

    public ProductItemDTO saveItem(ProductItemDTO dto) {
        Product product = productRepository.findByIdAndStatusTrue(dto.getProductId())
                .orElseThrow(() -> new NotFoundException("No product found with ID: " + dto.getProductId()));
        ProductItem item = mapper.toProductItemEntity(dto, product);
        return mapper.toProductItemDTO(itemRepository.save(item));
    }

    public List<ProductItemDTO> saveAllItems(List<ProductItemDTO> dtos) {
        List<Integer> productIds = dtos.stream().map(ProductItemDTO::getProductId).toList();
        Map<Integer, Product> productMap = productRepository.findAllByIdInAndStatusTrue(productIds).stream()
                .collect(Collectors.toMap(Product::getId, p -> p));
        List<Integer> missing = productIds.stream().filter(id -> !productMap.containsKey(id)).toList();
        if(!missing.isEmpty()) {
            throw new NotFoundException("Products not found with IDs: " + missing);
        }

        List<ProductItem> items = dtos.stream().map(dto ->
                mapper.toProductItemEntity(dto, productMap.get(dto.getProductId()))).toList();
        return itemRepository.saveAll(items).stream().map(mapper::toProductItemDTO).toList();
    }

    public ProductItemDTO updateItem(Integer id, ProductItemDTO dto) {
        ProductItem item = itemRepository.findByIdAndStatusTrue(id)
                .orElseThrow(() -> new NotFoundException("No Item found with ID: " + id));
        Product product = productRepository.findByIdAndStatusTrue(dto.getProductId())
                .orElseThrow(() -> new NotFoundException("No product found with ID: " + dto.getProductId()));
        ProductItem updated = mapper.toProductItemEntity(dto, product);
        updated.setId(id);
        updated.setCreatedDate(item.getCreatedDate());
        return mapper.toProductItemDTO(itemRepository.save(item));
    }

    public List<ProductItemDTO> updateAllItems(List<ProductItemDTO> dtos) {
        List<Integer> ids = dtos.stream().map(ProductItemDTO::getId).toList();
        Map<Integer, ProductItem> itemMap = itemRepository.findAllByIdInAndStatusTrue(ids).stream()
                .collect(Collectors.toMap(ProductItem::getId, i -> i));

        List<Integer> productIds = dtos.stream().map(ProductItemDTO::getProductId).toList();
        Map<Integer, Product> productMap = productRepository.findAllByIdInAndStatusTrue(productIds).stream()
                .collect(Collectors.toMap(Product::getId, p -> p));

        List<Integer> missingItems = ids.stream().filter(id -> !itemMap.containsKey(id)).toList();
        if(!missingItems.isEmpty()) {
            throw new NotFoundException("Items not found with IDs: " + missingItems);
        }

        List<Integer> missingProducts = productIds.stream().filter(id -> !productMap.containsKey(id)).toList();
        if(!missingProducts.isEmpty()) {
            throw new NotFoundException("Products not found with IDs: " + missingProducts);
        }

        List<ProductItem> items = dtos.stream().map(
                dto -> mapper.toProductItemEntity(dto, productMap.get(dto.getProductId()))).toList();

    }
}
