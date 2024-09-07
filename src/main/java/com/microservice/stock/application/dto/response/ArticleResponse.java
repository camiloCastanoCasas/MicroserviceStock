package com.microservice.stock.application.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class ArticleResponse {
    private Long id;
    private String name;
    private String description;
    private double price;
    private Integer quantity;
    private List<String> categories;
    private String brand;
}
