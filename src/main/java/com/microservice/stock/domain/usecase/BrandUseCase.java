package com.microservice.stock.domain.usecase;

import com.microservice.stock.domain.api.IBrandServicePort;
import com.microservice.stock.domain.exceptions.ValidationException;
import com.microservice.stock.domain.model.Brand;
import com.microservice.stock.domain.spi.IBrandPersistencePort;
import com.microservice.stock.domain.util.DomainConstants;

import java.util.ArrayList;

public class BrandUseCase implements IBrandServicePort {

    private final IBrandPersistencePort brandPersistencePort;

    public BrandUseCase(IBrandPersistencePort brandPersistencePort) {
        this.brandPersistencePort = brandPersistencePort;
    }

    @Override
    public void createBrand(Brand brand) {
        ArrayList<String> errors = new ArrayList<>();

        if (brand.getName().trim().isEmpty()) {
            errors.add(DomainConstants.FIELD_NAME_NULL_MESSAGE);
        }
        if (brand.getName().length() > DomainConstants.FIELD_NAME_BRAND_SIZE_MAX) {
            errors.add(DomainConstants.FIELD_NAME_BRAND_SIZE_MESSAGE);
        }
        if (brand.getDescription().trim().isEmpty()) {
            errors.add(DomainConstants.FIELD_DESCRIPTION_NULL_MESSAGE);
        }
        if (brand.getDescription().length() > DomainConstants.FIELD_DESCRIPTION_BRAND_SIZE_MAX) {
            errors.add(DomainConstants.FIELD_DESCRIPTION_BRAND_SIZE_MESSAGE);
        }
        if (brandPersistencePort.existsByName(brand.getName())) {
            errors.add(DomainConstants.BRAND_EXISTS_MESSAGE);
        }
        if (!errors.isEmpty()) {
            throw new ValidationException(errors);
        }
        brandPersistencePort.createBrand(brand);
    }


}
