package com.microservice.stock.infraestructure.out.jpa.mapper;

import com.microservice.stock.domain.model.Article;
import com.microservice.stock.infraestructure.out.jpa.entity.ArticleEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ArticleEntityMapper {
    ArticleEntity toEntity(Article article);
    Article toDomain(ArticleEntity articleEntity);
}
