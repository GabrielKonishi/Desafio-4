package com.trilha.back.finacys.service;

import com.trilha.back.finacys.request.CategoriaRequest;
import com.trilha.back.finacys.response.CategoriaResponse;

import java.util.List;

public interface CategoriaService {

    public CategoriaResponse buscarCategoria(Long id);

    public List<CategoriaResponse> buscarTodasCategorias();

    public CategoriaResponse alterarCategoria(Long id, CategoriaRequest categoriaRequest);

    public CategoriaResponse inserirCategoria(CategoriaRequest categoriaRequest);

    public void deletarCategoriaPorId(Long id);

}
