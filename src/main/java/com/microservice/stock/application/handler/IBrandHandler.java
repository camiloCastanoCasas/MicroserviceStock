package com.microservice.stock.application.handler;

import com.microservice.stock.application.dto.request.BrandRequest;

public interface IBrandHandler {
    void createBrand(BrandRequest BrandRequest);
}
