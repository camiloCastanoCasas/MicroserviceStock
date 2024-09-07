package com.microservice.stock.application.handler;

import com.microservice.stock.application.dto.request.ArticleRequest;
import com.microservice.stock.application.mapper.IArticleRequestMapper;
import com.microservice.stock.domain.api.IArticleServicePort;
import com.microservice.stock.domain.model.Article;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Transactional
public class ArticleHandler implements IArticleHandler {

    private final IArticleServicePort articleServicePort;
    private final IArticleRequestMapper articleRequestMapper;

    @Override
    public void createArticle(ArticleRequest articleRequest) {
        Article article = articleRequestMapper.toArticle(articleRequest);
        articleServicePort.createArticle(article);
    }
}
