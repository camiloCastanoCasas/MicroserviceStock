package com.microservice.stock.infraestructure.out.jpa.adapter;

import com.microservice.stock.domain.model.Article;
import com.microservice.stock.domain.model.Brand;
import com.microservice.stock.domain.model.Category;
import com.microservice.stock.domain.util.Pagination;
import com.microservice.stock.infraestructure.out.jpa.entity.ArticleEntity;
import com.microservice.stock.infraestructure.out.jpa.entity.BrandEntity;
import com.microservice.stock.infraestructure.out.jpa.entity.CategoryEntity;
import com.microservice.stock.infraestructure.out.jpa.mapper.ArticleEntityMapper;
import com.microservice.stock.infraestructure.out.jpa.mapper.ArticlePageMapper;
import com.microservice.stock.infraestructure.out.jpa.repository.IArticleRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
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

    private ArticlePageMapper articlePageMapper;

    @BeforeEach
    void setUp(){
        articlePageMapper = new ArticlePageMapper(articleEntityMapper);
        articleJpaAdapter = new ArticleJpaAdapter(articleRepository, articleEntityMapper);
    }

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

    @Test
    @DisplayName("Should return paginated articles")
    void listArticles_ShouldReturnPaginatedBrands() {
        // Given
        int pageNumber = 0;
        int pageSize = 5;
        String sortBy = "name";
        String sortDirection = "asc";

        // Mocking entities
        ArticleEntity articleEntity1 = new ArticleEntity(1L, "Article1", "Description", 3,
                new BigDecimal("123.456"),
                new BrandEntity(1L, null, null),
                List.of(
                        new CategoryEntity(1L, null, null)
                ));

        ArticleEntity articleEntity2 = new ArticleEntity (2L, "Article2", "Description", 3,
                new BigDecimal("123.456"),
                new BrandEntity(1L, null, null),
                List.of(
                        new CategoryEntity(1L, null, null)
                ));

        List<ArticleEntity> articleEntities = List.of(articleEntity1,articleEntity2);
        Page<ArticleEntity> page = new PageImpl<>(articleEntities, PageRequest.of(pageNumber, pageSize),2);

        when(articleRepository.findAll(any(Pageable.class))).thenReturn(page);
        when(articleEntityMapper.toDomain(articleEntity1)).thenReturn(new Article(1L, "Article1", "Description", 3,new BigDecimal("123.456"),
                new Brand(1L, null, null),
                List.of(
                        new Category(1L, null, null)
                )));
        when(articleEntityMapper.toDomain(articleEntity2)).thenReturn(new Article(2L, "Article2", "Description", 3,new BigDecimal("123.456"),
                new Brand(1L, null, null),
                List.of(
                        new Category(1L, null, null)
                )));

        // When
        Pagination<Article> result = articleJpaAdapter.listArticles(pageNumber, pageSize, sortBy, sortDirection);

        // Then
        assertThat(result.getContent()).hasSize(2);
        assertThat(result.getPageNumber()).isZero();
        assertThat(result.getPageSize()).isEqualTo(5);
        assertEquals(1L, result.getContent().get(0).getId(), "The Article ID should be 1L");
        assertEquals(2L, result.getContent().get(1).getId(), "The Article ID should be 2L");

        verify(articleRepository, times(1)).findAll(any(Pageable.class));
    }

    @Test
    @DisplayName("Should return paginated articles")
    void listArticles_ShouldReturnPaginatedBrandsWithDESC() {
        // Given
        int pageNumber = 0;
        int pageSize = 5;
        String sortBy = "name";
        String sortDirection = "desc";

        // Mocking entities
        ArticleEntity articleEntity1 = new ArticleEntity(1L, "Article1", "Description", 3,
                new BigDecimal("123.456"),
                new BrandEntity(1L, null, null),
                List.of(
                        new CategoryEntity(1L, null, null)
                ));

        ArticleEntity articleEntity2 = new ArticleEntity (2L, "Article2", "Description", 3,
                new BigDecimal("123.456"),
                new BrandEntity(1L, null, null),
                List.of(
                        new CategoryEntity(1L, null, null)
                ));

        List<ArticleEntity> articleEntities = List.of(articleEntity2,articleEntity1);
        Page<ArticleEntity> page = new PageImpl<>(articleEntities, PageRequest.of(pageNumber, pageSize),2);

        when(articleRepository.findAll(any(Pageable.class))).thenReturn(page);
        when(articleEntityMapper.toDomain(articleEntity1)).thenReturn(new Article(1L, "Article1", "Description", 3,new BigDecimal("123.456"),
                new Brand(1L, null, null),
                List.of(
                        new Category(1L, null, null)
                )));
        when(articleEntityMapper.toDomain(articleEntity2)).thenReturn(new Article(2L, "Article2", "Description", 3,new BigDecimal("123.456"),
                new Brand(1L, null, null),
                List.of(
                        new Category(1L, null, null)
                )));

        // When
        Pagination<Article> result = articleJpaAdapter.listArticles(pageNumber, pageSize, sortBy, sortDirection);

        // Then
        assertThat(result.getContent()).hasSize(2);
        assertThat(result.getPageNumber()).isZero();
        assertThat(result.getPageSize()).isEqualTo(5);
        assertEquals(2L, result.getContent().get(0).getId(), "The Article ID should be 2L");
        assertEquals(1L, result.getContent().get(1).getId(), "The Article ID should be 1L");

        verify(articleRepository, times(1)).findAll(any(Pageable.class));
    }

    @Test
    @DisplayName("Should return paginated articles")
    void listArticles_ShouldReturnPaginatedBrandsWithASC_AndSortByBrand() {
        // Given
        int pageNumber = 0;
        int pageSize = 5;
        String sortBy = "brandName";
        String sortDirection = "asc";

        // Mocking entities
        ArticleEntity articleEntity1 = new ArticleEntity(1L, "Article1", "Description", 3,
                new BigDecimal("123.456"),
                new BrandEntity(1L, "a", null),
                List.of(
                        new CategoryEntity(1L, null, null)
                ));

        ArticleEntity articleEntity2 = new ArticleEntity (2L, "Article2", "Description", 3,
                new BigDecimal("123.456"),
                new BrandEntity(2L, "b", null),
                List.of(
                        new CategoryEntity(1L, null, null)
                ));

        List<ArticleEntity> articleEntities = List.of(articleEntity1,articleEntity2);
        Page<ArticleEntity> page = new PageImpl<>(articleEntities, PageRequest.of(pageNumber, pageSize, Sort.by(Sort.Order.by(sortBy).with(Sort.Direction.fromString(sortDirection)))),2);

        when(articleRepository.findAll(any(Pageable.class))).thenReturn(page);
        when(articleEntityMapper.toDomain(articleEntity1)).thenReturn(new Article(1L, "Article1", "Description", 3,new BigDecimal("123.456"),
                new Brand(1L, "a", null),
                List.of(
                        new Category(1L, null, null)
                )));
        when(articleEntityMapper.toDomain(articleEntity2)).thenReturn(new Article(2L, "Article2", "Description", 3,new BigDecimal("123.456"),
                new Brand(1L, "b", null),
                List.of(
                        new Category(1L, null, null)
                )));

        // When
        Pagination<Article> result = articleJpaAdapter.listArticles(pageNumber, pageSize, sortBy, sortDirection);

        // Then
        assertThat(result.getContent()).hasSize(2);
        assertThat(result.getPageNumber()).isZero();
        assertThat(result.getPageSize()).isEqualTo(5);
        assertEquals(1L, result.getContent().get(0).getId(), "The Article ID should be 1L");
        assertEquals(2L, result.getContent().get(1).getId(), "The Article ID should be 2L");

        verify(articleRepository, times(1)).findAll(any(Pageable.class));
    }

    @Test
    @DisplayName("Should return paginated articles")
    void listArticles_ShouldReturnPaginatedBrandsWithDESC_AndSortByBrand() {
        // Given
        int pageNumber = 0;
        int pageSize = 5;
        String sortBy = "brandName";
        String sortDirection = "desc";

        // Mocking entities
        ArticleEntity articleEntity1 = new ArticleEntity(1L, "Article1", "Description", 3,
                new BigDecimal("123.456"),
                new BrandEntity(1L, "a", null),
                List.of(
                        new CategoryEntity(1L, null, null)
                ));

        ArticleEntity articleEntity2 = new ArticleEntity (2L, "Article2", "Description", 3,
                new BigDecimal("123.456"),
                new BrandEntity(2L, "b", null),
                List.of(
                        new CategoryEntity(1L, null, null)
                ));

        List<ArticleEntity> articleEntities = List.of(articleEntity2,articleEntity1);
        Page<ArticleEntity> page = new PageImpl<>(articleEntities, PageRequest.of(pageNumber, pageSize, Sort.by(Sort.Order.by(sortBy).with(Sort.Direction.fromString(sortDirection)))),2);

        when(articleRepository.findAll(any(Pageable.class))).thenReturn(page);
        when(articleEntityMapper.toDomain(articleEntity1)).thenReturn(new Article(1L, "Article1", "Description", 3,new BigDecimal("123.456"),
                new Brand(1L, "a", null),
                List.of(
                        new Category(1L, null, null)
                )));
        when(articleEntityMapper.toDomain(articleEntity2)).thenReturn(new Article(2L, "Article2", "Description", 3,new BigDecimal("123.456"),
                new Brand(1L, "b", null),
                List.of(
                        new Category(1L, null, null)
                )));

        // When
        Pagination<Article> result = articleJpaAdapter.listArticles(pageNumber, pageSize, sortBy, sortDirection);

        // Then
        assertThat(result.getContent()).hasSize(2);
        assertThat(result.getPageNumber()).isZero();
        assertThat(result.getPageSize()).isEqualTo(5);
        assertEquals(2L, result.getContent().get(0).getId(), "The Article ID should be 2L");
        assertEquals(1L, result.getContent().get(1).getId(), "The Article ID should be 1L");

        verify(articleRepository, times(1)).findAll(any(Pageable.class));
    }

    @Test
    @DisplayName("Should return paginated articles")
    void listArticles_ShouldReturnPaginatedBrandsWithASC_AndSortByCategoryName() {
        // Given
        int pageNumber = 0;
        int pageSize = 5;
        String sortBy = "categoryName";
        String sortDirection = "asc";

        // Mocking entities
        ArticleEntity articleEntity1 = new ArticleEntity(1L, "Article1", "Description", 3,
                new BigDecimal("123.456"),
                new BrandEntity(1L, "a", null),
                List.of(
                        new CategoryEntity(1L, "a", null)
                ));

        ArticleEntity articleEntity2 = new ArticleEntity (2L, "Article2", "Description", 3,
                new BigDecimal("123.456"),
                new BrandEntity(2L, "b", null),
                List.of(
                        new CategoryEntity(1L, "b", null)
                ));

        List<ArticleEntity> articleEntities = List.of(articleEntity1,articleEntity2);
        Page<ArticleEntity> page = new PageImpl<>(articleEntities, PageRequest.of(pageNumber, pageSize, Sort.by(Sort.Order.by(sortBy).with(Sort.Direction.fromString(sortDirection)))),2);

        when(articleRepository.findAll(any(Pageable.class))).thenReturn(page);
        when(articleEntityMapper.toDomain(articleEntity1)).thenReturn(new Article(1L, "Article1", "Description", 3,new BigDecimal("123.456"),
                new Brand(1L, "a", null),
                List.of(
                        new Category(1L, "a", null)
                )));
        when(articleEntityMapper.toDomain(articleEntity2)).thenReturn(new Article(2L, "Article2", "Description", 3,new BigDecimal("123.456"),
                new Brand(1L, "b", null),
                List.of(
                        new Category(1L, "b", null)
                )));

        // When
        Pagination<Article> result = articleJpaAdapter.listArticles(pageNumber, pageSize, sortBy, sortDirection);

        // Then
        assertThat(result.getContent()).hasSize(2);
        assertThat(result.getPageNumber()).isZero();
        assertThat(result.getPageSize()).isEqualTo(5);
        assertEquals(1L, result.getContent().get(0).getId(), "The Article ID should be 1L");
        assertEquals(2L, result.getContent().get(1).getId(), "The Article ID should be 2L");

        verify(articleRepository, times(1)).findAll(any(Pageable.class));
    }

    @Test
    @DisplayName("Should return paginated articles")
    void listArticles_ShouldReturnPaginatedBrandsWithDESC_AndSortByCategoryName() {
        // Given
        int pageNumber = 0;
        int pageSize = 5;
        String sortBy = "categoryName";
        String sortDirection = "desc";

        // Mocking entities
        ArticleEntity articleEntity1 = new ArticleEntity(1L, "Article1", "Description", 3,
                new BigDecimal("123.456"),
                new BrandEntity(1L, "a", null),
                List.of(
                        new CategoryEntity(1L, "a", null)
                ));

        ArticleEntity articleEntity2 = new ArticleEntity (2L, "Article2", "Description", 3,
                new BigDecimal("123.456"),
                new BrandEntity(2L, "b", null),
                List.of(
                        new CategoryEntity(1L, "b", null)
                ));

        List<ArticleEntity> articleEntities = List.of(articleEntity2,articleEntity1);
        Page<ArticleEntity> page = new PageImpl<>(articleEntities, PageRequest.of(pageNumber, pageSize, Sort.by(Sort.Order.by(sortBy).with(Sort.Direction.fromString(sortDirection)))),2);

        when(articleRepository.findAll(any(Pageable.class))).thenReturn(page);
        when(articleEntityMapper.toDomain(articleEntity1)).thenReturn(new Article(1L, "Article1", "Description", 3,new BigDecimal("123.456"),
                new Brand(1L, "a", null),
                List.of(
                        new Category(1L, "a", null)
                )));
        when(articleEntityMapper.toDomain(articleEntity2)).thenReturn(new Article(2L, "Article2", "Description", 3,new BigDecimal("123.456"),
                new Brand(1L, "b", null),
                List.of(
                        new Category(1L, "b", null)
                )));

        // When
        Pagination<Article> result = articleJpaAdapter.listArticles(pageNumber, pageSize, sortBy, sortDirection);

        // Then
        assertThat(result.getContent()).hasSize(2);
        assertThat(result.getPageNumber()).isZero();
        assertThat(result.getPageSize()).isEqualTo(5);
        assertEquals(2L, result.getContent().get(0).getId(), "The Article ID should be 2L");
        assertEquals(1L, result.getContent().get(1).getId(), "The Article ID should be 1L");

        verify(articleRepository, times(1)).findAll(any(Pageable.class));
    }
}