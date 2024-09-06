package com.microservice.stock.domain.usecase;

import com.microservice.stock.domain.exceptions.ValidationException;
import com.microservice.stock.domain.model.Category;
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

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class CategoryUseCaseTest {

    @Mock
    private ICategoryPersistencePort categoryPersistencePort;

    @InjectMocks
    private CategoryUseCase categoryUseCase;

    @Test
    @DisplayName("Given a category, it should be inserted correctly into the database.")
    void createCategory() {
        //Given
        Category category = new Category(1L, "CategoryName", "DescriptionName");
        Mockito.when(categoryPersistencePort.existsByName("CategoryName")).thenReturn(false);

        //When
        categoryUseCase.createCategory(category);

        //Then
        Mockito.verify(categoryPersistencePort, Mockito.times(1)).createCategory(category);
    }

    @Test
    @DisplayName("Throw a ValidationException when the name is empty.")
    void createCategory_ShouldThrowValidationException_WhenNameIsEmpty() {
        // Given
        Category category = new Category(1L, "", "DescriptionName");

        // When & Then
        ValidationException exception = assertThrows(ValidationException.class, () -> {
            categoryUseCase.createCategory(category);
        });

        assertThat(exception.getErrors()).contains(DomainConstants.FIELD_NAME_NULL_MESSAGE);
        Mockito.verify(categoryPersistencePort, Mockito.never()).createCategory(category);
    }

    @Test
    @DisplayName("Throw a ValidationException when the category name exceeds 50 characters.")
    void createCategory_NameTooLong_ShouldThrowValidationException() {
        // Given
        String longName = "A".repeat(51);
        Category category = new Category(1L, longName, "DescriptionName");

        // When & Then
        ValidationException exception = assertThrows(ValidationException.class, () -> {
            categoryUseCase.createCategory(category);
        });

        assertThat(exception.getErrors()).contains(DomainConstants.FIELD_NAME_CATEGORY_SIZE_MESSAGE);
        Mockito.verify(categoryPersistencePort, Mockito.never()).createCategory(category);
    }

    @Test
    @DisplayName("Throw a ValidationException when the category description is empty.")
    void createCategory_EmptyDescription_ShouldThrowValidationException() {
        // Given
        Category category = new Category(1L, "CategoryName", "");

        // When & Then
        ValidationException exception = assertThrows(ValidationException.class, () -> {
            categoryUseCase.createCategory(category);
        });

        assertThat(exception.getErrors()).contains(DomainConstants.FIELD_DESCRIPTION_NULL_MESSAGE);
        Mockito.verify(categoryPersistencePort, Mockito.never()).createCategory(category);
    }

    @Test
    @DisplayName("Throw a ValidationException when the category description exceeds 90 characters.")
    void createCategory_DescriptionTooLong_ShouldThrowValidationException() {
        // Given
        String longDescription = "This description is deliberately made very long to exceed the maximum allowed length of ninety characters.";
        Category category = new Category(1L, "CategoryName", longDescription);

        // When & Then
        ValidationException exception = assertThrows(ValidationException.class, () -> {
            categoryUseCase.createCategory(category);
        });

        assertThat(exception.getErrors()).contains(DomainConstants.FIELD_DESCRIPTION_CATEGORY_SIZE_MESSAGE);
        Mockito.verify(categoryPersistencePort, Mockito.never()).createCategory(category);
    }

    @Test
    @DisplayName("Throw a ValidationException when both the category name and description are empty.")
    void createCategory_EmptyNameAndDescription_ShouldThrowValidationException() {
        // Given
        Category category = new Category(1L, "", "");

        // When & Then
        ValidationException exception = assertThrows(ValidationException.class, () -> {
            categoryUseCase.createCategory(category);
        });

        assertThat(exception.getErrors()).contains(DomainConstants.FIELD_DESCRIPTION_NULL_MESSAGE, DomainConstants.FIELD_DESCRIPTION_NULL_MESSAGE);
        Mockito.verify(categoryPersistencePort, Mockito.never()).createCategory(category);
    }

    @Test
    @DisplayName("Throw a ValidationException when the category name exceeds 50 characters and the description exceeds 90 characters.")
    void createCategory_NameAndDescriptionTooLong_ShouldThrowValidationException() {
        // Given
        String longName = "A".repeat(51);
        String longDescription = "A".repeat(91);
        Category category = new Category(1L, longName, longDescription);

        // When & Then
        ValidationException exception = assertThrows(ValidationException.class, () -> {
            categoryUseCase.createCategory(category);
        });

        assertThat(exception.getErrors()).contains(DomainConstants.FIELD_NAME_CATEGORY_SIZE_MESSAGE, DomainConstants.FIELD_DESCRIPTION_CATEGORY_SIZE_MESSAGE);
        Mockito.verify(categoryPersistencePort, Mockito.never()).createCategory(category);
    }

    @Test
    @DisplayName("Throw a ValidationException when the category already exists.")
    void createCategory_ShouldThrowValidationException_WhenCategoryAlreadyExists() {
        // Given
        Category category = new Category(1L, "ExistingCategory", "DescriptionName");
        Mockito.when(categoryPersistencePort.existsByName("ExistingCategory")).thenReturn(true);

        // When & Then
        ValidationException exception = assertThrows(ValidationException.class, () -> {
            categoryUseCase.createCategory(category);
        });

        assertThat(exception.getErrors()).contains(DomainConstants.CATEGORY_EXISTS_MESSAGE);
        Mockito.verify(categoryPersistencePort, Mockito.never()).createCategory(category);
    }

    @Test
    @DisplayName("Throw a ValidationException when the page number is null.")
    void listCategory_ShouldThrowValidationException_WhenPageNumberIsNull() {
        ValidationException exception = assertThrows(ValidationException.class, () -> {
            categoryUseCase.listCategory(null, 10, "name", "asc");
        });
        assertThat(exception.getErrors()).contains(DomainConstants.INVALID_PAGE_NUMBER_NULL_MESSAGE);
    }

    @Test
    @DisplayName("Throw a ValidationException when the page size is null.")
    void listCategory_ShouldThrowValidationException_WhenPageSizeIsNull() {
        ValidationException exception = assertThrows(ValidationException.class, () -> {
            categoryUseCase.listCategory(0, null, "name", "asc");
        });
        assertThat(exception.getErrors()).contains(DomainConstants.INVALID_PAGE_SIZE_NULL_MESSAGE);
    }

    @Test
    @DisplayName("Throw a ValidationException when the page number is negative.")
    void listCategory_ShouldThrowValidationException_WhenPageNumberIsNegative() {
        ValidationException exception = assertThrows(ValidationException.class, () -> {
            categoryUseCase.listCategory(-1, 10, "name", "asc");
        });
        assertThat(exception.getErrors()).contains(DomainConstants.INVALID_PAGE_NUMBER_MESSAGE);
    }

    @Test
    @DisplayName("Throw a ValidationException when the page size is less than or equal to zero.")
    void listCategory_ShouldThrowValidationException_WhenPageSizeIsLessThanOrEqualZero() {
        ValidationException exception = assertThrows(ValidationException.class, () -> {
            categoryUseCase.listCategory(0, 0, "name", "asc");
        });
        assertThat(exception.getErrors()).contains(DomainConstants.INVALID_PAGE_SIZE_MESSAGE);
    }

    @Test
    @DisplayName("Throw a ValidationException when the sort field is invalid.")
    void listCategory_ShouldThrowValidationException_WhenSortByIsInvalid() {
        ValidationException exception = assertThrows(ValidationException.class, () -> {
            categoryUseCase.listCategory(0, 10, "invalidField", "asc");
        });
        assertThat(exception.getErrors()).contains(DomainConstants.INVALID_SORT_FIELD_MESSAGE);
    }

    @Test
    @DisplayName("Throw a ValidationException when the sort direction is invalid.")
    void listCategory_ShouldThrowValidationException_WhenSortDirectionIsInvalid() {
        ValidationException exception = assertThrows(ValidationException.class, () -> {
            categoryUseCase.listCategory(0, 10, "name", "invalidDirection");
        });
        assertThat(exception.getErrors()).contains(DomainConstants.INVALID_SORT_DIRECTION_MESSAGE);
    }

    @Test
    @DisplayName("Should list categories correctly with valid parameters and verify category details.")
    void listCategory_ShouldReturnCategoriesWithDetails_WhenParametersAreValid() {
        // Given
        Category category = new Category(1L, "Category1", "Description1");
        Pagination<Category> pagination = new Pagination<>(List.of(category), 0, 10, 1L);

        Mockito.when(categoryPersistencePort.listCategory(0, 10, "name", "asc")).thenReturn(pagination);

        // When
        Pagination<Category> result = categoryUseCase.listCategory(0, 10, "name", "asc");

        // Then
        assertNotNull(result, "The result should not be null.");
        assertFalse(result.getContent().isEmpty(), "The content should not be empty.");
        assertEquals(1, result.getContent().size(), "The number of categories should be 1.");

        // Verify the details of the returned category
        Category returnedCategory = result.getContent().get(0);
        assertEquals(1L, returnedCategory.getId(), "The category ID should be 1L.");
        assertEquals("Category1", returnedCategory.getName(), "The category name should be 'Category1'.");
        assertEquals("Description1", returnedCategory.getDescription(), "The category description should be 'Description1'.");

        // Verify that the persistence port method was called once with the correct parameters
        Mockito.verify(categoryPersistencePort, Mockito.times(1)).listCategory(0, 10, "name", "asc");
    }


}