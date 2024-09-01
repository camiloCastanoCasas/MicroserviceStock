package com.microservice.stock.application.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PaginationCategoryResponse {
    private List<CategoryResponse> content;
    private int page;
    private int size;
    private long totalElements;
    private int totalPages;
}
