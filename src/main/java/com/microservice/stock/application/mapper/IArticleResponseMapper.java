package com.microservice.stock.application.mapper;

import com.microservice.stock.application.dto.response.ArticleResponse;
import com.microservice.stock.application.dto.response.PaginationResponse;
import com.microservice.stock.domain.model.Article;
import com.microservice.stock.domain.model.Brand;
import com.microservice.stock.domain.model.Category;
import com.microservice.stock.domain.util.Pagination;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Mapper(componentModel = "spring")
public interface IArticleResponseMapper {
    @Mapping(source = "categories", target = "categories", qualifiedByName = "mapCategoryListToStringList")
    @Mapping(source = "brand", target = "brand", qualifiedByName = "mapBrandToString")
    ArticleResponse toArticleResponse(Article article);

    @Named("mapCategoryListToStringList")
    default List<Map<String, Object>> mapCategoryListToStringList(List<Category> categories) {
        return categories.stream()
                .map(category -> {
                    Map<String, Object> categoryMap = new LinkedHashMap<>();
                    categoryMap.put("id", category.getId());
                    categoryMap.put("name", category.getName());
                    return categoryMap;
                })
                .toList();
    }

    @Named("mapBrandToString")
    default String mapBrandToString(Brand brand) {
        return brand.getName();
    }

    @Mapping(target = "page", source = "pagination.pageNumber")
    @Mapping(target = "size", source = "pagination.pageSize")
    @Mapping(target = "totalElements", source = "pagination.totalElements")
    @Mapping(target = "totalPages", source = "pagination.totalPages")
    @Mapping(target = "first", source = "pagination.first")
    @Mapping(target = "last", source = "pagination.last")
    @Mapping(target = "empty", source = "pagination.empty")
    @Mapping(target = "content", source = "pagination.content")
    PaginationResponse<ArticleResponse> toPaginationResponse(Pagination<ArticleResponse> pagination);
}
