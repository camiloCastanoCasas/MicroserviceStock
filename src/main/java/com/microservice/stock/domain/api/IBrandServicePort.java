package com.microservice.stock.domain.api;

import com.microservice.stock.domain.model.Brand;

public interface IBrandServicePort {
    void createBrand(Brand brand);
}
