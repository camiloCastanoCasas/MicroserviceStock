package com.microservice.stock.infraestructure.out.jpa.adapter;

import com.microservice.stock.domain.model.Category;
import com.microservice.stock.domain.spi.ICategoryPersistencePort;
import com.microservice.stock.infraestructure.exceptions.CategoryAlreadyExistsException;
import com.microservice.stock.infraestructure.out.jpa.mapper.CategoryEntityMapper;
import com.microservice.stock.infraestructure.out.jpa.repository.ICategoryRepository;
import com.microservice.stock.infraestructure.util.InfraestructureConstants;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class CategoryJpaAdapter implements ICategoryPersistencePort {

    private final ICategoryRepository categoryRepository;
    private final CategoryEntityMapper categoryEntityMapper;

    @Override
    public void createCategory(Category category) {
        if(categoryRepository.findByName(category.getName()).isPresent()) {
            throw new CategoryAlreadyExistsException(InfraestructureConstants.CATEGORY_EXISTS_MESSAGE);
        }
        categoryRepository.save(categoryEntityMapper.toEntity(category));
    }
}
