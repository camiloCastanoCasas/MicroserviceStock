package com.microservice.stock.domain.usecase;

import com.microservice.stock.domain.exceptions.ValidationException;
import com.microservice.stock.domain.model.Category;
import com.microservice.stock.domain.spi.ICategoryPersistencePort;
import com.microservice.stock.domain.util.DomainConstants;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class CategoryUseCaseTest {

    @Mock
    private ICategoryPersistencePort categoryPersistencePort;

    @InjectMocks
    private CategoryUseCase categoryUseCase;

    @Test
    @DisplayName("Dada una categoria debe insertarla correctamente en la base de datos.")
    void createCategory() {
        //Given (Dado)
        Category category = new Category(1L, "CategoryName", "DescriptionName");

        Mockito.when(categoryPersistencePort.existsByName("CategoryName")).thenReturn(false);
        //When (Cuando)

        categoryUseCase.createCategory(category);

        //Then (Entonces)
        Mockito.verify(categoryPersistencePort, Mockito.times(1)).createCategory(category);
    }

    @Test
    @DisplayName("Debe lanzar una ValidationException cuando el nombre está vacío.")
    void createCategory_ShouldThrowValidationException_WhenNameIsEmpty() {
        // Given (Dado)
        Category category = new Category(1L, "", "DescriptionName");

        // When & Then (Cuando & Entonces)
        ValidationException exception = assertThrows(ValidationException.class, () -> {
            categoryUseCase.createCategory(category);
        });

        assertThat(exception.getErrors()).contains(DomainConstants.FIELD_NAME_NULL_MESSAGE);
        Mockito.verify(categoryPersistencePort, Mockito.never()).createCategory(category);
    }

    @Test
    @DisplayName("Debe lanzar ValidationException cuando el nombre de la categoría supera los 50 caracteres.")
    void createCategory_NameTooLong_ShouldThrowValidationException() {
        // Given (Dado)
        String longName = "A".repeat(51);
        Category category = new Category(1L, longName, "DescriptionName");

        // When (Cuando) y Then (Entonces)
        ValidationException exception = assertThrows(ValidationException.class, () -> {
            categoryUseCase.createCategory(category);
        });

        assertThat(exception.getErrors()).contains(DomainConstants.FIELD_NAME_CATEGORY_SIZE_MESSAGE);
        Mockito.verify(categoryPersistencePort, Mockito.never()).createCategory(category);
    }

    @Test
    @DisplayName("Debe lanzar ValidationException cuando la descripción de la categoría está vacía.")
    void createCategory_EmptyDescription_ShouldThrowValidationException() {
        // Given (Dado)
        Category category = new Category(1L, "CategoryName", "");

        // When (Cuando) y Then (Entonces)
        ValidationException exception = assertThrows(ValidationException.class, () -> {
            categoryUseCase.createCategory(category);
        });

        assertThat(exception.getErrors()).contains(DomainConstants.FIELD_DESCRIPTION_NULL_MESSAGE);
        Mockito.verify(categoryPersistencePort, Mockito.never()).createCategory(category);
    }

    @Test
    @DisplayName("Debe lanzar ValidationException cuando la descripción de la categoría supera los 90 caracteres.")
    void createCategory_DescriptionTooLong_ShouldThrowValidationException() {
        // Given (Dado)
        String longDescription = "This description is deliberately made very long to exceed the maximum allowed length of ninety characters.";
        Category category = new Category(1L, "CategoryName", longDescription);

        // When (Cuando) y Then (Entonces)
        ValidationException exception = assertThrows(ValidationException.class, () -> {
            categoryUseCase.createCategory(category);
        });

        assertThat(exception.getErrors()).contains(DomainConstants.FIELD_DESCRIPTION_CATEGORY_SIZE_MESSAGE);
        Mockito.verify(categoryPersistencePort, Mockito.never()).createCategory(category);
    }

    @Test
    @DisplayName("Debe lanzar ValidationException cuando el nombre y la descripción de la categoría está vacía.")
    void createCategory_EmptyNameAndDescription_ShouldThrowValidationException() {
        // Given (Dado)
        Category category = new Category(1L, "", "");

        // When (Cuando) y Then (Entonces)
        ValidationException exception = assertThrows(ValidationException.class, () -> {
            categoryUseCase.createCategory(category);
        });

        assertThat(exception.getErrors()).contains(DomainConstants.FIELD_DESCRIPTION_NULL_MESSAGE, DomainConstants.FIELD_DESCRIPTION_NULL_MESSAGE);
        Mockito.verify(categoryPersistencePort, Mockito.never()).createCategory(category);
    }

    @Test
    @DisplayName("Debe lanzar ValidationException cuando el nombre de la categoría supera los 50 caracteres y la descripción los 90 caracteres.")
    void createCategory_NameAndDescriptionTooLong_ShouldThrowValidationException() {
        // Given (Dado)
        String longName = "A".repeat(51);
        String longDescription = "A".repeat(91);
        Category category = new Category(1L, longName, longDescription);

        // When (Cuando) y Then (Entonces)
        ValidationException exception = assertThrows(ValidationException.class, () -> {
            categoryUseCase.createCategory(category);
        });

        assertThat(exception.getErrors()).contains(DomainConstants.FIELD_NAME_CATEGORY_SIZE_MESSAGE, DomainConstants.FIELD_DESCRIPTION_CATEGORY_SIZE_MESSAGE);
        Mockito.verify(categoryPersistencePort, Mockito.never()).createCategory(category);
    }

    @Test
    @DisplayName("Debe lanzar una ValidationException cuando la categoría ya existe.")
    void createCategory_ShouldThrowValidationException_WhenCategoryAlreadyExists() {
        // Given (Dado)
        Category category = new Category(1L, "ExistingCategory", "DescriptionName");

        // Simulamos que ya existe una categoría con el mismo nombre
        Mockito.when(categoryPersistencePort.existsByName("ExistingCategory")).thenReturn(true);

        // When & Then (Cuando & Entonces)
        ValidationException exception = assertThrows(ValidationException.class, () -> {
            categoryUseCase.createCategory(category);
        });

        assertThat(exception.getErrors()).contains(DomainConstants.CATEGORY_EXISTS_MESSAGE);
        Mockito.verify(categoryPersistencePort, Mockito.never()).createCategory(category);
    }
}