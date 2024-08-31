package com.microservice.stock.domain.spi;

import com.microservice.stock.domain.model.Brand;

public interface IBrandPersistencePort {
    void createBrand(Brand brand);
    boolean existsByName(String name);
}
