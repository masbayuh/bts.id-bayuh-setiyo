package com.coding_backend_v2_bayuh_setiyo.demo.services;

import com.coding_backend_v2_bayuh_setiyo.demo.dto.PageResponse;
import com.coding_backend_v2_bayuh_setiyo.demo.dto.ProductRequest;
import com.coding_backend_v2_bayuh_setiyo.demo.dto.ProductResponse;
import com.coding_backend_v2_bayuh_setiyo.demo.entity.Product;
import com.coding_backend_v2_bayuh_setiyo.demo.entity.User;
import com.coding_backend_v2_bayuh_setiyo.demo.exception.NotFoundException;
import com.coding_backend_v2_bayuh_setiyo.demo.repository.ProductRepository;
import com.coding_backend_v2_bayuh_setiyo.demo.repository.ProductSpecification;
import com.coding_backend_v2_bayuh_setiyo.demo.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

@Service
@RequiredArgsConstructor
public class ProductService {
    private static final int MAX_LIMIT = 100;

    private final ProductRepository productRepository;
    private final UserRepository userRepository;

    @Transactional(readOnly = true)
    public PageResponse<ProductResponse> findAll(String search, String category, int page, int limit) {
        int safePage = Math.max(page, 1);
        int safeLimit = Math.min(Math.max(limit, 1), MAX_LIMIT);

        Page<Product> result = productRepository.findAll(
                ProductSpecification.filter(search, category),
                PageRequest.of(safePage - 1, safeLimit, Sort.by("id").ascending()));

        return new PageResponse<>(
                result.getContent().stream().map(ProductResponse::from).toList(),
                safePage, safeLimit, result.getTotalElements(), result.getTotalPages());
    }

    @Transactional(readOnly = true)
    public ProductResponse findById(Long id) {
        return ProductResponse.from(getOrThrow(id));
    }

    @Transactional
    public ProductResponse create(ProductRequest req) {
        User user = currentUser();

        Product p = new Product();
        apply(p, req);
        p.setCreatedBy(user.getUsername());
        p.setCreatedById(user.getId());
        p.setUpdatedBy(user.getUsername());
        p.setUpdatedById(user.getId());

        return ProductResponse.from(productRepository.save(p));
    }

    @Transactional
    public ProductResponse update(Long id, ProductRequest req) {
        Product p = getOrThrow(id);
        User user = currentUser();

        apply(p, req);
        p.setUpdatedBy(user.getUsername());
        p.setUpdatedById(user.getId());

        return ProductResponse.from(productRepository.save(p));
    }

    @Transactional
    public void delete(Long id) {
        productRepository.delete(getOrThrow(id));
    }

    private Product getOrThrow(Long id) {
        return productRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Produk dengan id " + id + " tidak ditemukan"));
    }

    private void apply(Product p, ProductRequest req) {
        p.setTitle(req.title());
        p.setPrice(req.price());
        p.setDescription(req.description());
        p.setCategory(req.category());
        p.getImages().clear();
        p.getImages().addAll(req.images());
    }

    private User currentUser() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth == null || auth.getName() == null) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Belum login");
        }
        return userRepository.findByUsername(auth.getName())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED, "User tidak valid"));
    }
}
