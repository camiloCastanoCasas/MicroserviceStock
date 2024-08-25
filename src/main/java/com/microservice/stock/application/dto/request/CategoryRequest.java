package com.microservice.stock.application.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class CategoryRequest {
    private final String name;
    private final String description;
}
