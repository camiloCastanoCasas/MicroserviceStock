package com.microservice.stock.application.handler;

import com.microservice.stock.application.dto.request.ArticleRequest;
import com.microservice.stock.application.dto.response.ArticleResponse;
import com.microservice.stock.application.dto.response.PaginationResponse;
import com.microservice.stock.application.mapper.IArticleRequestMapper;
import com.microservice.stock.application.mapper.IArticleResponseMapper;
import com.microservice.stock.domain.api.IArticleServicePort;
import com.microservice.stock.domain.model.Article;
import com.microservice.stock.domain.util.Pagination;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class ArticleHandler implements IArticleHandler {

    private final IArticleServicePort articleServicePort;
    private final IArticleRequestMapper articleRequestMapper;
    private final IArticleResponseMapper articleResponseMapper;

    @Override
    public void createArticle(ArticleRequest articleRequest) {
        Article article = articleRequestMapper.toArticle(articleRequest);
        articleServicePort.createArticle(article);
    }

    @Override
    public PaginationResponse<ArticleResponse> listArticles(int pageNumber, int pageSize, String sortBy, String sortDirection) {
        Pagination<Article> articlePagination = articleServicePort.listArticles(pageNumber,pageSize,sortBy,sortDirection);

        List<ArticleResponse> articleResponses = articlePagination.getContent().stream()
                .map(articleResponseMapper::toArticleResponse)
                .toList();

        Pagination<ArticleResponse> responsePagination = new Pagination<>(
                articleResponses,
                articlePagination.getPageNumber(),
                articlePagination.getPageSize(),
                articlePagination.getTotalElements()
        );
        return articleResponseMapper.toPaginationResponse(responsePagination);
    }
}
