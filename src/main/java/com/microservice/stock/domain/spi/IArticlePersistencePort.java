package com.microservice.stock.domain.spi;

import com.microservice.stock.domain.model.Article;

import com.microservice.stock.domain.util.Pagination;

public interface IArticlePersistencePort {
    void createArticle(Article article);
    boolean existByName(String name);
    Pagination<Article> listArticles(Integer pageNumber, Integer pageSize, String sortBy, String sortDirection);
}
