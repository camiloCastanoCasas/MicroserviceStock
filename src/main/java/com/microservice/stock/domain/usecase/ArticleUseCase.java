package com.microservice.stock.domain.usecase;

import com.microservice.stock.domain.api.IArticleServicePort;
import com.microservice.stock.domain.exceptions.ValidationException;
import com.microservice.stock.domain.model.Article;
import com.microservice.stock.domain.spi.IArticlePersistencePort;
import com.microservice.stock.domain.spi.IBrandPersistencePort;
import com.microservice.stock.domain.spi.ICategoryPersistencePort;
import com.microservice.stock.domain.util.DomainConstants;
import com.microservice.stock.domain.util.Pagination;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class ArticleUseCase implements IArticleServicePort {

    private final IArticlePersistencePort articlePersistencePort;
    private final ICategoryPersistencePort categoryPersistencePort;
    private final IBrandPersistencePort brandPersistencePort;

    public ArticleUseCase(IArticlePersistencePort articlePersistencePort, ICategoryPersistencePort categoryPersistencePort, IBrandPersistencePort brandPersistencePort) {
        this.articlePersistencePort = articlePersistencePort;
        this.categoryPersistencePort = categoryPersistencePort;
        this.brandPersistencePort = brandPersistencePort;
    }

    @Override
    public void createArticle(Article article) {
        ArrayList<String> errors = new ArrayList<>();

        if(article.getName().trim().isEmpty()){
            errors.add(DomainConstants.FIELD_NAME_NULL_MESSAGE);
        }
        if(article.getDescription().trim().isEmpty()){
            errors.add(DomainConstants.FIELD_DESCRIPTION_NULL_MESSAGE);
        }
        if(article.getPrice() == null){
            errors.add(DomainConstants.FIELD_PRICE_NOT_NULL_MESSAGE);
        } else if (article.getPrice().compareTo(BigDecimal.ZERO) <= 0){
            errors.add(DomainConstants.FIELD_PRICE_NOT_POSITIVE_MESSAGE);
        }
        if(article.getQuantity() == null){
            errors.add(DomainConstants.FIELD_QUANTITY_NOT_NULL_MESSAGE);
        }else if(article.getQuantity() < 0){
            errors.add(DomainConstants.FIELD_QUANTITY_NOT_POSITIVE_OR_ZERO_MESSAGE);
        }
        if (article.getCategories().isEmpty()) {
            errors.add(DomainConstants.CATEGORY_AT_LEAST_ONE_MESSAGE);
        } else if (article.getCategories().size() > DomainConstants.FIELD_CATEGORIES_MAX) {
            errors.add(DomainConstants.CATEGORY_MORE_THAN_THREE_MESSAGE);
        }

        Set<Long> categoryIds = new HashSet<>();
        article.getCategories().forEach(category -> {
            if (!categoryIds.add(category.getId())) {
                errors.add(DomainConstants.CATEGORY_DUPLICATE_MESSAGE);
            }
        });

        for(Long categoryId: categoryIds){
            if(!categoryPersistencePort.existById(categoryId)){
                errors.add(String.format(DomainConstants.CATEGORY_DOES_NOT_EXISTS, categoryId));
            }
        }

        if(!brandPersistencePort.existById(article.getBrand().getId())){
            errors.add(String.format(DomainConstants.BRAND_DOES_NOT_EXISTS, article.getBrand().getId()));
        }

        if(articlePersistencePort.existByName(article.getName())){
            errors.add(DomainConstants.ARTICLE_EXISTS_MESSAGE);
        }

        if (!errors.isEmpty()) {
            throw new ValidationException(errors);
        }
        articlePersistencePort.createArticle(article);
    }

    @Override
    public Pagination<Article> listArticles(Integer pageNumber, Integer pageSize, String sortBy, String sortDirection) {
        ArrayList<String> errors = new ArrayList<>();

        if(pageNumber == null){
            errors.add(DomainConstants.INVALID_PAGE_NUMBER_NULL_MESSAGE);
        } else if(pageNumber < 0) {
            errors.add(DomainConstants.INVALID_PAGE_NUMBER_MESSAGE);
        }
        if(pageSize == null){
            errors.add(DomainConstants.INVALID_PAGE_SIZE_NULL_MESSAGE);
        } else if (pageSize <= 0) {
            errors.add(DomainConstants.INVALID_PAGE_SIZE_MESSAGE);
        }
        if (sortBy == null || !DomainConstants.VALID_SORT_FIELDS.contains(sortBy)) {
            errors.add(DomainConstants.INVALID_SORT_FIELD_MESSAGE);
        }
        if (!sortDirection.equalsIgnoreCase(DomainConstants.ORDER_ASC) && !sortDirection.equalsIgnoreCase(DomainConstants.ORDER_DESC)) {
            errors.add(DomainConstants.INVALID_SORT_DIRECTION_MESSAGE);
        }
        if (!errors.isEmpty()) {
            throw new ValidationException(errors);
        }
        return articlePersistencePort.listArticles(pageNumber, pageSize,sortBy,sortDirection);
    }
}
