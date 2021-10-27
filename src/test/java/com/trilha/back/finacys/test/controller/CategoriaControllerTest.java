package com.trilha.back.finacys.test.controller;

import com.trilha.back.finacys.controller.CategoriaController;
import com.trilha.back.finacys.response.CategoriaResponse;
import com.trilha.back.finacys.serviceImpl.CategoriaServiceImpl;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;

import static io.restassured.module.mockmvc.RestAssuredMockMvc.given;
import static io.restassured.module.mockmvc.RestAssuredMockMvc.standaloneSetup;


@SpringBootTest
public class CategoriaControllerTest {

    @Autowired
    private CategoriaController controller;

    @MockBean
    private CategoriaServiceImpl service;

    @BeforeEach
    public void setUp(){
        standaloneSetup(this.controller);

    }

    @Test
    public void buscarCategoriaPorId(){
        CategoriaServiceImpl service = Mockito.mock(CategoriaServiceImpl.class);

        Mockito.when(service.buscarCategoria(1L)).thenReturn(new CategoriaResponse(1L, "test", "test_description"));

        given().accept(ContentType.JSON)
                .when().get("/categoria/{id}", 1L)
                .then().statusCode(HttpStatus.OK.value());

    }





}