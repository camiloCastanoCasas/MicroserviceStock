package com.microservice.stock.domain.usecase;

import com.microservice.stock.domain.exceptions.ValidationException;
import com.microservice.stock.domain.model.Brand;
import com.microservice.stock.domain.spi.IBrandPersistencePort;
import com.microservice.stock.domain.util.DomainConstants;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(MockitoExtension.class)
class BrandUseCaseTest {

    @Mock
    private IBrandPersistencePort brandPersistencePort;

    @InjectMocks
    private BrandUseCase brandUseCase;

    @Test
    @DisplayName("Given a brand, insert it correctly into the database.")
    void createBrand() {

        //Given
        Brand brand = new Brand(1L, "BrandName", "BrandDescription");
        Mockito.when(brandPersistencePort.existsByName("BrandName")).thenReturn(false);

        //When
        brandUseCase.createBrand(brand);

        //Then
        Mockito.verify(brandPersistencePort, Mockito.times(1)).createBrand(brand);
    }

    @Test
    @DisplayName("Throw a ValidationException when the name is empty.")
    void createBrand_ThrowValidationException_WhenNameIsEmpty() {
        // Given
        Brand brand = new Brand(1L, "", "BrandDescription");

        // When
        ValidationException exception = assertThrows(ValidationException.class, () -> {
            brandUseCase.createBrand(brand);
        });

        //Then
        assertThat(exception.getErrors()).contains(DomainConstants.FIELD_NAME_NULL_MESSAGE);
        Mockito.verify(brandPersistencePort, Mockito.never()).createBrand(brand);
    }

    @Test
    @DisplayName("Throw a ValidationException when the brand name exceeds 50 characters")
    void createBrand_NameTooLong_ThrowValidationException() {
        // Given
        String longName = "A".repeat(51);
        Brand brand = new Brand(1L, longName, "BrandDescription");

        // When
        ValidationException exception = assertThrows(ValidationException.class, () -> {
            brandUseCase.createBrand(brand);
        });

        //Then
        assertThat(exception.getErrors()).contains(DomainConstants.FIELD_NAME_BRAND_SIZE_MESSAGE);
        Mockito.verify(brandPersistencePort, Mockito.never()).createBrand(brand);
    }

    @Test
    @DisplayName("Throw a ValidationException when the brand description is empty.")
    void createBrand_EmptyDescription_ThrowValidationException() {
        // Given
        Brand brand = new Brand(1L, "BrandName", "");

        // When
        ValidationException exception = assertThrows(ValidationException.class, () -> {
            brandUseCase.createBrand(brand);
        });

        //Then
        assertThat(exception.getErrors()).contains(DomainConstants.FIELD_DESCRIPTION_NULL_MESSAGE);
        Mockito.verify(brandPersistencePort, Mockito.never()).createBrand(brand);
    }

    @Test
    @DisplayName("Throw a ValidationException when the brand description exceeds 120 characters.")
    void createBrand_DescriptionTooLong_ThrowValidationException() {
        // Given
        String longDescription = "A".repeat(121);
        Brand brand = new Brand(1L, "BrandName", longDescription);

        // When
        ValidationException exception = assertThrows(ValidationException.class, () -> {
            brandUseCase.createBrand(brand);
        });

        //Then
        assertThat(exception.getErrors()).contains(DomainConstants.FIELD_DESCRIPTION_BRAND_SIZE_MESSAGE);
        Mockito.verify(brandPersistencePort, Mockito.never()).createBrand(brand);
    }

    @Test
    @DisplayName("Throw a ValidationException when both the brand name and description are empty.")
    void createBrand_EmptyNameAndDescription_ThrowValidationException() {
        // Given (Dado)
        Brand brand = new Brand(1L, "", "");

        // When
        ValidationException exception = assertThrows(ValidationException.class, () -> {
            brandUseCase.createBrand(brand);
        });

        //Then
        assertThat(exception.getErrors()).contains(DomainConstants.FIELD_NAME_NULL_MESSAGE, DomainConstants.FIELD_DESCRIPTION_NULL_MESSAGE);
        Mockito.verify(brandPersistencePort, Mockito.never()).createBrand(brand);
    }

    @Test
    @DisplayName("Throw a ValidationException when the brand name exceeds 50 characters and the description exceeds 120 characters.")
    void createBrand_NameAndDescriptionTooLong_ThrowValidationException() {
        // Given (Dado)
        String longName = "A".repeat(51);
        String longDescription = "A".repeat(121);
        Brand brand = new Brand(1L, longName, longDescription);

        // When
        ValidationException exception = assertThrows(ValidationException.class, () -> {
            brandUseCase.createBrand(brand);
        });

        //Then
        assertThat(exception.getErrors()).contains(DomainConstants.FIELD_NAME_BRAND_SIZE_MESSAGE, DomainConstants.FIELD_DESCRIPTION_BRAND_SIZE_MESSAGE);
        Mockito.verify(brandPersistencePort, Mockito.never()).createBrand(brand);
    }

    @Test
    @DisplayName("Throw a ValidationException when the brand already exists.")
    void createBrand_ThrowValidationException_WhenBrandAlreadyExists() {
        // Given (Dado)
        Brand brand = new Brand(1L, "ExistingBrand", "BrandDescription");
        Mockito.when(brandPersistencePort.existsByName("ExistingBrand")).thenReturn(true);

        // When
        ValidationException exception = assertThrows(ValidationException.class, () -> {
            brandUseCase.createBrand(brand);
        });

        //Then
        assertThat(exception.getErrors()).contains(DomainConstants.BRAND_EXISTS_MESSAGE);
        Mockito.verify(brandPersistencePort, Mockito.never()).createBrand(brand);
    }
}