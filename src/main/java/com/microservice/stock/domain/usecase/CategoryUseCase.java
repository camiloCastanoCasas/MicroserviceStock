package com.microservice.stock.domain.usecase;

import com.microservice.stock.domain.api.ICategoryServicePort;
import com.microservice.stock.domain.exceptions.ValidationException;
import com.microservice.stock.domain.model.Category;
import com.microservice.stock.domain.spi.ICategoryPersistencePort;
import com.microservice.stock.domain.util.DomainConstants;
import com.microservice.stock.domain.util.Pagination;

import java.util.ArrayList;

public class CategoryUseCase implements ICategoryServicePort {

    private final ICategoryPersistencePort categoryPersistencePort;

    public CategoryUseCase(ICategoryPersistencePort categoryPersistencePort){
        this.categoryPersistencePort = categoryPersistencePort;
    }

    @Override
    public void createCategory(Category category) {
        ArrayList<String> errors = new ArrayList<>();

        if (category.getName().trim().isEmpty()) {
            errors.add(DomainConstants.FIELD_NAME_NULL_MESSAGE);
        }
        if (category.getName().length() > DomainConstants.FIELD_NAME_CATEGORY_SIZE_MAX) {
            errors.add(DomainConstants.FIELD_NAME_CATEGORY_SIZE_MESSAGE);
        }
        if (category.getDescription().trim().isEmpty()) {
            errors.add(DomainConstants.FIELD_DESCRIPTION_NULL_MESSAGE);
        }
        if (category.getDescription().length() > DomainConstants.FIELD_DESCRIPTION_CATEGORY_SIZE_MAX) {
            errors.add(DomainConstants.FIELD_DESCRIPTION_CATEGORY_SIZE_MESSAGE);
        }
        if (categoryPersistencePort.existsByName(category.getName())) {
            errors.add(DomainConstants.CATEGORY_EXISTS_MESSAGE);
        }
        if (!errors.isEmpty()) {
            throw new ValidationException(errors);
        }
        categoryPersistencePort.createCategory(category);
    }

    @Override
    public Pagination<Category> listCategory(int pageNumber, int pageSize, String sortby, String sortDirection) {
        ArrayList<String> errors = new ArrayList<>();

        if (pageNumber < 0) {
            errors.add(DomainConstants.INVALID_PAGE_NUMBER_MESSAGE);
        }
        if (pageSize <= 0) {
            errors.add(DomainConstants.INVALID_PAGE_SIZE_MESSAGE);
        }
        if (!sortby.equalsIgnoreCase(DomainConstants.VALID_SORT_FIELD)) {
            errors.add(DomainConstants.INVALID_SORT_FIELD_MESSAGE);
        }
        if (!sortDirection.equalsIgnoreCase(DomainConstants.ORDER_ASC) && !sortDirection.equalsIgnoreCase(DomainConstants.ORDER_DESC)) {
            errors.add(DomainConstants.INVALID_SORT_DIRECTION_MESSAGE);
        }
        if (!errors.isEmpty()) {
            throw new ValidationException(errors);
        }
        return categoryPersistencePort.listCategory(pageNumber, pageSize, sortby, sortDirection);
    }
}
