package com.microservice.stock.infraestructure.input.rest;

import com.microservice.stock.application.dto.request.CategoryRequest;
import com.microservice.stock.application.dto.response.CategoryResponse;
import com.microservice.stock.application.handler.ICategoryHandler;
import com.microservice.stock.domain.util.Pagination;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/category")
@RequiredArgsConstructor
public class CategoryRestController {

    private final ICategoryHandler categoryHandler;

    @Operation(summary = "Create category")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Category created successfully",
                    content = @Content),
            @ApiResponse(responseCode = "400", description = "Category not created",
                    content = @Content),
    })
    @PostMapping("/create")
    public ResponseEntity<Void> createCategory(@Valid @RequestBody CategoryRequest categoryRequest) {
        categoryHandler.createCategory(categoryRequest);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping
    public ResponseEntity<Pagination<CategoryResponse>> listCategories(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "name") String sortby,
            @RequestParam(defaultValue = "asc") String sortDirection
    ) {
        Pagination<CategoryResponse> response = categoryHandler.listCategories(page, size, sortby, sortDirection);
        return ResponseEntity.ok(response);
    }
}
