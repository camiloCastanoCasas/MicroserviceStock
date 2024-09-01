package com.microservice.stock.domain.spi;

import com.microservice.stock.domain.model.Brand;
import com.microservice.stock.domain.util.Pagination;

public interface IBrandPersistencePort {
    void createBrand(Brand brand);
    boolean existsByName(String name);
    Pagination<Brand> listBrands(int pageNumber, int pageSize, String sortBy, String sortDirection);
}
