package com.microservice.stock.application.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.math.BigDecimal;
import java.util.List;

@AllArgsConstructor
@Getter
public class ArticleRequest {

    @NotBlank(message = "Name is required.")
    private String name;

    @NotBlank(message = "Name is required.")
    private String description;


    private BigDecimal price;


    private int quantity;


    private List<Long> categoryIds;


    private Long brandId;
}
