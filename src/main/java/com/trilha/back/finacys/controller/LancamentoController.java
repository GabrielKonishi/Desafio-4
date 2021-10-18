package com.trilha.back.finacys.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.trilha.back.finacys.exception.ValidateException;
import com.trilha.back.finacys.request.LancamentoRequest;
import com.trilha.back.finacys.response.LancamentoResponse;
import com.trilha.back.finacys.serviceImpl.LancamentoServiceImpl;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@CrossOrigin("*")
@RestController
@Api(value = "Api rest lancamento")
public class LancamentoController {

	@Autowired
	LancamentoServiceImpl service;

	@GetMapping(value = "/lancamento/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	@ApiOperation(value = "Retorna um lancamento")
	public ResponseEntity<LancamentoResponse> buscarLancamentoPorId(@PathVariable Long id) throws ValidateException {

		return new ResponseEntity<LancamentoResponse>(service.buscarLancamentoPorId(id), HttpStatus.OK);
	}

	@GetMapping(value = "/lancamento", produces = MediaType.APPLICATION_JSON_VALUE)
	@ApiOperation(value = "Retorna uma Lista de lancamentos")
	public ResponseEntity<List<LancamentoResponse>> buscarTodosLancamentos() {

		return new ResponseEntity<List<LancamentoResponse>>(service.buscarTodosLancamentos(), HttpStatus.OK);
	}
	
	@GetMapping(value = "/lancamentoPago", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<LancamentoResponse>> buscarLancamentosPagos(@RequestParam(value = "lancamento_paid", required = false) boolean paid){
		
		return new ResponseEntity<List<LancamentoResponse>>(service.listarLancamentosPagos(paid), HttpStatus.OK);
	}
 
	
	@PostMapping(value = "/lancamento", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	@ApiOperation(value = "Cadastra um lancamento")
	public ResponseEntity<LancamentoResponse> inserirLancamento(@RequestBody LancamentoRequest lancamentoRequest)
			throws Exception {
		return new ResponseEntity<LancamentoResponse>(service.inserirLancamento(lancamentoRequest), HttpStatus.CREATED);

	}

	@PutMapping(value = "/lancamento/{id}", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	@ApiOperation(value = "Atualiza um lancamento")
	public ResponseEntity<LancamentoResponse> alterarLancamento(@PathVariable(value = "id") Long id,
			@RequestBody LancamentoRequest lancamentoRequest) throws ValidateException {

		return new ResponseEntity<LancamentoResponse>(service.alterarLancamento(id, lancamentoRequest), HttpStatus.OK);

	}

	@DeleteMapping(value = "/lancamento/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	@ApiOperation(value = "Deleta um lancamento cadastrado")
	public ResponseEntity<Object> deletarLancamentoPorId(@PathVariable(value = "id") Long id) throws ValidateException {
		service.deletarLancamento(id);
		return new ResponseEntity<Object>(HttpStatus.OK);
	}

}