package com.microservice.stock.domain.usecase;

import com.microservice.stock.domain.api.IArticleServicePort;
import com.microservice.stock.domain.exceptions.ValidationException;
import com.microservice.stock.domain.model.Article;
import com.microservice.stock.domain.model.Category;
import com.microservice.stock.domain.spi.IArticlePersistencePort;
import com.microservice.stock.domain.spi.ICategoryPersistencePort;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class ArticleUseCase implements IArticleServicePort {

    private final IArticlePersistencePort articlePersistencePort;
    private final ICategoryPersistencePort categoryPersistencePort;

    public ArticleUseCase(IArticlePersistencePort articlePersistencePort, ICategoryPersistencePort categoryPersistencePort) {
        this.articlePersistencePort = articlePersistencePort;
        this.categoryPersistencePort = categoryPersistencePort;
    }

    @Override
    public void createArticle(Article article) {
        ArrayList<String> errors = new ArrayList<>();

        if (article.getCategories().isEmpty()) {
            errors.add("Article must have at least one category.");
        } else if (article.getCategories().size() > 3) {
            errors.add("Article cannot have more than three categories.");
        }

        Set<Long> categoryIds = new HashSet<>();
        article.getCategories().forEach(category -> {
            if (!categoryIds.add(category.getId())) {
                errors.add("Article contains duplicate categories.");
            }
        });

        for(Long categoryId: categoryIds){
            if(!categoryPersistencePort.existById(categoryId)){
                errors.add("Error con la categoria" + categoryId);
            }
        }

        if (!errors.isEmpty()) {
            throw new ValidationException(errors);
        }

        articlePersistencePort.createArticle(article);
    }
}
