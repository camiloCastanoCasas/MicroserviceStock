package com.microservice.stock.infraestructure.out.jpa.adapter;

import com.microservice.stock.domain.model.Brand;
import com.microservice.stock.infraestructure.out.jpa.entity.BrandEntity;
import com.microservice.stock.infraestructure.out.jpa.mapper.BrandEntityMapper;
import com.microservice.stock.infraestructure.out.jpa.repository.IBrandRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class BrandJpaAdapterTest {

    @Mock
    private IBrandRepository brandRepository;

    @Mock
    private BrandEntityMapper brandEntityMapper;

    @InjectMocks
    private BrandJpaAdapter brandJpaAdapter;

    @Test
    @DisplayName("Should save the brand correctly in the database")
    void createBrand_ShouldSaveCategory() {
        Brand brand = new Brand(1L, "BrandName", "BrandDescription");
        BrandEntity brandEntity = brandEntityMapper.toEntity(brand);
        given(brandRepository.save(brandEntity)).willReturn(brandEntity);

        brandJpaAdapter.createBrand(brand);

        verify(brandRepository, times(1)).save(brandEntity);
    }

    @Test
    @DisplayName("Should return true if the brand already exists")
    void existsByName_ShouldReturnTrue_WhenBrandExists() {
        // Given
        String name = "Existing Brand";
        Mockito.when(brandRepository.findByName(name)).thenReturn(Optional.of(new BrandEntity()));

        // When
        boolean exists = brandJpaAdapter.existsByName(name);

        // Then
        assertTrue(exists);
        verify(brandRepository, times(1)).findByName(name);
    }

    @Test
    @DisplayName("Should return false if the brand does not exist")
    void existsByName_ShouldReturnFalse_WhenBrandDoesNotExist() {
        // Given
        String name = "Non-Existing Brand";
        Mockito.when(brandRepository.findByName(name)).thenReturn(Optional.empty());

        // When
        boolean exists = brandJpaAdapter.existsByName(name);

        // Then
        assertFalse(exists);
        verify(brandRepository, times(1)).findByName(name);
    }
}