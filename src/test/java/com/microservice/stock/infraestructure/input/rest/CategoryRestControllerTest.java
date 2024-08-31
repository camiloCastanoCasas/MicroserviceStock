package com.microservice.stock.infraestructure.input.rest;

import com.microservice.stock.application.dto.request.CategoryRequest;
import com.microservice.stock.application.handler.CategoryHandler;
import com.microservice.stock.domain.exceptions.ValidationException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MockMvcBuilder;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
class CategoryRestControllerTest {

    @Mock
    private CategoryHandler categoryHandler;

    @InjectMocks
    private CategoryRestController categoryRestController;

    private MockMvc mockMvc;

    @BeforeEach
    void setUp(){
        mockMvc = MockMvcBuilders.standaloneSetup(categoryRestController).build();
    }

    @Test
    @DisplayName("Debería insertar correctamente una categoría y retornar HttpStatus.CREATED")
    public void createCategory_ShouldReturnCreatedStatus() throws Exception {
        String body = "{\"name\":\"SpringFramework\",\"description\":\"Descripcion\"}";
        MockHttpServletRequestBuilder request = post("/category/create")
                .contentType(MediaType.APPLICATION_JSON)
                .content(body);
        mockMvc.perform(request).andExpect(status().isCreated());
    }

    @Test
    @DisplayName("Deberia manejar validacion de campo name vacio")
    public void createCategory_ShouldReturnBadRequestWhenNameIsEmpty() throws Exception {
        String body = "{\"name\":\"\",\"description\":\"Descripcion\"}";
        MockHttpServletRequestBuilder request = post("/category/create")
                .contentType(MediaType.APPLICATION_JSON)
                .content(body);
        mockMvc.perform(request).andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("Deberia manejar validacion de campo name es mayor a 50 caracteres")
    public void createCategory_ShouldReturnBadRequestWhenNameTooLong() throws Exception {
        String body = "{\"name\":\"CategoryNameThisIsAReallyLongCategoryNameThatExceedsFiftyCharacters\",\"description\":\"\"}";
        MockHttpServletRequestBuilder request = post("/category/create")
                .contentType(MediaType.APPLICATION_JSON)
                .content(body);
        mockMvc.perform(request).andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("Deberia manejar validacion de campo Description es vacio")
    public void createCategory_ShouldReturnBadRequestWhenDescriptionIsEmpty() throws Exception {
        String body = "{\"name\":\"CategoryName\",\"description\":\"\"}";
        MockHttpServletRequestBuilder request = post("/category/create")
                .contentType(MediaType.APPLICATION_JSON)
                .content(body);
        mockMvc.perform(request).andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("Deberia manejar validacion de campo Description es mayor a 90 caracteres")
    public void createCategory_ShouldReturnBadRequestWhenDescriptionTooLong() throws Exception {
        String body = "{\"name\":\"CategoryName\",\"description\":\"ThisDescriptionIsWayTooLongAndDefinitelyExceedsTheMaximumAllowedLengthOfNinetyCharactersForTestingPurposesInYourApplication\"}";
        MockHttpServletRequestBuilder request = post("/category/create")
                .contentType(MediaType.APPLICATION_JSON)
                .content(body);
        mockMvc.perform(request).andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("Deberia manejar validacion de campo name y description son vacios")
    public void createCategory_ShouldReturnBadRequestWhenDescriptionAndNameAreEmpty() throws Exception {
        String body = "{\"name\":\"\",\"description\":\"\"}";
        MockHttpServletRequestBuilder request = post("/category/create")
                .contentType(MediaType.APPLICATION_JSON)
                .content(body);
        mockMvc.perform(request).andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("Deberia manejar validacion de campo name es mayor a 50 caracteres y Description es mayor a 90 caracteres")
    public void createCategory_ShouldReturnBadRequestWhenDescriptionAndNameTooLong() throws Exception {
        String body = "{\"name\":\"CategoryNameThisIsAReallyLongCategoryNameThatExceedsFiftyCharacters\",\"description\":\"ThisDescriptionIsWayTooLongAndDefinitelyExceedsTheMaximumAllowedLengthOfNinetyCharactersForTestingPurposesInYourApplication\"}";
        MockHttpServletRequestBuilder request = post("/category/create")
                .contentType(MediaType.APPLICATION_JSON)
                .content(body);
        mockMvc.perform(request).andExpect(status().isBadRequest());
    }
}
