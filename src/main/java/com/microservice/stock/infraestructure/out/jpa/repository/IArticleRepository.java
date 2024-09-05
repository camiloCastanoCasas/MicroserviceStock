package com.microservice.stock.infraestructure.out.jpa.repository;

import com.microservice.stock.infraestructure.out.jpa.entity.ArticleEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IArticleRepository extends JpaRepository<ArticleEntity, Long> {
}
