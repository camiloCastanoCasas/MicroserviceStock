package com.microservice.stock.application.handler;

import com.microservice.stock.application.dto.request.BrandRequest;
import com.microservice.stock.application.mapper.IBrandRequestMapper;
import com.microservice.stock.domain.api.IBrandServicePort;
import com.microservice.stock.domain.model.Brand;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Transactional
public class BrandHandler implements IBrandHandler {

    private final IBrandServicePort brandServicePort;
    private final IBrandRequestMapper brandRequestMapper;

    @Override
    public void createBrand(BrandRequest brandRequest) {
        Brand brand = brandRequestMapper.toBrand(brandRequest);
        brandServicePort.createBrand(brand);
    }
}
