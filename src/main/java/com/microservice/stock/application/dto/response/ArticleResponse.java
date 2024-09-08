package com.microservice.stock.application.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;
import java.util.Map;

@Getter
@AllArgsConstructor
public class ArticleResponse {
    private Long id;
    private String name;
    private String description;
    private double price;
    private Integer quantity;
    private List<Map<String, Object>> categories;
    private String brand;
}
