package com.microservice.stock.infraestructure.input.rest;

import com.microservice.stock.application.dto.request.ArticleRequest;
import com.microservice.stock.application.handler.IArticleHandler;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
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

    @Operation(summary = "Create article",
            tags = {"Article"},
            description = "This operation allows the creation of a new article in the system. "
                    + "The article must have a unique name, and the name, description, quantity, "
                    + "price, brandId and List of categories fields must comply with validation rules such as not being empty."
                    + "If the article is successfully created, "
                    + "a status code of 201 is returned."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Article created successfully",
                    content = @Content),
            @ApiResponse(responseCode = "400", description = "Article not created",
                    content = @Content),
    })
    @PostMapping("/create")
    public ResponseEntity<Void> createArticle(@Valid @RequestBody ArticleRequest articleRequest) {
        articleHandler.createArticle(articleRequest);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}
