package com.coding_backend_v2_bayuh_setiyo.demo.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;
import java.util.List;

public record ProductRequest (
        @NotBlank(message = "title wajib diisi") String title,
        @NotNull(message = "price wajib diisi") @Positive(message = "price harus lebih dari 0") BigDecimal price,
        String description,
        @NotBlank(message = "category wajib diisi") String category,
        @NotEmpty(message = "images minimal 1") List<@NotBlank(message = "url image tidak boleh kosong") String> images
) {
}
