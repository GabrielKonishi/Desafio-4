package com.trilha.back.finacys.test.request;

import com.trilha.back.finacys.request.CategoriaRequest;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.jupiter.api.Assertions.assertEquals;

@RunWith(SpringRunner.class)
public class CategoriaRequestTest {

    @Autowired
    private CategoriaRequest request;

    @Test
    public void categoriaGetterAndSetter(){
        CategoriaRequest categoriaRequest = new CategoriaRequest();
        categoriaRequest.setName("test");
        categoriaRequest.setDescription("test_description");

        assertEquals("test", categoriaRequest.getName());
    }



}