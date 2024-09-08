package com.microservice.stock.infraestructure.out.jpa.adapter;

import com.microservice.stock.domain.model.Article;
import com.microservice.stock.domain.spi.IArticlePersistencePort;
import com.microservice.stock.domain.util.DomainConstants;
import com.microservice.stock.domain.util.Pagination;
import com.microservice.stock.infraestructure.out.jpa.entity.ArticleEntity;
import com.microservice.stock.infraestructure.out.jpa.mapper.ArticleEntityMapper;
import com.microservice.stock.infraestructure.out.jpa.mapper.ArticlePageMapper;
import com.microservice.stock.infraestructure.out.jpa.repository.IArticleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*;

import java.util.List;

@RequiredArgsConstructor
public class ArticleJpaAdapter implements IArticlePersistencePort {
    private final IArticleRepository articleRepository;
    private final ArticleEntityMapper articleEntityMapper;

    @Override
    public void createArticle(Article article) {
        ArticleEntity articleEntity = articleEntityMapper.toEntity(article);
        articleRepository.save(articleEntity);
    }

    @Override
    public boolean existByName(String name) {
        return articleRepository.findByName(name).isPresent();
    }

    @Override
    public Pagination<Article> listArticles(Integer pageNumber, Integer pageSize, String sortBy, String sortDirection) {
        Page<ArticleEntity> page;
        if ("categoryName".equalsIgnoreCase(sortBy)) {
            Page<ArticleEntity> allArticles = articleRepository.findAll(PageRequest.of(pageNumber, pageSize));

            List<ArticleEntity> sortedArticles = allArticles.getContent().stream()
                    .sorted((a1, a2) -> {
                        String firstCategoryName1 = a1.getCategories().isEmpty() ? "" : a1.getCategories().get(0).getName();
                        String firstCategoryName2 = a2.getCategories().isEmpty() ? "" : a2.getCategories().get(0).getName();
                        if(DomainConstants.ORDER_ASC.equalsIgnoreCase(sortDirection)){
                            return firstCategoryName1.compareToIgnoreCase(firstCategoryName2);
                        }
                        else {
                            return firstCategoryName2.compareToIgnoreCase(firstCategoryName1);
                        }
                    })
                    .toList();
            Page<ArticleEntity> sortedPage = new PageImpl<>(sortedArticles, PageRequest.of(pageNumber, pageSize), allArticles.getTotalElements());

            ArticlePageMapper articlePageMapper = new ArticlePageMapper(articleEntityMapper);
            return articlePageMapper.toPagination(sortedPage);
        } else {
            Sort sort = Sort.by(Sort.Order.by(sortBy).with(Sort.Direction.fromString(sortDirection)));
            Pageable pageable = PageRequest.of(pageNumber, pageSize, sort);

            page = articleRepository.findAll(pageable);
            ArticlePageMapper articlePageMapper = new ArticlePageMapper(articleEntityMapper);
            return articlePageMapper.toPagination(page);
        }
    }
}


