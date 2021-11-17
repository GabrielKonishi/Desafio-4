package com.trilha.back.finacys.test.serviceImpl;

import com.trilha.back.finacys.bo.ValidacaoBo;
import com.trilha.back.finacys.entity.Categoria;
import com.trilha.back.finacys.entity.Lancamento;
import com.trilha.back.finacys.repository.CategoriaRepository;
import com.trilha.back.finacys.repository.LancamentoRepository;
import com.trilha.back.finacys.request.CategoriaRequest;
import com.trilha.back.finacys.request.LancamentoRequest;
import com.trilha.back.finacys.response.LancamentoResponse;
import com.trilha.back.finacys.serviceImpl.LancamentoServiceImpl;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
public class LancamentoServiceImplTest {

    @Autowired
    public LancamentoServiceImpl service;

    @MockBean
    public Categoria categoria;

    @MockBean
    public LancamentoRepository repository;

    @MockBean
    public CategoriaRepository categoriaRepository;

    @MockBean
    public ValidacaoBo bo;

    @Test
    public void buscarLancamento() {
        bo.validarObrigatoriedade(1L, "id_lancamento");
        LocalDate date = LocalDate.parse("2012-05-01");
        Categoria categoria = new Categoria(2L, "test", "test_description");
        Lancamento lancamento = new Lancamento(1L, "test", "test_description", "1", "555", date, true, categoria);
        Mockito.when(repository.findById(lancamento.getId())).thenReturn(java.util.Optional.of(lancamento));

        Assert.assertTrue(lancamento.equals(lancamento));

        LancamentoResponse lancamentoResponse = service.buscarLancamentoPorId(lancamento.getId());

        Assert.assertEquals("test", lancamentoResponse.getName());
        Assert.assertEquals("test_description", lancamentoResponse.getDescription());
        Assert.assertEquals("1", lancamentoResponse.getType());
        Assert.assertEquals("555", lancamentoResponse.getAmount());
        Assert.assertEquals(date, lancamentoResponse.getDate());
        Assert.assertEquals(true, lancamentoResponse.isPaid());
    }

    @Test
    public void inserirLancamento() {
        LocalDate date = LocalDate.parse("2012-05-01");
        Categoria categoria = new Categoria(2L, "test", "test_description");
        CategoriaRequest categoriaRequest = new CategoriaRequest(categoria);
        Lancamento lancamento = new Lancamento(1L, "test", "test_description", "1", "555", date, true, categoria);
        LancamentoRequest lancamentoRequest = new LancamentoRequest(
                "test",
                "test_description",
                "1",
                "555",
                date,
                true,
                categoria
        );

        List<Categoria> listaCategoria = new ArrayList<>();
        listaCategoria.add(categoria);

        Mockito.when(categoriaRepository.findAll()).thenReturn(listaCategoria);
        Mockito.when(categoriaRepository.findById(2L)).thenReturn(java.util.Optional.of(categoria));
        LancamentoResponse lancamentoResponse = service.inserirLancamento(lancamentoRequest);

        Assert.assertEquals("test", lancamentoResponse.getName());
        Assert.assertEquals("test_description", lancamentoResponse.getDescription());
        Assert.assertEquals("1", lancamentoResponse.getType());
        Assert.assertEquals("555", lancamentoResponse.getAmount());
        Assert.assertEquals(date, lancamentoResponse.getDate());
        Assert.assertEquals(true, lancamentoResponse.isPaid());
    }

    @Test
    public void alterarLancamento() {
        LocalDate date = LocalDate.parse("2012-05-01");
        Categoria categoria = new Categoria(2L, "test", "test_description");
        CategoriaRequest categoriaRequest = new CategoriaRequest(categoria);
        Lancamento lancamento = new Lancamento(1L, "test", "test_description", "1", "555", date, true, categoria);
        bo.validarObrigatoriedade(lancamento.getId(), "lancamento_id");

        Mockito.when(repository.findById(lancamento.getId())).thenReturn(java.util.Optional.of(lancamento));
        Assert.assertTrue(lancamento.equals(lancamento));
        LocalDate updatedDate = LocalDate.parse("2012-06-11");
        LancamentoRequest lancamentoRequest = new LancamentoRequest("update_test",
                "update_test_descriptio",
                "1",
                "556",
                updatedDate,
                true,
                categoria);

        LancamentoResponse lancamentoResponse = service.alterarLancamento(lancamento.getId(), lancamentoRequest);
        Assert.assertEquals("update_test", lancamentoResponse.getName());
        Assert.assertEquals("update_test_descriptio", lancamentoResponse.getDescription());
        Assert.assertEquals("1", lancamentoResponse.getType());
        Assert.assertEquals("556", lancamentoResponse.getAmount());
        Assert.assertEquals(updatedDate, lancamentoResponse.getDate());
        Assert.assertEquals(true, lancamentoResponse.isPaid());
        Assert.assertEquals(categoria.getId(), lancamentoResponse.getCategoria());
    }

    @TestConfiguration
    static class LancamentoServiceImplConfig {

        @Bean
        public LancamentoServiceImpl service() {
            return new LancamentoServiceImpl();
        }
    }


}