package com.microservice.stock.infraestructure.input.rest;

import com.microservice.stock.application.dto.request.CategoryRequest;
import com.microservice.stock.application.dto.response.CategoryResponse;
import com.microservice.stock.application.dto.response.PaginationResponse;
import com.microservice.stock.application.handler.ICategoryHandler;
import io.swagger.v3.oas.annotations.Parameter;
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

    @Operation(summary = "Create category",
               tags = { "Category"},
               description = "This operation allows the creation of a new category in the system. "
                    + "The category must have a unique name, and the name and description "
                    + "fields must comply with validation rules such as not being empty and "
                    + "not exceeding the maximum length. If the category is successfully created, "
                    + "a status code of 201 is returned."
    )
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

    @Operation(summary = "Category Pagination",
              tags = { "Category", "Pagination" },
              description = "This operation retrieves a paginated list of available categories in the system. Clients can specify the desired page number and page size, as well as sort the categories in ascending or descending order by their name.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful retrieval",
                    content = @Content),
            @ApiResponse(responseCode = "400", description = "Invalid parameters",
                    content = @Content),
    })
    @GetMapping
    public ResponseEntity<PaginationResponse<CategoryResponse>> listCategories(
            @Parameter(description = "Page number to retrieve (starting from 0)")
            @RequestParam(required = false) Integer page,
            @Parameter(description = "Number of elements per page")
            @RequestParam(required = false) Integer size,
            @Parameter(description = "Sorting criteria 'name'")
            @RequestParam(required = false) String sortBy,
            @Parameter(description = "Sorting criteria, 'asc' or 'desc'")
            @RequestParam(defaultValue = "asc") String sortDirection
    ) {
        PaginationResponse<CategoryResponse> response = categoryHandler.listCategories(page, size, sortBy, sortDirection);
        return ResponseEntity.ok(response);
    }
}
