package com.microservice.stock.domain.usecase;

import com.microservice.stock.domain.api.IBrandServicePort;
import com.microservice.stock.domain.exceptions.ValidationException;
import com.microservice.stock.domain.model.Brand;
import com.microservice.stock.domain.spi.IBrandPersistencePort;
import com.microservice.stock.domain.util.DomainConstants;
import com.microservice.stock.domain.util.Pagination;

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

    @Override
    public Pagination<Brand> listBrands(int pageNumber, int pageSize, String sortBy, String sortDirection) {
        ArrayList<String> errors = new ArrayList<>();

        if (pageNumber < 0) {
            errors.add(DomainConstants.INVALID_PAGE_NUMBER_MESSAGE);
        }
        if (pageSize <= 0) {
            errors.add(DomainConstants.INVALID_PAGE_SIZE_MESSAGE);
        }
        if (!sortBy.equalsIgnoreCase(DomainConstants.VALID_SORT_FIELD)) {
            errors.add(DomainConstants.INVALID_SORT_FIELD_MESSAGE);
        }
        if (!sortDirection.equalsIgnoreCase(DomainConstants.ORDER_ASC) && !sortDirection.equalsIgnoreCase(DomainConstants.ORDER_DESC)) {
            errors.add(DomainConstants.INVALID_SORT_DIRECTION_MESSAGE);
        }
        if (!errors.isEmpty()) {
            throw new ValidationException(errors);
        }
        return brandPersistencePort.listBrands(pageNumber,pageSize,sortBy,sortDirection);
    }


}
