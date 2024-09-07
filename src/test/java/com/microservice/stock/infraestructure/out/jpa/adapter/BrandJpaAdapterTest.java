package com.microservice.stock.infraestructure.out.jpa.adapter;

import com.microservice.stock.domain.model.Brand;
import com.microservice.stock.domain.util.Pagination;
import com.microservice.stock.infraestructure.out.jpa.entity.BrandEntity;
import com.microservice.stock.infraestructure.out.jpa.mapper.BrandEntityMapper;
import com.microservice.stock.infraestructure.out.jpa.mapper.BrandPageMapper;
import com.microservice.stock.infraestructure.out.jpa.repository.IBrandRepository;
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
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class BrandJpaAdapterTest {

    @Mock
    private IBrandRepository brandRepository;

    @Mock
    private BrandEntityMapper brandEntityMapper;

    @InjectMocks
    private BrandJpaAdapter brandJpaAdapter;

    private BrandPageMapper brandPageMapper;

    @BeforeEach
    void setUp(){
        brandPageMapper = new BrandPageMapper(brandEntityMapper);
        brandJpaAdapter = new BrandJpaAdapter(brandRepository, brandEntityMapper);
    }

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
        when(brandRepository.findByName(name)).thenReturn(Optional.of(new BrandEntity()));

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
        when(brandRepository.findByName(name)).thenReturn(Optional.empty());

        // When
        boolean exists = brandJpaAdapter.existsByName(name);

        // Then
        assertFalse(exists);
        verify(brandRepository, times(1)).findByName(name);
    }

    @Test
    @DisplayName("Should return paginated brands")
    void listCategory_ShouldReturnPaginatedBrands() {
        // Given
        int pageNumber = 0;
        int pageSize = 5;
        String sortBy = "name";
        String sortDirection = "asc";

        // Mocking entities
        BrandEntity brandEntity1 = new BrandEntity(1L, "Brand 1", "Description 1");
        BrandEntity brandEntity2 = new BrandEntity(2L, "Brand 2", "Description 2");

        List<BrandEntity> brandEntities = List.of(brandEntity1,brandEntity2);
        Page<BrandEntity> page = new PageImpl<>(brandEntities, PageRequest.of(pageNumber, pageSize),2);

        when(brandRepository.findAll(any(Pageable.class))).thenReturn(page);
        when(brandEntityMapper.toDomain(brandEntity1)).thenReturn(new Brand(1L, "Category 1", "Description 1"));
        when(brandEntityMapper.toDomain(brandEntity2)).thenReturn(new Brand(2L, "Category 2", "Description 2"));

        // When
        Pagination<Brand> result = brandJpaAdapter.listBrands(pageNumber, pageSize, sortBy, sortDirection);

        // Then
        assertThat(result.getContent()).hasSize(2);
        assertThat(result.getPageNumber()).isZero();
        assertThat(result.getPageSize()).isEqualTo(5);
        verify(brandRepository, times(1)).findAll(any(Pageable.class));
    }

    @Test
    @DisplayName("Should return empty pagination when no brands exist")
    void listBrand_ShouldReturnEmptyPagination_WhenNoBrandsExist() {
        //Given
        int pageNumber = 0;
        int pageSize = 5;
        String sortBy = "name";
        String sortDirection = "asc";

        Page<BrandEntity> emptyPage = new PageImpl<>(List.of(), PageRequest.of(pageNumber, pageSize), 0);

        when(brandRepository.findAll(any(Pageable.class))).thenReturn(emptyPage);

        //When
        Pagination<Brand> result = brandJpaAdapter.listBrands(pageNumber, pageSize, sortBy, sortDirection);

        //Then
        assertThat(result.getContent()).isEmpty();
        assertThat(result.getTotalElements()).isZero();
        assertThat(result.getPageNumber()).isZero();
        assertThat(result.getPageSize()).isEqualTo(5);
        verify(brandRepository, times(1)).findAll(any(Pageable.class));
    }

    @Test
    @DisplayName("Should sort brands in descending order by name")
    void listBrand_ShouldSortBrandsDescending_WhenSortDirectionIsDesc() {
        //Given
        int pageNumber = 0;
        int pageSize = 5;
        String sortBy = "name";
        String sortDirection = "desc";

        BrandEntity brandEntity1 = new BrandEntity(1L, "Brand A", "Description A");
        BrandEntity brandEntity2 = new BrandEntity(2L, "Brand B", "Description B");

        List<BrandEntity> brandEntities = List.of(brandEntity2, brandEntity1);
        Page<BrandEntity> page = new PageImpl<>(brandEntities, PageRequest.of(pageNumber, pageSize, Sort.by(Sort.Order.desc(sortBy))), brandEntities.size());

        when(brandRepository.findAll(any(Pageable.class))).thenReturn(page);
        when(brandEntityMapper.toDomain(brandEntity1)).thenReturn(new Brand(1L, "Brand A", "Description A"));
        when(brandEntityMapper.toDomain(brandEntity2)).thenReturn(new Brand(2L, "Brand B", "Description B"));

        // When
        Pagination<Brand> result = brandJpaAdapter.listBrands(pageNumber, pageSize, sortBy, sortDirection);

        // Then
        assertThat(result.getContent()).hasSize(2);
        assertThat(result.getPageSize()).isEqualTo(5);
        assertThat(result.getContent().get(0).getName()).isEqualTo("Brand B");
        assertThat(result.getContent().get(1).getName()).isEqualTo("Brand A");
        verify(brandRepository, times(1)).findAll(any(Pageable.class));
    }

    @Test
    @DisplayName("Should return correct pagination size")
    void listBrand_ShouldReturnCorrectPaginationSize() {
        //Given
        int pageNumber = 0;
        int pageSize = 10;
        String sortBy = "name";
        String sortDirection = "asc";

        BrandEntity brandEntity1 = new BrandEntity(1L, "Brand 1", "Description 1");
        BrandEntity brandEntity2 = new BrandEntity(2L, "Brand 2", "Description 2");

        List<BrandEntity> brandEntities = List.of(brandEntity1, brandEntity2);
        Page<BrandEntity> page = new PageImpl<>(brandEntities, PageRequest.of(pageNumber, pageSize), 2);

        when(brandRepository.findAll(any(Pageable.class))).thenReturn(page);
        when(brandEntityMapper.toDomain(brandEntity1)).thenReturn(new Brand(1L, "Brand 1", "Description 1"));
        when(brandEntityMapper.toDomain(brandEntity2)).thenReturn(new Brand(2L, "Brand 2", "Description 2"));

        //When
        Pagination<Brand> result = brandJpaAdapter.listBrands(pageNumber, pageSize, sortBy, sortDirection);

        //Then
        assertThat(result.getContent()).hasSize(2);
        assertThat(result.getPageSize()).isEqualTo(10);
        assertThat(result.getTotalElements()).isEqualTo(2);
        verify(brandRepository, times(1)).findAll(any(Pageable.class));
    }

    @Test
    @DisplayName("Should map BrandEntity to Brand correctly in pagination")
    void listBrand_ShouldMapBrandEntityToBrandCorrectly() {
        // Given
        int pageNumber = 0;
        int pageSize = 5;
        String sortBy = "name";
        String sortDirection = "asc";

        BrandEntity brandEntity1 = new BrandEntity(1L, "Brand 1", "Description 1");
        BrandEntity brandEntity2 = new BrandEntity(2L, "Brand 2", "Description 2");

        List<BrandEntity> brandEntities = List.of(brandEntity1, brandEntity2);
        Page<BrandEntity> page = new PageImpl<>(brandEntities);

        when(brandRepository.findAll(any(Pageable.class))).thenReturn(page);
        when(brandEntityMapper.toDomain(brandEntity1)).thenReturn(new Brand(1L, "Brand 1", "Description 1"));
        when(brandEntityMapper.toDomain(brandEntity2)).thenReturn(new Brand(2L, "Brand 2", "Description 2"));

        //When
        Pagination<Brand> result = brandJpaAdapter.listBrands(pageNumber, pageSize, sortBy, sortDirection);

        //Then
        assertThat(result.getContent()).hasSize(2);
        assertThat(result.getContent().get(0).getName()).isEqualTo("Brand 1");
        assertThat(result.getContent().get(1).getName()).isEqualTo("Brand 2");
        verify(brandEntityMapper, times(1)).toDomain(brandEntity1);
        verify(brandEntityMapper, times(1)).toDomain(brandEntity2);
    }

}