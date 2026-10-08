package com.coding_backend_v2_bayuh_setiyo.demo.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public record PageResponse<T> (
        List<T> data,
        int page,
        int limit,
        @JsonProperty("total_items") long totalItems,
        @JsonProperty("total_pages") int totalPages
) {
}
