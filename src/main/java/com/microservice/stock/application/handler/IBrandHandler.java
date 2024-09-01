package com.microservice.stock.application.handler;

import com.microservice.stock.application.dto.request.BrandRequest;
import com.microservice.stock.application.dto.response.BrandResponse;
import com.microservice.stock.application.dto.response.PaginationResponse;

public interface IBrandHandler {
    void createBrand(BrandRequest brandRequest);
    PaginationResponse<BrandResponse> listBrands(int pageNumber, int pageSize, String sortBy, String sortDirection);
}
