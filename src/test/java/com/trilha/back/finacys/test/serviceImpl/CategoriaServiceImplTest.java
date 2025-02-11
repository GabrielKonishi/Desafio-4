package com.trilha.back.finacys.test.serviceImpl;

import com.trilha.back.finacys.bo.ValidacaoBo;
import com.trilha.back.finacys.entity.Categoria;
import com.trilha.back.finacys.exception.ValidateException;
import com.trilha.back.finacys.repository.CategoriaRepository;
import com.trilha.back.finacys.request.CategoriaRequest;
import com.trilha.back.finacys.response.CategoriaResponse;
import com.trilha.back.finacys.service.impl.CategoriaServiceImpl;
import org.junit.Assert;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.runner.RunWith;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.ArrayList;
import java.util.List;


@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class CategoriaServiceImplTest {


    @Autowired
    private CategoriaServiceImpl service;

    @MockBean
    private CategoriaRepository repository;

    @MockBean
    ValidacaoBo bo;


    @Test
    @DisplayName("deve buscar uma categoria por id")
    public void deveRetornarUmaCategoriaComSucesso_buscarCategoria() {
        Categoria categoria = new Categoria(1L, "test_name", "test_description");
        Mockito.when(repository.findById(ArgumentMatchers.eq(categoria.getId()))).thenReturn(java.util.Optional.of(categoria));
        CategoriaResponse categoriaResponse = service.buscarCategoria(1L);

        Assert.assertEquals("test_name", categoriaResponse.getName());
        Assert.assertEquals("test_description", categoriaResponse.getDescription());
        Mockito.verify(repository, Mockito.times(1)).findById(categoria.getId());
    }

    @Test
    @DisplayName("deve buscar uma lista de categoria")
    public void deveRetornarUmaListaCategoriaComSucesso_buscarTodasCategorias(){
        List<Categoria> listCategoria = new ArrayList<>();
        Categoria indexCategoria1 = new Categoria(1L, "test_index_1", "test_description_index_1");
        Categoria indexCategoria2 = new Categoria(2L, "test_index_2", "test_description_index_2");
        listCategoria.add(indexCategoria1);
        listCategoria.add(indexCategoria2);
        Mockito.when(repository.findAll()).thenReturn(listCategoria);
        List<CategoriaResponse> categoriaResponses = service.buscarTodasCategorias();
        Assert.assertEquals("test_index_1", categoriaResponses.get(0).getName());
        Assert.assertEquals("test_description_index_1", categoriaResponses.get(0).getDescription());
        Assert.assertEquals("test_index_2", categoriaResponses.get(1).getName());
        Assert.assertEquals("test_description_index_2", categoriaResponses.get(1).getDescription());
        Mockito.verify(repository, Mockito.times(1)).findAll();
    }

    @Test
    @DisplayName("deve retornar uma exception na busca de um id inexistente")
    public void deveRetornarUmaException_buscarCategoria() throws ValidateException{

        Categoria categoria = new Categoria(1L, "test_name", "test_description");


        Mockito.doThrow(new ValidateException("Categoria não encontrada: ", HttpStatus.NOT_FOUND))
                .when(service.buscarCategoria(2L)).getId();
        CategoriaResponse categoriaResponse = service.buscarCategoria(2L);
    }


    @Test
    @DisplayName("deve buscar uma categoria por id e alterar o seu valor inicial")
    public void deveRemoverEAdicionarUmaNovaCategoria_alterarCategoria() {
        Categoria categoria = new Categoria(1L, "test", "test_description");
        Mockito.when(repository.findById(ArgumentMatchers.eq(categoria.getId()))).thenReturn(java.util.Optional.of(categoria));
        Assert.assertTrue(categoria.equals(categoria));
        CategoriaRequest categoriaRequest = new CategoriaRequest();
        categoriaRequest.setName("update_test");
        categoriaRequest.setDescription("update_description_test");

        CategoriaResponse categoriaResponse = service.alterarCategoria(1L, categoriaRequest);
        Mockito.verify(repository, Mockito.times(1)).delete(categoria);
        Assert.assertEquals("update_test", categoriaResponse.getName());
        Assert.assertEquals("update_description_test", categoriaResponse.getDescription());
    }

    @Test
    @DisplayName("deve inserir uma nova categoria")
    public void deveInserirUmaCategoriaComSucesso_inserirCategoria() {
        CategoriaRequest categoriaRequest = new CategoriaRequest();
        categoriaRequest.setName("insert_test");
        categoriaRequest.setDescription("insert_description_test");
        Categoria categoria = new Categoria();
        categoria.setName(categoriaRequest.getName());
        categoria.setDescription(categoriaRequest.getDescription());

        CategoriaResponse categoriaResponse = service.inserirCategoria(categoriaRequest);

        Mockito.verify(repository, Mockito.times(1)).save(categoria);

        Assert.assertEquals("insert_test", categoriaResponse.getName());
        Assert.assertEquals("insert_description_test", categoriaResponse.getDescription());
        Assert.assertEquals(categoria.getName(), categoriaResponse.getName());
        Assert.assertEquals(categoria.getDescription(), categoriaResponse.getDescription());

    }

    @Test
    @DisplayName("deve deletar uma categoria")
    public void deveDeletarUmaCategoriaComSucesso_deletarCategoriaPorId() {
        Categoria categoria = new Categoria(1L, "delete_test", "delete_test_description");
        Mockito.when(repository.findById(categoria.getId())).thenReturn(java.util.Optional.of(categoria));
        Assert.assertTrue(categoria.equals(categoria));
        service.deletarCategoriaPorId(categoria.getId());

        Mockito.verify(repository, Mockito.times(1)).findById(categoria.getId());
        Mockito.verify(repository, Mockito.times(1)).deleteById(categoria.getId());
    }
}