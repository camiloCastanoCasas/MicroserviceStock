package com.microservice.stock.infraestructure.out.jpa.adapter;

import com.microservice.stock.domain.model.Article;
import com.microservice.stock.domain.spi.IArticlePersistencePort;
import com.microservice.stock.infraestructure.out.jpa.entity.ArticleEntity;
import com.microservice.stock.infraestructure.out.jpa.mapper.ArticleEntityMapper;
import com.microservice.stock.infraestructure.out.jpa.mapper.BrandEntityMapper;
import com.microservice.stock.infraestructure.out.jpa.repository.IArticleRepository;
import com.microservice.stock.infraestructure.out.jpa.repository.IBrandRepository;
import com.microservice.stock.infraestructure.out.jpa.repository.ICategoryRepository;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class ArticleJpaAdapter implements IArticlePersistencePort {
    private final IArticleRepository articleRepository;
    private final ArticleEntityMapper articleEntityMapper;
    private final IBrandRepository brandRepository;
    private final ICategoryRepository categoryRepository;

    @Override
    public void createArticle(Article article) {
        ArticleEntity articleEntity = articleEntityMapper.toEntity(article);
        articleRepository.save(articleEntity);
    }
}
