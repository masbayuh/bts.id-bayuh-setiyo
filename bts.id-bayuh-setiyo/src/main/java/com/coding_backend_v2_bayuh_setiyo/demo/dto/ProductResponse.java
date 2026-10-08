package com.coding_backend_v2_bayuh_setiyo.demo.dto;

import com.coding_backend_v2_bayuh_setiyo.demo.entity.Product;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public record ProductResponse (
        Long id,
        String title,
        BigDecimal price,
        String description,
        String category,
        List<String> images,
        @JsonProperty("created_at") @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime createdAt,
        @JsonProperty("created_by") String createdBy,
        @JsonProperty("created_by_id") Long createdById,
        @JsonProperty("updated_at") @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime updatedAt,
        @JsonProperty("updated_by") String updatedBy,
        @JsonProperty("updated_by_id") Long updatedById
)  {
    public static ProductResponse from(Product p) {
        return new ProductResponse(
                p.getId(), p.getTitle(), p.getPrice(), p.getDescription(), p.getCategory(),
                new ArrayList<>(p.getImages()),
                p.getCreatedAt(), p.getCreatedBy(), p.getCreatedById(),
                p.getUpdatedAt(), p.getUpdatedBy(), p.getUpdatedById());
    }
}
