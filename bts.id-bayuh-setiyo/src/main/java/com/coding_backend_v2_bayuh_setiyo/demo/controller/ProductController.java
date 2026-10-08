package com.coding_backend_v2_bayuh_setiyo.demo.controller;

import com.coding_backend_v2_bayuh_setiyo.demo.dto.PageResponse;
import com.coding_backend_v2_bayuh_setiyo.demo.dto.ProductRequest;
import com.coding_backend_v2_bayuh_setiyo.demo.dto.ProductResponse;
import com.coding_backend_v2_bayuh_setiyo.demo.services.ProductService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    @GetMapping
    public PageResponse<ProductResponse> list(
            @RequestParam(required = false) String search,
            @RequestParam(required = false) String category,
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int limit) {
        return productService.findAll(search, category, page, limit);
    }

    @GetMapping("/{id}")
    public ProductResponse detail(@PathVariable Long id) {
        return productService.findById(id);
    }

    @PostMapping
    public ResponseEntity<ProductResponse> create(@Valid @RequestBody ProductRequest req) {
        return ResponseEntity.status(HttpStatus.CREATED).body(productService.create(req));
    }

    @PutMapping("/{id}")
    public ProductResponse update(@PathVariable Long id, @Valid @RequestBody ProductRequest req) {
        return productService.update(id, req);
    }

    @DeleteMapping("/{id}")
    public Map<String, String> delete(@PathVariable Long id) {
        productService.delete(id);
        return Map.of("message", "Produk berhasil dihapus");
    }
}
