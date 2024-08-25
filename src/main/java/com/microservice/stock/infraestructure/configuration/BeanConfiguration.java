package com.microservice.stock.infraestructure.configuration;

import com.microservice.stock.domain.api.ICategoryServicePort;
import com.microservice.stock.domain.spi.ICategoryPersistencePort;
import com.microservice.stock.domain.usecase.CategoryUseCase;
import com.microservice.stock.infraestructure.out.jpa.adapter.CategoryJpaAdapter;
import com.microservice.stock.infraestructure.out.jpa.mapper.CategoryEntityMapper;
import com.microservice.stock.infraestructure.out.jpa.repository.ICategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class BeanConfiguration {

    private final ICategoryRepository categoryRepository;
    private final CategoryEntityMapper categoryEntityMapper;

    @Bean
    public ICategoryPersistencePort categoryPersistencePort() {
        return new CategoryJpaAdapter(categoryRepository, categoryEntityMapper);
    }

    @Bean
    public ICategoryServicePort categoryServicePort() {
        return new CategoryUseCase(categoryPersistencePort());
    }


}
