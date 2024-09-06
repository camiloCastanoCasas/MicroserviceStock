package com.microservice.stock.domain.usecase;

import com.microservice.stock.domain.api.IArticleServicePort;
import com.microservice.stock.domain.exceptions.ValidationException;
import com.microservice.stock.domain.model.Article;
import com.microservice.stock.domain.spi.IArticlePersistencePort;
import com.microservice.stock.domain.spi.IBrandPersistencePort;
import com.microservice.stock.domain.spi.ICategoryPersistencePort;
import com.microservice.stock.domain.util.DomainConstants;

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

        if (article.getCategories().isEmpty()) {
            errors.add(DomainConstants.CATEGORY_AT_LEAST_ONE_MESSAGE);
        } else if (article.getCategories().size() > 3) {
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

        if (!errors.isEmpty()) {
            throw new ValidationException(errors);
        }

        articlePersistencePort.createArticle(article);
    }
}
