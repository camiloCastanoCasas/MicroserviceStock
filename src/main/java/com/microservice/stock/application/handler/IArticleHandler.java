package com.microservice.stock.application.handler;

import com.microservice.stock.application.dto.request.ArticleRequest;
import com.microservice.stock.application.dto.response.ArticleResponse;
import com.microservice.stock.application.dto.response.PaginationResponse;

public interface IArticleHandler {
    void createArticle(ArticleRequest articleRequest);
    PaginationResponse<ArticleResponse> listArticles(int pageNumber, int pageSize, String sortBy, String sortDirection);
}
