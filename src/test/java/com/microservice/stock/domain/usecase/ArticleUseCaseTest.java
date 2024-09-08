package com.microservice.stock.domain.usecase;

import com.microservice.stock.domain.exceptions.ValidationException;
import com.microservice.stock.domain.model.Article;
import com.microservice.stock.domain.model.Brand;
import com.microservice.stock.domain.model.Category;
import com.microservice.stock.domain.spi.IArticlePersistencePort;
import com.microservice.stock.domain.spi.IBrandPersistencePort;
import com.microservice.stock.domain.spi.ICategoryPersistencePort;
import com.microservice.stock.domain.util.DomainConstants;
import com.microservice.stock.domain.util.Pagination;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.times;

@ExtendWith(MockitoExtension.class)
class ArticleUseCaseTest {

    @Mock
    private IArticlePersistencePort articlePersistencePort;

    @Mock
    private ICategoryPersistencePort categoryPersistencePort;

    @Mock
    private IBrandPersistencePort brandPersistencePort;

    @InjectMocks
    private ArticleUseCase articleUseCase;

    @Test
    @DisplayName("Given a article, insert it correctly into the database.")
    void createArticle() {
        //Given
        Article article = new Article(1L, "Article1", "Description1", 3,
                new BigDecimal("123.456"),
                new Brand(1L, null, null),
                List.of(new Category(1L, null, null))
        );
        Mockito.when(articlePersistencePort.existByName("Article1")).thenReturn(false);
        Mockito.when(brandPersistencePort.existById(article.getBrand().getId())).thenReturn(true);
        Mockito.when(categoryPersistencePort.existById(article.getCategories().get(0).getId())).thenReturn(true);

        // When
        articleUseCase.createArticle(article);

        // Then
        Mockito.verify(articlePersistencePort, times(1)).createArticle(article);
        Mockito.verify(brandPersistencePort, times(1)).existById(article.getBrand().getId());
        Mockito.verify(categoryPersistencePort, times(1)).existById(article.getCategories().get(0).getId());
    }

    @Test
    @DisplayName("Throw a ValidationException when the name is empty.")
    void createArticle_ThrowValidationException_WhenNameIsEmpty() {
        // Given
        Article article = new Article(1L, "", "Description1", 3,
                new BigDecimal("123.456"),
                new Brand(1L, null, null),
                List.of(new Category(1L, null, null))
        );

        // When
        Mockito.when(brandPersistencePort.existById(article.getBrand().getId())).thenReturn(true);
        Mockito.when(categoryPersistencePort.existById(article.getCategories().get(0).getId())).thenReturn(true);

        ValidationException exception = assertThrows(ValidationException.class, () -> {
            articleUseCase.createArticle(article);
        });

        //Then
        assertThat(exception.getErrors()).contains(DomainConstants.FIELD_NAME_NULL_MESSAGE);
        Mockito.verify(articlePersistencePort, Mockito.never()).createArticle(article);
        Mockito.verify(brandPersistencePort, times(1)).existById(article.getBrand().getId());
        Mockito.verify(categoryPersistencePort, times(1)).existById(article.getCategories().get(0).getId());
    }

    @Test
    @DisplayName("Throw a ValidationException when the description is empty.")
    void createArticle_ThrowValidationException_WhenDescriptionIsEmpty() {
        // Given
        Article article = new Article(1L, "Article1", "", 3,
                new BigDecimal("123.456"),
                new Brand(1L, null, null),
                List.of(new Category(1L, null, null))
        );

        // When
        Mockito.when(brandPersistencePort.existById(article.getBrand().getId())).thenReturn(true);
        Mockito.when(categoryPersistencePort.existById(article.getCategories().get(0).getId())).thenReturn(true);

        ValidationException exception = assertThrows(ValidationException.class, () -> {
            articleUseCase.createArticle(article);
        });

        //Then
        assertThat(exception.getErrors()).contains(DomainConstants.FIELD_DESCRIPTION_NULL_MESSAGE);
        Mockito.verify(articlePersistencePort, Mockito.never()).createArticle(article);
        Mockito.verify(brandPersistencePort, times(1)).existById(article.getBrand().getId());
        Mockito.verify(categoryPersistencePort, times(1)).existById(article.getCategories().get(0).getId());
    }

    @Test
    @DisplayName("Throw a ValidationException when the price is null.")
    void createArticle_ThrowValidationException_WhenPriceIsNull() {
        // Given
        Article article = new Article(1L, "Article1", "Description", 3,
                null,
                new Brand(1L, null, null),
                List.of(new Category(1L, null, null))
        );

        // When
        Mockito.when(brandPersistencePort.existById(article.getBrand().getId())).thenReturn(true);
        Mockito.when(categoryPersistencePort.existById(article.getCategories().get(0).getId())).thenReturn(true);

        ValidationException exception = assertThrows(ValidationException.class, () -> {
            articleUseCase.createArticle(article);
        });

        //Then
        assertThat(exception.getErrors()).contains(DomainConstants.FIELD_PRICE_NOT_NULL_MESSAGE);
        Mockito.verify(articlePersistencePort, Mockito.never()).createArticle(article);
        Mockito.verify(brandPersistencePort, times(1)).existById(article.getBrand().getId());
        Mockito.verify(categoryPersistencePort, times(1)).existById(article.getCategories().get(0).getId());
    }

    @Test
    @DisplayName("Throw a ValidationException when the price is negative")
    void createArticle_ThrowValidationException_WhenPriceIsNegative() {
        // Given
        Article article = new Article(1L, "Article1", "Description", 3,
                new BigDecimal("-123.456"),
                new Brand(1L, null, null),
                List.of(new Category(1L, null, null))
        );

        // When
        Mockito.when(brandPersistencePort.existById(article.getBrand().getId())).thenReturn(true);
        Mockito.when(categoryPersistencePort.existById(article.getCategories().get(0).getId())).thenReturn(true);

        ValidationException exception = assertThrows(ValidationException.class, () -> {
            articleUseCase.createArticle(article);
        });

        //Then
        assertThat(exception.getErrors()).contains(DomainConstants.FIELD_PRICE_NOT_POSITIVE_MESSAGE);
        Mockito.verify(articlePersistencePort, Mockito.never()).createArticle(article);
        Mockito.verify(brandPersistencePort, times(1)).existById(article.getBrand().getId());
        Mockito.verify(categoryPersistencePort, times(1)).existById(article.getCategories().get(0).getId());
    }

    @Test
    @DisplayName("Throw a ValidationException when the price is zero")
    void createArticle_ThrowValidationException_WhenPriceIsZero() {
        // Given
        Article article = new Article(1L, "Article1", "Description", 3,
                new BigDecimal("0"),
                new Brand(1L, null, null),
                List.of(new Category(1L, null, null))
        );

        // When
        Mockito.when(brandPersistencePort.existById(article.getBrand().getId())).thenReturn(true);
        Mockito.when(categoryPersistencePort.existById(article.getCategories().get(0).getId())).thenReturn(true);

        ValidationException exception = assertThrows(ValidationException.class, () -> {
            articleUseCase.createArticle(article);
        });

        //Then
        assertThat(exception.getErrors()).contains(DomainConstants.FIELD_PRICE_NOT_POSITIVE_MESSAGE);
        Mockito.verify(articlePersistencePort, Mockito.never()).createArticle(article);
        Mockito.verify(brandPersistencePort, times(1)).existById(article.getBrand().getId());
        Mockito.verify(categoryPersistencePort, times(1)).existById(article.getCategories().get(0).getId());
    }

    @Test
    @DisplayName("Throw a ValidationException when the quantity is null.")
    void createArticle_ThrowValidationException_WhenQuantityIsNull() {
        // Given
        Article article = new Article(1L, "Article1", "Description", null,
                new BigDecimal("123.456"),
                new Brand(1L, null, null),
                List.of(new Category(1L, null, null))
        );

        // When
        Mockito.when(brandPersistencePort.existById(article.getBrand().getId())).thenReturn(true);
        Mockito.when(categoryPersistencePort.existById(article.getCategories().get(0).getId())).thenReturn(true);

        ValidationException exception = assertThrows(ValidationException.class, () -> {
            articleUseCase.createArticle(article);
        });

        //Then
        assertThat(exception.getErrors()).contains(DomainConstants.FIELD_QUANTITY_NOT_NULL_MESSAGE);
        Mockito.verify(articlePersistencePort, Mockito.never()).createArticle(article);
        Mockito.verify(brandPersistencePort, times(1)).existById(article.getBrand().getId());
        Mockito.verify(categoryPersistencePort, times(1)).existById(article.getCategories().get(0).getId());
    }

    @Test
    @DisplayName("Throw a ValidationException when the quantity is negative.")
    void createArticle_ThrowValidationException_WhenQuantityIsNegative() {
        // Given
        Article article = new Article(1L, "Article1", "Description", -1,
                new BigDecimal("123.456"),
                new Brand(1L, null, null),
                List.of(new Category(1L, null, null))
        );

        // When
        Mockito.when(brandPersistencePort.existById(article.getBrand().getId())).thenReturn(true);
        Mockito.when(categoryPersistencePort.existById(article.getCategories().get(0).getId())).thenReturn(true);

        ValidationException exception = assertThrows(ValidationException.class, () -> {
            articleUseCase.createArticle(article);
        });

        //Then
        assertThat(exception.getErrors()).contains(DomainConstants.FIELD_QUANTITY_NOT_POSITIVE_OR_ZERO_MESSAGE);
        Mockito.verify(articlePersistencePort, Mockito.never()).createArticle(article);
        Mockito.verify(brandPersistencePort, times(1)).existById(article.getBrand().getId());
        Mockito.verify(categoryPersistencePort, times(1)).existById(article.getCategories().get(0).getId());
    }

    @Test
    @DisplayName("Throw a ValidationException when the list of categories is empty.")
    void createArticle_ThrowValidationException_WhenCategoriesIsEmpty() {
        // Given
        Article article = new Article(1L, "Article1", "Description", 3,
                new BigDecimal("123.456"),
                new Brand(1L, null, null),
                List.of()
        );

        // When
        Mockito.when(brandPersistencePort.existById(article.getBrand().getId())).thenReturn(true);

        ValidationException exception = assertThrows(ValidationException.class, () -> {
            articleUseCase.createArticle(article);
        });

        //Then
        assertThat(exception.getErrors()).contains(DomainConstants.CATEGORY_AT_LEAST_ONE_MESSAGE);
        Mockito.verify(articlePersistencePort, Mockito.never()).createArticle(article);
        Mockito.verify(brandPersistencePort, times(1)).existById(article.getBrand().getId());
    }

    @Test
    @DisplayName("Throw a ValidationException when the list of categories exceeds three categories.")
    void createArticle_ThrowValidationException_WhenCategoriesExceedsThree() {
        // Given
        Article article = new Article(1L, "Article1", "Description", 3,
                new BigDecimal("123.456"),
                new Brand(1L, null, null),
                List.of(
                        new Category(1L, null, null),
                        new Category(2L, null, null),
                        new Category(3L, null, null),
                        new Category(4L, null, null)
                )
        );

        // When
        Mockito.when(brandPersistencePort.existById(article.getBrand().getId())).thenReturn(true);

        ValidationException exception = assertThrows(ValidationException.class, () -> {
            articleUseCase.createArticle(article);
        });

        //Then
        assertThat(exception.getErrors()).contains(DomainConstants.CATEGORY_MORE_THAN_THREE_MESSAGE);
        Mockito.verify(articlePersistencePort, Mockito.never()).createArticle(article);
        Mockito.verify(brandPersistencePort, times(1)).existById(article.getBrand().getId());
    }

    @Test
    @DisplayName("Throw a ValidationException when the list of categories have duplicate categories.")
    void createArticle_ThrowValidationException_WhenHaveDuplicateCategories() {
        // Given
        Article article = new Article(1L, "Article1", "Description", 3,
                new BigDecimal("123.456"),
                new Brand(1L, null, null),
                List.of(
                        new Category(1L, null, null),
                        new Category(1L, null, null)
                )
        );

        // When
        Mockito.when(brandPersistencePort.existById(article.getBrand().getId())).thenReturn(true);

        ValidationException exception = assertThrows(ValidationException.class, () -> {
            articleUseCase.createArticle(article);
        });

        //Then
        assertThat(exception.getErrors()).contains(DomainConstants.CATEGORY_DUPLICATE_MESSAGE);
        Mockito.verify(articlePersistencePort, Mockito.never()).createArticle(article);
        Mockito.verify(brandPersistencePort, times(1)).existById(article.getBrand().getId());
    }

    @Test
    @DisplayName("Throw a ValidationException when the category doesn't exists.")
    void createArticle_ThrowValidationException_WhenCategoryDoesNotExists() {
        // Given
        Article article = new Article(1L, "Article1", "Description", 3,
                new BigDecimal("123.456"),
                new Brand(1L, null, null),
                List.of(
                        new Category(1L, null, null)
                )
        );

        // When
        Mockito.when(brandPersistencePort.existById(article.getBrand().getId())).thenReturn(true);

        ValidationException exception = assertThrows(ValidationException.class, () -> {
            articleUseCase.createArticle(article);
        });

        //Then
        assertThat(exception.getErrors()).contains(String.format(DomainConstants.CATEGORY_DOES_NOT_EXISTS, article.getCategories().get(0).getId()));
        Mockito.verify(articlePersistencePort, Mockito.never()).createArticle(article);
        Mockito.verify(brandPersistencePort, times(1)).existById(article.getBrand().getId());
    }

    @Test
    @DisplayName("Throw a ValidationException when the brandID doesn't exists.")
    void createArticle_ThrowValidationException_WhenBrandID_DoesNotExists() {
        // Given
        Article article = new Article(1L, "Article1", "Description", 3,
                new BigDecimal("123.456"),
                new Brand(1L, null, null),
                List.of(
                        new Category(1L, null, null)
                )
        );

        // When
        Mockito.when(categoryPersistencePort.existById(article.getCategories().get(0).getId())).thenReturn(true);

        ValidationException exception = assertThrows(ValidationException.class, () -> {
            articleUseCase.createArticle(article);
        });

        //Then
        assertThat(exception.getErrors()).contains(String.format(DomainConstants.BRAND_DOES_NOT_EXISTS, article.getBrand().getId()));
        Mockito.verify(articlePersistencePort, Mockito.never()).createArticle(article);
        Mockito.verify(categoryPersistencePort, times(1)).existById(article.getCategories().get(0).getId());
    }

    @Test
    @DisplayName("Throw a ValidationException when the article already exists.")
    void createArticle_ThrowValidationException_WhenArticle_AlreadyExists() {
        // Given
        Article article = new Article(1L, "Article1", "Description", 3,
                new BigDecimal("123.456"),
                new Brand(1L, null, null),
                List.of(
                        new Category(1L, null, null)
                )
        );

        // When
        Mockito.when(articlePersistencePort.existByName("Article1")).thenReturn(true);
        Mockito.when(brandPersistencePort.existById(article.getBrand().getId())).thenReturn(true);
        Mockito.when(categoryPersistencePort.existById(article.getCategories().get(0).getId())).thenReturn(true);

        ValidationException exception = assertThrows(ValidationException.class, () -> {
            articleUseCase.createArticle(article);
        });

        //Then
        assertThat(exception.getErrors()).contains(DomainConstants.ARTICLE_EXISTS_MESSAGE);
        Mockito.verify(articlePersistencePort, Mockito.never()).createArticle(article);
        Mockito.verify(categoryPersistencePort, times(1)).existById(article.getCategories().get(0).getId());
        Mockito.verify(brandPersistencePort, times(1)).existById(article.getBrand().getId());
    }

    @Test
    @DisplayName("Throw a ValidationException when the page number is null.")
    void listArticles_ShouldThrowValidationException_WhenPageNumberIsNull(){
        ValidationException exception = assertThrows(ValidationException.class, () -> {
            articleUseCase.listArticles(null, 10, "name", "asc");
        });
        assertThat(exception.getErrors()).contains(DomainConstants.INVALID_PAGE_NUMBER_NULL_MESSAGE);
    }

    @Test
    @DisplayName("Throw a ValidationException when the page size is null.")
    void listArticles_ShouldThrowValidationException_WhenPageSizeIsNull(){
        ValidationException exception = assertThrows(ValidationException.class, () -> {
            articleUseCase.listArticles(0, null, "name", "asc");
        });
        assertThat(exception.getErrors()).contains(DomainConstants.INVALID_PAGE_SIZE_NULL_MESSAGE);
    }

    @Test
    @DisplayName("Throw a ValidationException when the page number is negative.")
    void listArticles_ShouldThrowValidationException_WhenPageNumberIsNegative(){
        ValidationException exception = assertThrows(ValidationException.class, () -> {
            articleUseCase.listArticles(-1, 10, "name", "asc");
        });
        assertThat(exception.getErrors()).contains(DomainConstants.INVALID_PAGE_NUMBER_MESSAGE);
    }

    @Test
    @DisplayName("Throw a ValidationException when the page size is less than or equal to zero.")
    void listArticles_ShouldThrowValidationException_WhenPageSizeIsLessThanOrEqualZero(){
        ValidationException exception = assertThrows(ValidationException.class, () -> {
            articleUseCase.listArticles(0, 0, "name", "asc");
        });
        assertThat(exception.getErrors()).contains(DomainConstants.INVALID_PAGE_SIZE_MESSAGE);
    }

    @Test
    @DisplayName("Throw a ValidationException when the sort field is invalid.")
    void listArticles_ShouldThrowValidationException_WhenSortByIsInvalid(){
        ValidationException exception = assertThrows(ValidationException.class, () -> {
            articleUseCase.listArticles(0, 10, "invalidField", "asc");
        });
        assertThat(exception.getErrors()).contains(DomainConstants.INVALID_SORT_FIELD_MESSAGE);
    }

    @Test
    @DisplayName("Throw a ValidationException when the sort direction is invalid.")
    void listArticles_ShouldThrowValidationException_WhenSortDirectionIsInvalid(){
        ValidationException exception = assertThrows(ValidationException.class, () -> {
            articleUseCase.listArticles(0, 10, "name", "invalidDirection");
        });
        assertThat(exception.getErrors()).contains(DomainConstants.INVALID_SORT_DIRECTION_MESSAGE);
    }

    @Test
    @DisplayName("Should list articles correctly with valid parameters and verify article details.")
    void listArticles_ShouldReturnBrandsWithDetails_WhenParametersAreValid(){
        //Given
        Article article = new Article(1L, "Article1", "Description", 3,
                new BigDecimal("123.456"),
                new Brand(1L, null, null),
                List.of(
                        new Category(1L, null, null)
                )
        );
        Article article2 = new Article(2L, "B_Article", "Description", 3,
                new BigDecimal("123.456"),
                new Brand(1L, null, null),
                List.of(
                        new Category(1L, null, null)
                )
        );
        Pagination<Article> pagination = new Pagination<>(List.of(article, article2),0,10,2L);

        Mockito.when(articlePersistencePort.listArticles(0,10,"name","asc")).thenReturn(pagination);

        //When
        Pagination<Article> result = articleUseCase.listArticles(0,10,"name","asc");

        //Then
        assertNotNull(result, "The result should not be null.");
        assertFalse(result.getContent().isEmpty(), "The content should not be empty.");
        assertEquals(2, result.getContent().size(), "The number of articles should be 2.");

        // Verify the details of the returned brand
        Article returnedArticle1 = result.getContent().get(0);
        assertEquals(1L, returnedArticle1.getId(), "The Article ID should be 1L");
        assertEquals("Article1", returnedArticle1.getName(), "The article name should be 'Article1'.");
        assertEquals("Description", returnedArticle1.getDescription(), "The article description should be 'Description'.");

        Article returnedArticle2 = result.getContent().get(1);
        assertEquals(2L, returnedArticle2.getId(), "The Article ID should be 1L");
        assertEquals("B_Article", returnedArticle2.getName(), "The article name should be 'B_Article'.");
        assertEquals("Description", returnedArticle2.getDescription(), "The article description should be 'Description'.");

        // Verify that the persistence port method was called once with the correct parameters
        Mockito.verify(articlePersistencePort, Mockito.times(1)).listArticles(0, 10, "name", "asc");
    }
}