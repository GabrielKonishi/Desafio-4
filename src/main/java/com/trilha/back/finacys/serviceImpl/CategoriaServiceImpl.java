package com.trilha.back.finacys.serviceImpl;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.trilha.back.finacys.bo.ValidacaoBo;
import com.trilha.back.finacys.entity.Categoria;
import com.trilha.back.finacys.exception.ValidateException;
import com.trilha.back.finacys.repository.CategoriaRepository;
import com.trilha.back.finacys.request.CategoriaRequest;
import com.trilha.back.finacys.response.CategoriaResponse;

@Service
@Transactional(rollbackFor = Exception.class)
public class CategoriaServiceImpl {

	@Autowired
	CategoriaRepository repository;

	@Autowired
	ValidacaoBo bo;
	

	public CategoriaResponse buscarCategoria(Long id) throws ValidateException {
		bo.validarObrigatoriedade(id, "id_categoria");
		Optional<Categoria> categoriaOp = repository.findById(id);
		if (!categoriaOp.isPresent()) {
			throw new ValidateException("categoria não encontrada: " + id, HttpStatus.NOT_FOUND);
		}

		return converterEntityParaResponse(categoriaOp.get());

	}
	
	public List<CategoriaResponse> buscarTodasCategorias()throws ValidateException{
		List<Categoria> findAll = repository.findAll();
		
		if(findAll.isEmpty()) {
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
		if(!entityOp.isPresent()) {
			throw new ValidateException("categoria não encontrada: " + id, HttpStatus.NOT_FOUND);
		}
		repository.deleteById(id);
	}

	private Categoria converterRequestParaEntity(CategoriaRequest categoriaRequest) {
		Categoria categoria = new Categoria();
		categoria.setName(categoriaRequest.getName());
		categoria.setDescription(categoriaRequest.getDescription());
		return categoria;
	}

	private CategoriaResponse converterEntityParaResponse(Categoria categoria) {
		CategoriaResponse categoriaResponse = new CategoriaResponse();
		categoriaResponse.setId(categoria.getId());
		categoriaResponse.setDescription(categoria.getDescription());
		categoriaResponse.setName(categoria.getName());
		return categoriaResponse;
	}

	private void validarCamposRequest(CategoriaRequest categoriaRequest) {
		bo.validarObrigatoriedade(categoriaRequest.getName(), "name_categoria");
		bo.validarObrigatoriedade(categoriaRequest.getDescription(), "description_categoria");
	}
	
	// criar um metodo para validar a entrada do request
	// alterar os sysouts por log4J

}
