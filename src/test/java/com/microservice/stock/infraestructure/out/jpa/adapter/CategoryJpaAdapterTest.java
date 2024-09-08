package com.microservice.stock.infraestructure.out.jpa.adapter;

import com.microservice.stock.domain.model.Category;
import com.microservice.stock.domain.util.Pagination;
import com.microservice.stock.infraestructure.out.jpa.entity.CategoryEntity;
import com.microservice.stock.infraestructure.out.jpa.mapper.CategoryEntityMapper;
import com.microservice.stock.infraestructure.out.jpa.mapper.CategoryPageMapper;
import com.microservice.stock.infraestructure.out.jpa.repository.ICategoryRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.*;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CategoryJpaAdapterTest {

    @Mock
    private ICategoryRepository categoryRepository;

    @Mock
    private CategoryEntityMapper categoryEntityMapper;

    @InjectMocks
    private CategoryJpaAdapter categoryJpaAdapter;

    private CategoryPageMapper categoryPageMapper;

    @BeforeEach
    void setUp() {
        categoryPageMapper = new CategoryPageMapper(categoryEntityMapper);
        categoryJpaAdapter = new CategoryJpaAdapter(categoryRepository, categoryEntityMapper);
    }

    @Test
    @DisplayName("Should correctly save the category in the database")
    void createCategory_ShouldSaveCategory() {
        Category category = new Category(1L, "CategoryName", "CategoryDescription");
        CategoryEntity categoryEntity = categoryEntityMapper.toEntity(category);
        given(categoryRepository.save(categoryEntity)).willReturn(categoryEntity);

        categoryJpaAdapter.createCategory(category);

        verify(categoryRepository, times(1)).save(categoryEntity);

    }

    @Test
    @DisplayName("Should return true if the category already exists")
    void existsByName_ShouldReturnTrue_WhenCategoryExists() {
        // Given
        String name = "Existing Category";
        when(categoryRepository.findByName(name)).thenReturn(Optional.of(new CategoryEntity()));

        // When
        boolean exists = categoryJpaAdapter.existsByName(name);

        // Then
        assertTrue(exists);
        verify(categoryRepository, times(1)).findByName(name);
    }

    @Test
    @DisplayName("Should return false if the category does not exist")
    void existsByName_ShouldReturnFalse_WhenCategoryDoesNotExist() {
        // Given
        String name = "Non-Existing Category";
        when(categoryRepository.findByName(name)).thenReturn(Optional.empty());

        // When
        boolean exists = categoryJpaAdapter.existsByName(name);

        // Then
        assertFalse(exists);
        verify(categoryRepository, times(1)).findByName(name);
    }

    @Test
    @DisplayName("Should return paginated categories")
    void listCategory_ShouldReturnPaginatedCategories() {
        // Given
        int pageNumber = 0;
        int pageSize = 5;
        String sortBy = "name";
        String sortDirection = "asc";

        // Mocking entities
        CategoryEntity categoryEntity1 = new CategoryEntity(1L, "Category 1", "Description 1");
        CategoryEntity categoryEntity2 = new CategoryEntity(2L, "Category 2", "Description 2");

        List<CategoryEntity> categoryEntities = List.of(categoryEntity1, categoryEntity2);
        Page<CategoryEntity> page = new PageImpl<>(categoryEntities, PageRequest.of(pageNumber, pageSize),2);

        when(categoryRepository.findAll(any(Pageable.class))).thenReturn(page);
        when(categoryEntityMapper.toDomain(categoryEntity1)).thenReturn(new Category(1L, "Category 1", "Description 1"));
        when(categoryEntityMapper.toDomain(categoryEntity2)).thenReturn(new Category(2L, "Category 2", "Description 2"));

        // When
        Pagination<Category> result = categoryJpaAdapter.listCategory(pageNumber, pageSize, sortBy, sortDirection);

        // Then
        assertThat(result.getContent()).hasSize(2);
        assertThat(result.getPageNumber()).isZero();
        assertThat(result.getPageSize()).isEqualTo(5);
        verify(categoryRepository, times(1)).findAll(any(Pageable.class));
    }

    @Test
    @DisplayName("Should return empty pagination when no categories exist")
    void listCategory_ShouldReturnEmptyPagination_WhenNoCategoriesExist() {
        //Given
        int pageNumber = 0;
        int pageSize = 5;
        String sortBy = "name";
        String sortDirection = "asc";

        Page<CategoryEntity> emptyPage = new PageImpl<>(List.of(), PageRequest.of(pageNumber, pageSize), 0);

        when(categoryRepository.findAll(any(Pageable.class))).thenReturn(emptyPage);

        //When
        Pagination<Category> result = categoryJpaAdapter.listCategory(pageNumber, pageSize, sortBy, sortDirection);

        //Then
        assertThat(result.getContent()).isEmpty();
        assertThat(result.getTotalElements()).isZero();
        assertThat(result.getPageNumber()).isZero();
        assertThat(result.getPageSize()).isEqualTo(5);
        verify(categoryRepository, times(1)).findAll(any(Pageable.class));
    }

    @Test
    @DisplayName("Should sort categories in descending order by name")
    void listCategory_ShouldSortCategoriesDescending_WhenSortDirectionIsDesc() {
        //Given
        int pageNumber = 0;
        int pageSize = 5;
        String sortBy = "name";
        String sortDirection = "desc";

        CategoryEntity categoryEntity1 = new CategoryEntity(1L, "Category A", "Description A");
        CategoryEntity categoryEntity2 = new CategoryEntity(2L, "Category B", "Description B");

        List<CategoryEntity> categoryEntities = List.of(categoryEntity2, categoryEntity1);
        Page<CategoryEntity> page = new PageImpl<>(categoryEntities, PageRequest.of(pageNumber, pageSize, Sort.by(Sort.Order.desc(sortBy))), categoryEntities.size());

        when(categoryRepository.findAll(any(Pageable.class))).thenReturn(page);
        when(categoryEntityMapper.toDomain(categoryEntity1)).thenReturn(new Category(1L, "Category A", "Description A"));
        when(categoryEntityMapper.toDomain(categoryEntity2)).thenReturn(new Category(2L, "Category B", "Description B"));

        // When
        Pagination<Category> result = categoryJpaAdapter.listCategory(pageNumber, pageSize, sortBy, sortDirection);

        // Then
        assertThat(result.getContent()).hasSize(2);
        assertThat(result.getPageSize()).isEqualTo(5);
        assertThat(result.getContent().get(0).getName()).isEqualTo("Category B");
        assertThat(result.getContent().get(1).getName()).isEqualTo("Category A");
        verify(categoryRepository, times(1)).findAll(any(Pageable.class));
    }

    @Test
    @DisplayName("Should return correct pagination size")
    void listCategory_ShouldReturnCorrectPaginationSize() {
        //Given
        int pageNumber = 0;
        int pageSize = 10;
        String sortBy = "name";
        String sortDirection = "asc";

        CategoryEntity categoryEntity1 = new CategoryEntity(1L, "Category 1", "Description 1");
        CategoryEntity categoryEntity2 = new CategoryEntity(2L, "Category 2", "Description 2");

        List<CategoryEntity> categoryEntities = List.of(categoryEntity1, categoryEntity2);
        Page<CategoryEntity> page = new PageImpl<>(categoryEntities, PageRequest.of(pageNumber, pageSize), 2);

        when(categoryRepository.findAll(any(Pageable.class))).thenReturn(page);
        when(categoryEntityMapper.toDomain(categoryEntity1)).thenReturn(new Category(1L, "Category 1", "Description 1"));
        when(categoryEntityMapper.toDomain(categoryEntity2)).thenReturn(new Category(2L, "Category 2", "Description 2"));

        //When
        Pagination<Category> result = categoryJpaAdapter.listCategory(pageNumber, pageSize, sortBy, sortDirection);

        //Then
        assertThat(result.getContent()).hasSize(2);
        assertThat(result.getPageSize()).isEqualTo(10);
        assertThat(result.getTotalElements()).isEqualTo(2);
        verify(categoryRepository, times(1)).findAll(any(Pageable.class));
    }

    @Test
    @DisplayName("Should map CategoryEntity to Category correctly in pagination")
    void listCategory_ShouldMapCategoryEntityToCategoryCorrectly() {
        // Given
        int pageNumber = 0;
        int pageSize = 5;
        String sortBy = "name";
        String sortDirection = "asc";

        CategoryEntity categoryEntity1 = new CategoryEntity(1L, "Category 1", "Description 1");
        CategoryEntity categoryEntity2 = new CategoryEntity(2L, "Category 2", "Description 2");

        List<CategoryEntity> categoryEntities = List.of(categoryEntity1, categoryEntity2);
        Page<CategoryEntity> page = new PageImpl<>(categoryEntities);

        when(categoryRepository.findAll(any(Pageable.class))).thenReturn(page);
        when(categoryEntityMapper.toDomain(categoryEntity1)).thenReturn(new Category(1L, "Category 1", "Description 1"));
        when(categoryEntityMapper.toDomain(categoryEntity2)).thenReturn(new Category(2L, "Category 2", "Description 2"));

        //When
        Pagination<Category> result = categoryJpaAdapter.listCategory(pageNumber, pageSize, sortBy, sortDirection);

        //Then
        assertThat(result.getContent()).hasSize(2);
        assertThat(result.getContent().get(0).getName()).isEqualTo("Category 1");
        assertThat(result.getContent().get(1).getName()).isEqualTo("Category 2");
        verify(categoryEntityMapper, times(1)).toDomain(categoryEntity1);
        verify(categoryEntityMapper, times(1)).toDomain(categoryEntity2);
    }
}