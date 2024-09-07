package com.microservice.stock.application.mapper;

import com.microservice.stock.domain.model.Article;
import com.microservice.stock.application.dto.request.ArticleRequest;
import com.microservice.stock.domain.model.Brand;
import com.microservice.stock.domain.model.Category;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.List;

@Mapper(componentModel = "spring")
public interface IArticleRequestMapper {

    @Mapping(source = "categoryIds", target = "categories", qualifiedByName = "mapCategoryIdsToCategories")
    @Mapping(source = "brandId", target = "brand", qualifiedByName = "mapBrandIdToBrand")
    @Mapping(target = "id", ignore = true)
    Article toArticle(ArticleRequest articleRequest);

    @Named("mapCategoryIdsToCategories")
    default List<Category> mapCategoryIdsToCategories(List<Long> categoryIds) {
        return categoryIds.stream().map(id -> new Category(id, null, null)).toList();
    }

    @Named("mapBrandIdToBrand")
    default Brand mapBrandIdToBrand(Long brandId) {
        return new Brand(brandId, null, null);
    }
}
