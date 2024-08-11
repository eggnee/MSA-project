package com.sparta.msa_exam.product;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/products")
public class ProductController {
    @Value("${server.port}")
    private String serverPort;
    private final ProductService productService;

    @PostMapping
    public ResponseEntity<Long> create(@RequestBody ProductDto dto) {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Server-port", serverPort);
        return ResponseEntity.ok()
                .headers(headers)
                .body(productService.create(dto));
    }

    @GetMapping
    public ResponseEntity<List<ProductDto>> readAll() {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Server-port", serverPort);
        return ResponseEntity.ok()
                .headers(headers)
                .body(productService.readAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductDto> readOne(@PathVariable(name = "id") Long productId) {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Server-port", serverPort);
        return ResponseEntity.ok()
                .headers(headers)
                .body(productService.readOne(productId));
    }
}
