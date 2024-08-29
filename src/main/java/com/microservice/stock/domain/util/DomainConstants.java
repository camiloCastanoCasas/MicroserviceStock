package com.microservice.stock.domain.util;

public final class DomainConstants {

    private DomainConstants(){
        throw new IllegalStateException("Utility class");
    }

    public static final String FIELD_NAME_NULL_MESSAGE = "Field name cannot be null or empty";
    public static final String FIELD_DESCRIPTION_NULL_MESSAGE = "Field description cannot be null or empty";
    public static final String FIELD_NAME_CATEGORY_SIZE_MESSAGE = "Category name cannot be longer than 50 characters";
    public static final String FIELD_DESCRIPTION_CATEGORY_SIZE_MESSAGE = "Category description cannot be longer than 90 characters";
    public static final int FIELD_NAME_CATEGORY_SIZE_MAX = 50;
    public static final int FIELD_DESCRIPTION_CATEGORY_SIZE_MAX = 90;
    public static final String CATEGORY_EXISTS_MESSAGE = "Category already exists";
}
