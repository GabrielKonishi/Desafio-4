package com.trilha.back.finacys.service.impl;

import com.trilha.back.finacys.bo.ValidacaoBo;
import com.trilha.back.finacys.entity.Categoria;
import com.trilha.back.finacys.exception.ValidateException;
import com.trilha.back.finacys.repository.CategoriaRepository;
import com.trilha.back.finacys.request.CategoriaRequest;
import com.trilha.back.finacys.response.CategoriaResponse;
import com.trilha.back.finacys.service.CategoriaService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional(rollbackFor = Exception.class)
public class CategoriaServiceImpl implements CategoriaService {

    @Autowired
    CategoriaRepository repository;

    @Autowired
    ValidacaoBo bo;

    @Autowired
    private ModelMapper modelMapper;


    public CategoriaResponse buscarCategoria(Long id) throws ValidateException {
        bo.validarObrigatoriedade(id, "id_categoria");
        Categoria categoria = repository.findById(id).orElseThrow(() -> new ValidateException("Categoria não encontrada: " + id, HttpStatus.NOT_FOUND));
        return converterEntityParaResponse(categoria);

    }

    public List<CategoriaResponse> buscarTodasCategorias() throws ValidateException {
        List<Categoria> findAll = repository.findAll();

        if (findAll.isEmpty()) {
            throw new ValidateException("Não há categorias cadastradas", HttpStatus.NOT_FOUND);
        }

        return findAll.stream()
                .map(this::converterEntityParaResponse)
                .collect(Collectors.toList());
    }

    public CategoriaResponse alterarCategoria(Long id, CategoriaRequest categoriaRequest) throws ValidateException {
        bo.validarObrigatoriedade(id, "lancamento_id");
        validarCamposRequest(categoriaRequest);
        Optional<Categoria> categoriaOp = repository.findById(id);
        if (!categoriaOp.isPresent()) {
            throw new ValidateException("categoria não encontrada: " + id, HttpStatus.NOT_FOUND);
        }
        repository.delete(categoriaOp.get());
        Categoria categoriaNova = converterRequestParaEntity(categoriaRequest);
        repository.save(categoriaNova);
        return converterEntityParaResponse(categoriaNova);

    }

    public CategoriaResponse inserirCategoria(CategoriaRequest categoriaRequest) {
        validarCamposRequest(categoriaRequest);
        Categoria categoria = converterRequestParaEntity(categoriaRequest);
        repository.save(categoria);

        return converterEntityParaResponse(categoria);
    }

    public void deletarCategoriaPorId(Long id) {
        bo.validarObrigatoriedade(id, "categoria_id");
        Optional<Categoria> entityOp = repository.findById(id);
        if (!entityOp.isPresent()) {
            throw new ValidateException("categoria não encontrada: " + id, HttpStatus.NOT_FOUND);
        }
        repository.deleteById(id);
    }

    private Categoria converterRequestParaEntity(CategoriaRequest categoriaRequest) {
        return modelMapper.map(categoriaRequest, Categoria.class);
    }

    private CategoriaResponse converterEntityParaResponse(Categoria categoria) {
        return modelMapper.map(categoria, CategoriaResponse.class);
    }

    private void validarCamposRequest(CategoriaRequest categoriaRequest) {
        bo.validarObrigatoriedade(categoriaRequest.getName(), "name_categoria");
        bo.validarObrigatoriedade(categoriaRequest.getDescription(), "description_categoria");
    }
}
