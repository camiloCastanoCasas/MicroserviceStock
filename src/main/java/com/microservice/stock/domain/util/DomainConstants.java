package com.microservice.stock.domain.util;

import java.util.Set;

public final class DomainConstants {

    private DomainConstants(){
        throw new IllegalStateException("Utility class");
    }

    public static final String FIELD_NAME_NULL_MESSAGE = "Field name cannot be null or empty";
    public static final String FIELD_DESCRIPTION_NULL_MESSAGE = "Field description cannot be null or empty";
    public static final String FIELD_NAME_CATEGORY_SIZE_MESSAGE = "Category name cannot be longer than 50 characters";
    public static final String FIELD_NAME_BRAND_SIZE_MESSAGE = "Brand name cannot be longer than 50 characters";
    public static final String FIELD_DESCRIPTION_CATEGORY_SIZE_MESSAGE = "Category description cannot be longer than 90 characters";
    public static final String FIELD_DESCRIPTION_BRAND_SIZE_MESSAGE = "Brand description cannot be longer than 120 characters";
    public static final int FIELD_NAME_CATEGORY_SIZE_MAX = 50;
    public static final int FIELD_NAME_BRAND_SIZE_MAX = 50;
    public static final int FIELD_DESCRIPTION_CATEGORY_SIZE_MAX = 90;
    public static final int FIELD_DESCRIPTION_BRAND_SIZE_MAX = 120;
    public static final String CATEGORY_EXISTS_MESSAGE = "Category already exists";
    public static final String BRAND_EXISTS_MESSAGE = "Brand already exists";

    public static final String INVALID_PAGE_NUMBER_MESSAGE = "The page number must be non-negative.";
    public static final String INVALID_PAGE_SIZE_MESSAGE = "The page size must be greater than zero.";
    public static final String INVALID_SORT_FIELD_MESSAGE = "The sort field is invalid.";
    public static final String INVALID_SORT_DIRECTION_MESSAGE = "The sort direction must be 'asc' or 'desc'.";
    public static final String VALID_SORT_FIELD = "name";
    public static final String ORDER_ASC = "asc";
    public static final String ORDER_DESC = "desc";
}
