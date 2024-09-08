package com.microservice.stock.application.handler;

import com.microservice.stock.application.dto.request.BrandRequest;
import com.microservice.stock.application.dto.response.BrandResponse;
import com.microservice.stock.application.dto.response.PaginationResponse;
import com.microservice.stock.application.mapper.IBrandRequestMapper;
import com.microservice.stock.application.mapper.IBrandResponseMapper;
import com.microservice.stock.domain.api.IBrandServicePort;
import com.microservice.stock.domain.model.Brand;
import com.microservice.stock.domain.util.Pagination;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class BrandHandler implements IBrandHandler {

    private final IBrandServicePort brandServicePort;
    private final IBrandRequestMapper brandRequestMapper;
    private final IBrandResponseMapper brandResponseMapper;

    @Override
    public void createBrand(BrandRequest brandRequest) {
        Brand brand = brandRequestMapper.toBrand(brandRequest);
        brandServicePort.createBrand(brand);
    }

    @Override
    public PaginationResponse<BrandResponse> listBrands(Integer pageNumber, Integer pageSize, String sortBy, String sortDirection) {

        Pagination<Brand> brandPagination = brandServicePort.listBrands(pageNumber,pageSize,sortBy,sortDirection);

        List<BrandResponse> brandResponses = brandPagination.getContent().stream()
                .map(brandResponseMapper::toBrandResponse)
                .toList();

        Pagination<BrandResponse> responsePagination = new Pagination<>(
                brandResponses,
                brandPagination.getPageNumber(),
                brandPagination.getPageSize(),
                brandPagination.getTotalElements()
        );
        return brandResponseMapper.toPaginationResponse(responsePagination);
    }
}
