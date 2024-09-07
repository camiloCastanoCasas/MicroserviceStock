package com.microservice.stock.domain.usecase;

import com.microservice.stock.domain.exceptions.ValidationException;
import com.microservice.stock.domain.model.Brand;
import com.microservice.stock.domain.spi.IBrandPersistencePort;
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

    @Test
    @DisplayName("Throw a ValidationException when the page number is null.")
    void listBrands_ShouldThrowValidationException_WhenPageNumberIsNull(){
        ValidationException exception = assertThrows(ValidationException.class, () -> {
           brandUseCase.listBrands(null, 10, "name", "asc");
        });
        assertThat(exception.getErrors()).contains(DomainConstants.INVALID_PAGE_NUMBER_NULL_MESSAGE);
    }

    @Test
    @DisplayName("Throw a ValidationException when the page size is null.")
    void listBrands_ShouldThrowValidationException_WhenPageSizeIsNull(){
        ValidationException exception = assertThrows(ValidationException.class, () -> {
            brandUseCase.listBrands(0, null, "name", "asc");
        });
        assertThat(exception.getErrors()).contains(DomainConstants.INVALID_PAGE_SIZE_NULL_MESSAGE);
    }

    @Test
    @DisplayName("Throw a ValidationException when the page number is negative.")
    void listBrands_ShouldThrowValidationException_WhenPageNumberIsNegative(){
        ValidationException exception = assertThrows(ValidationException.class, () -> {
            brandUseCase.listBrands(-1, 10, "name", "asc");
        });
        assertThat(exception.getErrors()).contains(DomainConstants.INVALID_PAGE_NUMBER_MESSAGE);
    }

    @Test
    @DisplayName("Throw a ValidationException when the page size is less than or equal to zero.")
    void listBrands_ShouldThrowValidationException_WhenPageSizeIsLessThanOrEqualZero(){
        ValidationException exception = assertThrows(ValidationException.class, () -> {
            brandUseCase.listBrands(0, 0, "name", "asc");
        });
        assertThat(exception.getErrors()).contains(DomainConstants.INVALID_PAGE_SIZE_MESSAGE);
    }

    @Test
    @DisplayName("Throw a ValidationException when the sort field is invalid.")
    void listBrands_ShouldThrowValidationException_WhenSortByIsInvalid(){
        ValidationException exception = assertThrows(ValidationException.class, () -> {
            brandUseCase.listBrands(0, 10, "invalidField", "asc");
        });
        assertThat(exception.getErrors()).contains(DomainConstants.INVALID_SORT_FIELD_MESSAGE);
    }

    @Test
    @DisplayName("Throw a ValidationException when the sort direction is invalid.")
    void listBrands_ShouldThrowValidationException_WhenSortDirectionIsInvalid(){
        ValidationException exception = assertThrows(ValidationException.class, () -> {
            brandUseCase.listBrands(0, 10, "name", "invalidDirection");
        });
        assertThat(exception.getErrors()).contains(DomainConstants.INVALID_SORT_DIRECTION_MESSAGE);
    }

    @Test
    @DisplayName("Should list categories correctly with valid parameters and verify category details.")
    void listBrands_ShouldReturnCategoriesWithDetails_WhenParametersAreValid(){
        //Given
        Brand brand = new Brand(1L, "Brand1", "Description1");
        Pagination<Brand> pagination = new Pagination<>(List.of(brand),0,10,1L);

        Mockito.when(brandPersistencePort.listBrands(0,10,"name","asc")).thenReturn(pagination);

        //When
        Pagination<Brand> result = brandUseCase.listBrands(0,10,"name","asc");

        //Then
        assertNotNull(result, "The result should not be null.");
        assertFalse(result.getContent().isEmpty(), "The content should not be empty.");
        assertEquals(1, result.getContent().size(), "The number of categories should be 1.");

        // Verify the details of the returned brand
        Brand returnedBrand = result.getContent().get(0);
        assertEquals(1L, returnedBrand.getId(), "The Brand ID should be 1L");
        assertEquals("Brand1", returnedBrand.getName(), "The brand name should be 'Brand1'.");
        assertEquals("Description1", returnedBrand.getDescription(), "The brand description should be 'Description1'.");

        // Verify that the persistence port method was called once with the correct parameters
        Mockito.verify(brandPersistencePort, Mockito.times(1)).listBrands(0, 10, "name", "asc");
    }
}