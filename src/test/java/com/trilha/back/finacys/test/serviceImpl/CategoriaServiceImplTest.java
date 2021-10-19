package com.trilha.back.finacys.test.serviceImpl;

import com.trilha.back.finacys.bo.ValidacaoBo;
import com.trilha.back.finacys.entity.Categoria;
import com.trilha.back.finacys.exception.ValidateException;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.http.HttpStatus;

import static org.junit.jupiter.api.Assertions.*;

class CategoriaServiceImplTest {

    @Mock
    ValidacaoBo bo;

    @Test
    void buscarCategoria() {
        Categoria categoria = new Categoria();
        Mockito.doReturn(categoria);
    }

    void buscarCaterogia_Exception(){
        Mockito.doThrow(new ValidateException("categoria não encontrada: " + 1L, HttpStatus.NOT_FOUND));
    }

    @Test
    void buscarTodasCategorias() {
    }

    @Test
    void alterarCategoria() {
    }

    @Test
    void inserirCategoria() {
    }

    @Test
    void deletarCategoriaPorId() {
    }
}