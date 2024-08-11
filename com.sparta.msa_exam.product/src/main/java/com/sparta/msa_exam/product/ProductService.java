package com.sparta.msa_exam.product;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;

    public Long create(ProductDto productDto) {
        Product product = Product.builder()
                .name(productDto.getName())
                .supplyPrice(productDto.getSupplyPrice())
                .build();
        return productRepository.save(product).getId();
    }

    public List<ProductDto> readAll() {
        return productRepository.findAll()
                .stream()
                .map(ProductDto::fromEntity)
                .toList();
    }

    public ProductDto readOne(Long productId) {
        Product product = productRepository.findById(productId).orElseThrow(() ->
                new IllegalArgumentException("존재하지 않는 상품입니다."));
        return ProductDto.fromEntity(product);
    }
}
