package com.coding_backend_v2_bayuh_setiyo.demo.repository;

import com.coding_backend_v2_bayuh_setiyo.demo.entity.Product;
import org.springframework.data.jpa.domain.Specification;

public final class ProductSpecification {
    private ProductSpecification() {}

    public static Specification<Product> filter(String search, String category) {
        return (root, query, cb) -> {
            var predicate = cb.conjunction();

            if (search != null && !search.isBlank()) {
                predicate = cb.and(predicate,
                        cb.like(cb.lower(root.get("title")),
                                "%" + search.trim().toLowerCase() + "%"));
            }
            if (category != null && !category.isBlank()) {
                predicate = cb.and(predicate,
                        cb.equal(cb.lower(root.get("category")),
                                category.trim().toLowerCase()));
            }
            return predicate;
        };
    }
}
