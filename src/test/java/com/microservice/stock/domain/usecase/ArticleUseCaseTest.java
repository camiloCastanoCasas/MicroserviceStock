package com.microservice.stock.domain.usecase;

import com.microservice.stock.domain.exceptions.ValidationException;
import com.microservice.stock.domain.model.Article;
import com.microservice.stock.domain.model.Brand;
import com.microservice.stock.domain.model.Category;
import com.microservice.stock.domain.spi.IArticlePersistencePort;
import com.microservice.stock.domain.spi.IBrandPersistencePort;
import com.microservice.stock.domain.spi.ICategoryPersistencePort;
import com.microservice.stock.domain.util.DomainConstants;
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
import static org.junit.jupiter.api.Assertions.assertThrows;
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
}