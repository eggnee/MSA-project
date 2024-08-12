package com.sparta.msa_exam.product;

import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;

    @Transactional
    @CachePut(cacheNames = "productCache", key = "#result")
    @CacheEvict(cacheNames = "productsCache", allEntries = true)
    public Long create(ProductDto productDto) {
        Product product = Product.builder()
                .name(productDto.getName())
                .supplyPrice(productDto.getSupplyPrice())
                .build();
        return productRepository.save(product).getId();
    }

    @Transactional(readOnly = true)
    @Cacheable(cacheNames = "productsCache")
    public List<ProductDto> readAll() {
        return productRepository.findAll()
                .stream()
                .map(ProductDto::fromEntity)
                .toList();
    }

    @Transactional(readOnly = true)
    @Cacheable(cacheNames = "productCache", key = "args[0]")
    public ProductDto readOne(Long productId) {
        Product product = productRepository.findById(productId).orElseThrow(() ->
                new IllegalArgumentException("존재하지 않는 상품입니다."));
        return ProductDto.fromEntity(product);
    }
}
