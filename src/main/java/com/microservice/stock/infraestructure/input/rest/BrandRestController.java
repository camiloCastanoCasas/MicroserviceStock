package com.microservice.stock.infraestructure.input.rest;

import com.microservice.stock.application.dto.request.BrandRequest;
import com.microservice.stock.application.dto.response.PaginationCategoryResponse;
import com.microservice.stock.application.handler.IBrandHandler;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/brand")
@RequiredArgsConstructor
public class BrandRestController {

    private final IBrandHandler brandHandler;

    @Operation(summary = "Create brand")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Brand created successfully",
                    content = @Content),
            @ApiResponse(responseCode = "400", description = "Brand not created",
                    content = @Content),
    })
    @PostMapping("/create")
    public ResponseEntity<Void> createBrand(@Valid @RequestBody BrandRequest brandRequest) {
        brandHandler.createBrand(brandRequest);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }


}
