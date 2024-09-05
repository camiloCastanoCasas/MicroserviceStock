package com.microservice.stock.infraestructure.input.rest;

import com.microservice.stock.application.dto.request.ArticleRequest;
import com.microservice.stock.application.dto.request.BrandRequest;
import com.microservice.stock.application.handler.IArticleHandler;
import com.microservice.stock.application.handler.IBrandHandler;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/article")
@RequiredArgsConstructor
public class ArticleRestController {

    private final IArticleHandler articleHandler;

    @PostMapping("/create")
    public ResponseEntity<Void> createArticle(@Valid @RequestBody ArticleRequest articleRequest) {
        articleHandler.createArticle(articleRequest);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}
