package com.microservice.stock.domain.api;

import com.microservice.stock.domain.model.Article;
import com.microservice.stock.domain.util.Pagination;

public interface IArticleServicePort {
    void createArticle(Article article);
    Pagination<Article> listArticles(Integer pageNumber, Integer pageSize, String sortBy, String sortDirection);
}
