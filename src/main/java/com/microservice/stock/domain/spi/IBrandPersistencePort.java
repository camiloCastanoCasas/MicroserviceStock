package com.microservice.stock.domain.spi;

import com.microservice.stock.domain.model.Brand;
import com.microservice.stock.domain.util.Pagination;

public interface IBrandPersistencePort {
    void createBrand(Brand brand);
    boolean existsByName(String name);
    boolean existById(Long id);
    Pagination<Brand> listBrands(Integer pageNumber, Integer pageSize, String sortBy, String sortDirection);
}
