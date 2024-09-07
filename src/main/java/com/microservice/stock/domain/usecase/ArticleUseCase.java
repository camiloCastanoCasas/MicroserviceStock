package com.microservice.stock.domain.usecase;

import com.microservice.stock.domain.api.IArticleServicePort;
import com.microservice.stock.domain.exceptions.ValidationException;
import com.microservice.stock.domain.model.Article;
import com.microservice.stock.domain.spi.IArticlePersistencePort;
import com.microservice.stock.domain.spi.IBrandPersistencePort;
import com.microservice.stock.domain.spi.ICategoryPersistencePort;
import com.microservice.stock.domain.util.DomainConstants;

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
}
