package com.microservice.stock.infraestructure.input.rest;

import com.microservice.stock.application.handler.BrandHandler;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
class BrandRestControllerTest {

    @Mock
    private BrandHandler brandHandler;

    @InjectMocks
    private BrandRestController brandRestController;

    private MockMvc mockMvc;

    @BeforeEach
    void setUp(){
        mockMvc = MockMvcBuilders.standaloneSetup(brandRestController).build();
    }

    @Test
    @DisplayName("Should correctly insert a brand and return HttpStatus.CREATED.")
    void createBrand_ShouldReturnCreatedStatus() throws Exception {
        String body = "{\"name\":\"BrandName\",\"description\":\"BrandDescripcion\"}";
        MockHttpServletRequestBuilder request = post("/brand/create")
                .contentType(MediaType.APPLICATION_JSON)
                .content(body);
        mockMvc.perform(request).andExpect(status().isCreated());
    }

    @Test
    @DisplayName("Should handle validation for empty name field.")
    void createBrand_ShouldReturnBadRequestWhenNameIsEmpty() throws Exception {
        String body = "{\"name\":\"\",\"description\":\"BrandDescription\"}";
        MockHttpServletRequestBuilder request = post("/brand/create")
                .contentType(MediaType.APPLICATION_JSON)
                .content(body);
        mockMvc.perform(request).andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("Should handle validation for name field being greater than 50 characters")
    void createBrand_ShouldReturnBadRequestWhenNameTooLong() throws Exception {
        String body = "{\"name\":\"BrandNameThisIsAReallyLongCategoryNameThatExceedsFiftyCharacters\",\"BrandDescription\":\"\"}";
        MockHttpServletRequestBuilder request = post("/brand/create")
                .contentType(MediaType.APPLICATION_JSON)
                .content(body);
        mockMvc.perform(request).andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("Should handle validation for description field being empty")
    void createBrand_ShouldReturnBadRequestWhenDescriptionIsEmpty() throws Exception {
        String body = "{\"name\":\"BrandName\",\"description\":\"\"}";
        MockHttpServletRequestBuilder request = post("/brand/create")
                .contentType(MediaType.APPLICATION_JSON)
                .content(body);
        mockMvc.perform(request).andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("Should handle validation for description field being greater than 120 characters")
    void createBrand_ShouldReturnBadRequestWhenDescriptionTooLong() throws Exception {
        String body = "{\"name\":\"CategoryName\",\"description\":\"ThisDescriptionIsWayTooLongAndDefinitelyExceedsTheMaximumAllowedLengthOfNinetyCharactersForTestingPurposesInYourApplication!!!!!!!!!!!!!!\"}";
        MockHttpServletRequestBuilder request = post("/brand/create")
                .contentType(MediaType.APPLICATION_JSON)
                .content(body);
        mockMvc.perform(request).andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("Should handle validation for name and description fields being empty")
    void createBrand_ShouldReturnBadRequestWhenDescriptionAndNameAreEmpty() throws Exception {
        String body = "{\"name\":\"\",\"description\":\"\"}";
        MockHttpServletRequestBuilder request = post("/brand/create")
                .contentType(MediaType.APPLICATION_JSON)
                .content(body);
        mockMvc.perform(request).andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("Should handle validation for `name` field being greater than 50 characters and `description` field being greater than 120 characters")
    void createBrand_ShouldReturnBadRequestWhenDescriptionAndNameTooLong() throws Exception {
        String body = "{\"name\":\"CategoryNameThisIsAReallyLongCategoryNameThatExceedsFiftyCharacters\",\"description\":\"ThisDescriptionIsWayTooLongAndDefinitelyExceedsTheMaximumAllowedLengthOfNinetyCharactersForTestingPurposesInYourApplication!!!!!!!!!!!!!!!!!!!!\"}";
        MockHttpServletRequestBuilder request = post("/brand/create")
                .contentType(MediaType.APPLICATION_JSON)
                .content(body);
        mockMvc.perform(request).andExpect(status().isBadRequest());
    }
}