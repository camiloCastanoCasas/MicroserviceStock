package com.microservice.stock.application.dto.request;

import com.microservice.stock.domain.util.DomainConstants;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class BrandRequest {
    @NotBlank(message = DomainConstants.FIELD_NAME_NULL_MESSAGE)
    @Size(max = DomainConstants.FIELD_NAME_BRAND_SIZE_MAX, message = DomainConstants.FIELD_NAME_BRAND_SIZE_MESSAGE)
    private final String name;

    @NotBlank(message = DomainConstants.FIELD_DESCRIPTION_NULL_MESSAGE)
    @Size(max = DomainConstants.FIELD_DESCRIPTION_BRAND_SIZE_MAX, message = DomainConstants.FIELD_DESCRIPTION_BRAND_SIZE_MESSAGE)
    private final String description;
}
