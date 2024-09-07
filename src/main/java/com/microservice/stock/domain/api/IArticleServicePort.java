package com.microservice.stock.domain.api;

import com.microservice.stock.domain.model.Article;

public interface IArticleServicePort {
    void createArticle(Article article);
}
