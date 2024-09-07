package com.microservice.stock.domain.api;

import com.microservice.stock.domain.model.Brand;
import com.microservice.stock.domain.util.Pagination;

public interface IBrandServicePort {
    void createBrand(Brand brand);
    Pagination<Brand> listBrands(Integer pageNumber, Integer pageSize, String sortBy, String sortDirection);
}
