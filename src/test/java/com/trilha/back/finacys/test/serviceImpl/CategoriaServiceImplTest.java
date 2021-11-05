package com.trilha.back.finacys.test.serviceImpl;

import com.trilha.back.finacys.bo.ValidacaoBo;
import com.trilha.back.finacys.entity.Categoria;
import com.trilha.back.finacys.exception.ValidateException;
import com.trilha.back.finacys.repository.CategoriaRepository;
import com.trilha.back.finacys.request.CategoriaRequest;
import com.trilha.back.finacys.response.CategoriaResponse;
import com.trilha.back.finacys.serviceImpl.CategoriaServiceImpl;
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
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;


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
    @DisplayName("deve retornar uma exception na busca de um id inexistente")
    public void deveRetornarUmaException_buscarCategoria() throws ValidateException{
        Long id = 2L;
        Categoria categoria = new Categoria(1L, "test_name", "test_description");
        service.buscarCategoria(id);

        Mockito.verify(repository, Mockito.times(1)).findById(id);
        ValidateException validateException = Assert.assertThrows(ValidateException.class, () -> service.buscarCategoria(id));


        Assert.assertTrue(validateException.getMessage().contains("categoria não encontrada: 2"));
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
        Assert.assertEquals(categoriaResponse.getName(), categoria.getName());
        Assert.assertEquals(categoriaResponse.getDescription(), categoria.getDescription());

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