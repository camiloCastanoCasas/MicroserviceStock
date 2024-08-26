package com.microservice.stock.application.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class BrandRequest {
    private final String name;
    private final String description;
}
