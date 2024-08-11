package com.sparta.msa_exam.order;

import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/order")
@RequiredArgsConstructor
public class OrderController {

    @Value("${server.port}")
    private String serverPort;
    private final OrderService orderService;

    @PostMapping
    public ResponseEntity<Long> create(@RequestBody OrderDto dto) {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Server-port", serverPort);
        return ResponseEntity.ok()
                .headers(headers)
                .body(orderService.create(dto));
    }

    @GetMapping("/{orderId}")
    public ResponseEntity<OrderDto> readOne(@PathVariable(name = "orderId") Long orderId) {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Server-port", serverPort);
        return ResponseEntity.ok()
                .headers(headers)
                .body(orderService.readOne(orderId));
    }

    @PutMapping("/{orderId}")
    public ResponseEntity<Long> update(@PathVariable(name = "orderId") Long orderId, @RequestBody Long productId) {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Server-port", serverPort);
        return ResponseEntity.ok()
                .headers(headers)
                .body(orderService.update(orderId, productId));
    }
}
