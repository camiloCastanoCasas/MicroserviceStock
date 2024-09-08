package com.microservice.stock.infraestructure.out.jpa.mapper;

import com.microservice.stock.domain.model.Article;
import com.microservice.stock.domain.util.Pagination;
import com.microservice.stock.infraestructure.out.jpa.entity.ArticleEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;

@RequiredArgsConstructor
public class ArticlePageMapper {

    private final ArticleEntityMapper articleEntityMapper;

    public Pagination<Article> toPagination(Page<ArticleEntity> page) {
        Pagination<Article> pagination = new Pagination<>();
        pagination.setContent(page.getContent().stream()
                .map(articleEntityMapper::toDomain)
                .toList());
        pagination.setPageNumber(page.getNumber());
        pagination.setPageSize(page.getSize());
        pagination.setTotalElements(page.getTotalElements());
        pagination.setTotalPages(page.getTotalPages());
        return pagination;
    }
}
