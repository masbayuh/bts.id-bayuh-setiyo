package com.coding_backend_v2_bayuh_setiyo.demo.dto;

import jakarta.validation.constraints.NotBlank;

public record LoginRequest (
        @NotBlank String username,
        @NotBlank String password
) {
}
