package com.microservice.stock.infraestructure.out.jpa.adapter;

import com.microservice.stock.domain.model.Category;
import com.microservice.stock.infraestructure.out.jpa.entity.CategoryEntity;
import com.microservice.stock.infraestructure.out.jpa.mapper.CategoryEntityMapper;
import com.microservice.stock.infraestructure.out.jpa.repository.ICategoryRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class CategoryJpaAdapterTest {

    @Mock
    private ICategoryRepository categoryRepository;

    @Mock
    private CategoryEntityMapper categoryEntityMapper;

    @InjectMocks
    private CategoryJpaAdapter categoryJpaAdapter;

    @Test
    @DisplayName("Debería guardar correctamente la categoría en la base de datos")
    void createCategory_ShouldSaveCategory() {
        Category category = new Category(1L, "CategoryName", "CategoryDescription");
        CategoryEntity categoryEntity = categoryEntityMapper.toEntity(category);
        given(categoryRepository.save(categoryEntity)).willReturn(categoryEntity);

        categoryJpaAdapter.createCategory(category);

        verify(categoryRepository, times(1)).save(categoryEntity);

    }

    @Test
    @DisplayName("Debería retornar true si la categoría ya existe")
    void existsByName_ShouldReturnTrue_WhenCategoryExists() {
        // Given
        String name = "Existing Category";
        Mockito.when(categoryRepository.findByName(name)).thenReturn(Optional.of(new CategoryEntity()));

        // When
        boolean exists = categoryJpaAdapter.existsByName(name);

        // Then
        assertTrue(exists);
        verify(categoryRepository, times(1)).findByName(name);
    }

    @Test
    @DisplayName("Debería retornar false si la categoría no existe")
    void existsByName_ShouldReturnFalse_WhenCategoryDoesNotExist() {
        // Given
        String name = "Non-Existing Category";
        Mockito.when(categoryRepository.findByName(name)).thenReturn(Optional.empty());

        // When
        boolean exists = categoryJpaAdapter.existsByName(name);

        // Then
        assertFalse(exists);
        verify(categoryRepository, times(1)).findByName(name);
    }
}