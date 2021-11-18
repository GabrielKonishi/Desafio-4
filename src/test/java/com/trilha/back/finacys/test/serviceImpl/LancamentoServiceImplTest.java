package com.trilha.back.finacys.test.serviceImpl;

import com.trilha.back.finacys.bo.ValidacaoBo;
import com.trilha.back.finacys.entity.Categoria;
import com.trilha.back.finacys.entity.Lancamento;
import com.trilha.back.finacys.exception.ValidateException;
import com.trilha.back.finacys.repository.CategoriaRepository;
import com.trilha.back.finacys.repository.LancamentoRepository;
import com.trilha.back.finacys.request.LancamentoRequest;
import com.trilha.back.finacys.response.LancamentoResponse;
import com.trilha.back.finacys.service.impl.LancamentoServiceImpl;
import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
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

    @Rule
    public ExpectedException ex = ExpectedException.none();

    @Test
    @DisplayName("deve buscar um lancamento com sucesso")
    public void DeveBuscarUmLancamentoComSucesso_buscarLancamento() {
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
    @DisplayName("deve cadastrar um lançamento com sucesso")
    public void DeveInserirUmLancamentoComSucesso_inserirLancamento() {
        LocalDate localDate = LocalDate.MIN;
        Categoria categoria = new Categoria(1L, "test_categoria", "test_categoria_description");
        LancamentoRequest lancamentoRequest =
                new LancamentoRequest("test", "test_description", "1", "555", localDate, false, categoria);
        LancamentoRequest request = new LancamentoRequest();
        Lancamento lancamento = new Lancamento(1L,
                lancamentoRequest.getName(),
                lancamentoRequest.getDescription(), lancamentoRequest.getType(),
                lancamentoRequest.getAmount(), lancamentoRequest.getDate(), lancamentoRequest.isPaid(),
                lancamentoRequest.getCategoria());
        Mockito.when(repository.save(lancamento)).thenReturn(lancamento);
        LancamentoResponse lancamentoResponse = service.inserirLancamento(lancamentoRequest);

        Assert.assertEquals(lancamentoRequest.getName(), lancamentoResponse.getName());
        Assert.assertEquals(lancamentoRequest.getDescription(), lancamentoResponse.getDescription());
        Assert.assertEquals(lancamentoRequest.getDate(), lancamentoResponse.getDate());

    }

    @Test
    @DisplayName("deve alterar um lancamento")
    public void DeveAlterarUmLancamentoComSucesso_alterarLancamento() {
        LocalDate date = LocalDate.MIN;
        Categoria categoria = new Categoria(2L, "test", "test_description");
        Lancamento lancamento = new Lancamento(1L, "test", "test_description", "1", "555", date, true, categoria);
        Mockito.when(repository.findById(lancamento.getId())).thenReturn(java.util.Optional.of(lancamento));
        Mockito.when(categoriaRepository.findById(categoria.getId())).thenReturn(java.util.Optional.of(categoria));
        LocalDate updatedDate = LocalDate.parse("2012-06-11");
        LancamentoRequest lancamentoRequest = new LancamentoRequest(
                "test_update", "test_description_update", "1",
                "555", updatedDate, true, categoria);


        LancamentoResponse lancamentoResponse = service.alterarLancamento(1L, lancamentoRequest);
        Assert.assertEquals("test_update", lancamentoResponse.getName());
        Assert.assertEquals("test_description_update", lancamentoResponse.getDescription());
        Assert.assertEquals(lancamentoRequest.getDate(), updatedDate);
    }

    @Test
    @DisplayName("deve buscar com sucesso por dependentes")
    public void DeveBuscarOsLancamentosComSucesso_buscarTodosLancamentosPorDependentes(){
        LocalDate date = LocalDate.MIN;
        Categoria categoria = new Categoria(2L, "test", "test_description");
        Lancamento lancamento = new Lancamento(
                1L,"test",
                "test_description", "0",
                "555", date, false,  categoria
        );

        List<Lancamento> listaLancamento = new ArrayList<>();
        listaLancamento.add(lancamento);

        Mockito.when(repository.findAll()).thenReturn(listaLancamento);
        service.buscarLancamentoPorDependentes(date.toString(), "555", Optional.of(false));
        Mockito.verify(repository, Mockito.times(1)).findAll();
    }

    @Test
    @DisplayName("deve retornar uma exception devido a falta de dados da data")
    public void DeveRetornarUmaExceptionDataVazia_buscarTodosLancamentosPorDependentes(){

        ex.expect(ValidateException.class);
        ex.expectMessage("preencha o campo data");

        service.buscarLancamentoPorDependentes(null, "555", Optional.of(false));
    }

    @Test
    @DisplayName("deve retornar uma exception devido a falta de dados de amount")
    public void DeveRetornarUmaExceptionAmountVazia_buscarTodosLancamentosPorDependentes(){
        LocalDate date = LocalDate.MIN;
        ex.expect(ValidateException.class);
        ex.expectMessage("preencha o campo amount");

        service.buscarLancamentoPorDependentes(date.toString(), null, Optional.of(false));
    }

    @Test
    @DisplayName("deve retornar uma exception devido a falta de dados da paid")
    public void DeveRetornarUmaExceptionPaidVazia_buscarTodosLancamentosPorDependentes(){
        LocalDate date = LocalDate.MIN;
        ex.expect(ValidateException.class);
        ex.expectMessage("preencha o campo de pagamento");

        service.buscarLancamentoPorDependentes(date.toString(), "555", Optional.ofNullable(null));
    }

    @Test
    @DisplayName("deve retornar uma exception não há lancamentos cadastrados")
    public void DeveRetornarUmaExceptionNaoHaLancamentosCadastrados_buscarTodosLancamentosPorDependentes(){
        LocalDate date = LocalDate.MIN;
        List<Lancamento> listaLancamento = new ArrayList<>();
        Mockito.when(repository.findAll()).thenReturn(listaLancamento);
        ex.expect(ValidateException.class);
        ex.expectMessage("Não há lancamentos cadastrados com os valores fornecidos");
        service.buscarLancamentoPorDependentes(date.toString(), "555", Optional.of(false));
        Mockito.verify(repository, Mockito.times(1)).findAll();
    }

}