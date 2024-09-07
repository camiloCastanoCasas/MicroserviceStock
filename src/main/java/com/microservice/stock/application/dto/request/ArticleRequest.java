package com.microservice.stock.application.dto.request;

import com.microservice.stock.domain.util.DomainConstants;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.hibernate.validator.constraints.UniqueElements;

import java.math.BigDecimal;
import java.util.List;

@AllArgsConstructor
@Getter
public class ArticleRequest {

    @NotBlank(message = DomainConstants.FIELD_NAME_NULL_MESSAGE)
    private String name;

    @NotBlank(message = DomainConstants.FIELD_NAME_NULL_MESSAGE)
    private String description;

    @NotNull(message = DomainConstants.FIELD_PRICE_NOT_NULL_MESSAGE)
    @Positive(message = DomainConstants.FIELD_PRICE_NOT_POSITIVE_MESSAGE)
    private BigDecimal price;

    @NotNull(message = DomainConstants.FIELD_QUANTITY_NOT_NULL_MESSAGE)
    @PositiveOrZero(message = DomainConstants.FIELD_QUANTITY_NOT_POSITIVE_OR_ZERO_MESSAGE)
    private Integer quantity;

    @Size(min = DomainConstants.FIELD_CATEGORIES_MIN,
          max = DomainConstants.FIELD_CATEGORIES_MAX,
          message = DomainConstants.FIELD_CATEGORIES_OUT_OF_RANGE_MESSAGE
    )
    @UniqueElements(message = DomainConstants.FIELD_CATEGORIES_NOT_UNIQUE_MESSAGE)
    private List<Long> categoryIds;

    @NotNull(message = DomainConstants.FIELD_BRAND_ID_NOT_NULL_MESSAGE)
    @Positive(message = DomainConstants.FIELD_BRAND_ID_NOT_POSITIVE_MESSAGE)
    private Long brandId;
}
