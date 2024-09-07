package com.microservice.stock.domain.util;

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
    public static final String INVALID_PAGE_NUMBER_NULL_MESSAGE = "Page number cannot be null.";
    public static final String INVALID_PAGE_SIZE_NULL_MESSAGE = "Page size cannot be null.";
  
    public static final String CATEGORY_AT_LEAST_ONE_MESSAGE = "Article must have at least one category.";
    public static final String CATEGORY_MORE_THAN_THREE_MESSAGE = "Article cannot have more than three categories.";
    public static final String CATEGORY_DUPLICATE_MESSAGE = "Article contains duplicate categories.";
    public static final String CATEGORY_DOES_NOT_EXISTS = "Category with id %d does not exist.";
    public static final String BRAND_DOES_NOT_EXISTS = "Brand with id %d does not exist.";
    public static final String FIELD_PRICE_NOT_NULL_MESSAGE = "Price cannot be empty, zero or negative value.";
    public static final String FIELD_PRICE_NOT_POSITIVE_MESSAGE = "Price must be positive value.";
    public static final String FIELD_QUANTITY_NOT_NULL_MESSAGE = "Quantity cannot be null.";
    public static final String FIELD_QUANTITY_NOT_POSITIVE_OR_ZERO_MESSAGE = "Quantity must be zero or a positive value.";
    public static final int FIELD_CATEGORIES_MIN = 1;
    public static final int FIELD_CATEGORIES_MAX = 3;
    public static final String FIELD_CATEGORIES_OUT_OF_RANGE_MESSAGE = "The number of categories must be between 1 and 3.";
    public static final String FIELD_CATEGORIES_NOT_UNIQUE_MESSAGE = "Categories must be unique in the article.";
    public static final String FIELD_BRAND_ID_NOT_NULL_MESSAGE = "Brand ID cannot be null.";
    public static final String FIELD_BRAND_ID_NOT_POSITIVE_MESSAGE = "Brand ID must be a positive value.";
    public static final String ARTICLE_EXISTS_MESSAGE = "Article already exists";
}
