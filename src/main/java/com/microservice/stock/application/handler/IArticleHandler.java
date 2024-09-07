package com.microservice.stock.application.handler;

import com.microservice.stock.application.dto.request.ArticleRequest;

public interface IArticleHandler {
    void createArticle(ArticleRequest articleRequest);
}
