package com.microservice.stock.infraestructure.out.jpa.adapter;

import com.microservice.stock.domain.model.Article;
import com.microservice.stock.domain.model.Brand;
import com.microservice.stock.domain.model.Category;
import com.microservice.stock.infraestructure.out.jpa.entity.ArticleEntity;
import com.microservice.stock.infraestructure.out.jpa.mapper.ArticleEntityMapper;
import com.microservice.stock.infraestructure.out.jpa.repository.IArticleRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ArticleJpaAdapterTest {

    @Mock
    private IArticleRepository articleRepository;

    @Mock
    private ArticleEntityMapper articleEntityMapper;

    @InjectMocks
    private ArticleJpaAdapter articleJpaAdapter;

    @Test
    @DisplayName("Should save the article correctly in the database")
    void createArticle_ShouldSaveCategory() {
        Article article = new Article(1L, "Article1", "Description1", 3,
                new BigDecimal("123.456"),
                new Brand(1L, null, null),
                List.of(new Category(1L, null, null))
        );
        ArticleEntity articleEntity = articleEntityMapper.toEntity(article);
        given(articleRepository.save(articleEntity)).willReturn(articleEntity);

        articleJpaAdapter.createArticle(article);

        verify(articleRepository, times(1)).save(articleEntity);
    }

    @Test
    @DisplayName("Should return true if the article already exists")
    void existsByName_ShouldReturnTrue_WhenArticleExists() {
        // Given
        String name = "Existing Article";
        when(articleRepository.findByName(name)).thenReturn(Optional.of(new ArticleEntity()));

        // When
        boolean exists = articleJpaAdapter.existByName(name);

        // Then
        assertTrue(exists);
        verify(articleRepository, times(1)).findByName(name);
    }

    @Test
    @DisplayName("Should return false if the article does not exist")
    void existsByName_ShouldReturnTrue_WhenArticleDoesNotExists() {
        // Given
        String name = "Non-Existing Article";
        when(articleRepository.findByName(name)).thenReturn(Optional.empty());

        // When
        boolean exists = articleJpaAdapter.existByName(name);

        // Then
        assertFalse(exists);
        verify(articleRepository, times(1)).findByName(name);
    }
}