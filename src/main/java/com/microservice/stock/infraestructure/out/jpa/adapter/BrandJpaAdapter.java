package com.microservice.stock.infraestructure.out.jpa.adapter;

import com.microservice.stock.domain.model.Brand;
import com.microservice.stock.domain.spi.IBrandPersistencePort;
//import com.microservice.stock.infraestructure.exceptions.BrandAlreadyExistsException;
import com.microservice.stock.infraestructure.out.jpa.mapper.BrandEntityMapper;
import com.microservice.stock.infraestructure.out.jpa.repository.IBrandRepository;
//import com.microservice.stock.infraestructure.util.InfraestructureConstants;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class BrandJpaAdapter implements IBrandPersistencePort {

    private final IBrandRepository brandRepository;
    private final BrandEntityMapper brandEntityMapper;

    @Override
    public void createBrand(Brand brand) {
//        if(brandRepository.findByName(brand.getName()).isPresent()) {
//            throw new BrandAlreadyExistsException(InfraestructureConstants.BRAND_EXISTS_MESSAGE);
//        }
        brandRepository.save(brandEntityMapper.toEntity(brand));
    }

    @Override
    public boolean existsByName(String name) {
        return false;
    }

}
