package com.microservice.stock.infraestructure.out.jpa.repository;

import com.microservice.stock.infraestructure.out.jpa.entity.BrandEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface IBrandRepository extends JpaRepository<BrandEntity, Long> {
    Optional<BrandEntity> findByName(String name);
    boolean existsById(Long id);
}
