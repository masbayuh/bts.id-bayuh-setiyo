package com.coding_backend_v2_bayuh_setiyo.demo.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public record AuthResponse (
        @JsonProperty("authentication_token") String authenticationToken,
        @JsonProperty("refresh_token") String refreshToken
) {
}
