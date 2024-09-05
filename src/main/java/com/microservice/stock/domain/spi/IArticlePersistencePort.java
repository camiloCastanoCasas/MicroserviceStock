package com.microservice.stock.domain.spi;

import com.microservice.stock.domain.model.Article;

public interface IArticlePersistencePort {
    void createArticle(Article article);
}
