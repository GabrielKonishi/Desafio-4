package com.trilha.back.finacys.controller;

import com.trilha.back.finacys.exception.ValidateException;
import com.trilha.back.finacys.request.CategoriaRequest;
import com.trilha.back.finacys.response.CategoriaResponse;
import com.trilha.back.finacys.serviceImpl.CategoriaServiceImpl;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin("*") // essa anotação define que qualquer dominio pode acessar essa api
@RestController
@Api(value = "Api rest categoria")
public class CategoriaController {

	@Autowired
	CategoriaServiceImpl service;
	
	Logger logger = org.slf4j.LoggerFactory.getLogger(getClass());

	@GetMapping(value = "/categoria/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	@ApiOperation(value = "Retorna uma categoria")
	public ResponseEntity<CategoriaResponse> buscarCategoriaPorId(@PathVariable(value = "id") Long id)
			throws ValidateException {
		logger.info("------INICIANDO O METODO GET------");
		return new ResponseEntity<CategoriaResponse>(service.buscarCategoria(id), HttpStatus.OK);
	}

	@GetMapping(value = "/categoria",produces = MediaType.APPLICATION_JSON_VALUE)
	@ApiOperation(value = "Retorna uma Lista de categorias")
	public ResponseEntity<List<CategoriaResponse>> buscarTodasCategorias(){
		logger.info("Listando todas as categorias");
		return new ResponseEntity<List<CategoriaResponse>>(service.buscarTodasCategorias(), HttpStatus.OK);
	}
	
	@PostMapping(value = "/categoria", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	@ApiOperation(value = "cadastra uma categoria")
	public ResponseEntity<CategoriaResponse> cadastrarCategoria(@RequestBody CategoriaRequest categoriaRequest) {
		return new ResponseEntity<CategoriaResponse>(service.inserirCategoria(categoriaRequest), HttpStatus.CREATED);
	}

	@PutMapping(value = "/categoria/{id}", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	@ApiOperation(value = "atualiza os valores de uma categoria previamente cadastrada")
	public ResponseEntity<CategoriaResponse> alterarCategoria(@PathVariable(value = "id") Long id,
			@RequestBody CategoriaRequest categoriaRequest) throws ValidateException {

		return new ResponseEntity<CategoriaResponse>(service.alterarCategoria(id, categoriaRequest), HttpStatus.OK);
	}
	
	@DeleteMapping(value = "/categoria/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	@ApiOperation(value = "Deleta uma categoria cadastrada")
	public ResponseEntity<Object> deletarCategoria(@PathVariable(value = "id") Long id){
		service.deletarCategoriaPorId(id);
		
		return new ResponseEntity<Object>(HttpStatus.OK);
	}
	

}
