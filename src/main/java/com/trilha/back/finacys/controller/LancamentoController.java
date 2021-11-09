package com.trilha.back.finacys.controller;

import com.trilha.back.finacys.exception.ValidateException;
import com.trilha.back.finacys.request.LancamentoRequest;
import com.trilha.back.finacys.response.LancamentoResponse;
import com.trilha.back.finacys.serviceImpl.LancamentoServiceImpl;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@CrossOrigin("*")
@RestController
@RequestMapping("/lancamento")
@Api(value = "Api rest lancamento")
public class LancamentoController {

	@Autowired
	LancamentoServiceImpl service;

	Logger logger = org.slf4j.LoggerFactory.getLogger(getClass());


	@GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	@ApiOperation(value = "Retorna um lancamento")
	public ResponseEntity<LancamentoResponse> buscarLancamentoPorId(@PathVariable Long id) throws ValidateException {
		logger.info("INICIANDO O METODO GET");

		return new ResponseEntity<LancamentoResponse>(service.buscarLancamentoPorId(id), HttpStatus.OK);
	}

	@GetMapping( produces = MediaType.APPLICATION_JSON_VALUE)
	@ApiOperation(value = "Retorna uma Lista de lancamentos")
	public ResponseEntity<List<LancamentoResponse>> buscarTodosLancamentos(
			@RequestParam(value = "lancamento_paid", required = false) Optional<Boolean> paid,
			@RequestParam(value = "categoria_id", required = false) Optional<Long> categoriaId) {

		return new ResponseEntity<List<LancamentoResponse>>(service.buscarTodosLancamentos(paid,categoriaId), HttpStatus.OK);
	}

	@PostMapping( produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	@ApiOperation(value = "Cadastra um lancamento")
	public ResponseEntity<LancamentoResponse> inserirLancamento(@RequestBody @Valid LancamentoRequest lancamentoRequest)
			throws Exception {
		return new ResponseEntity<LancamentoResponse>(service.inserirLancamento(lancamentoRequest), HttpStatus.CREATED);

	}

	@PutMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	@ApiOperation(value = "Atualiza um lancamento")
	public ResponseEntity<LancamentoResponse> alterarLancamento(@PathVariable(value = "id") Long id,
			@RequestBody @Valid LancamentoRequest lancamentoRequest) throws ValidateException {

		return new ResponseEntity<LancamentoResponse>(service.alterarLancamento(id, lancamentoRequest), HttpStatus.OK);

	}

	@DeleteMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	@ApiOperation(value = "Deleta um lancamento cadastrado")
	public ResponseEntity<Object> deletarLancamentoPorId(@PathVariable(value = "id") Long id) throws ValidateException {
		service.deletarLancamento(id);
		return new ResponseEntity<Object>(HttpStatus.OK);
	}

	@GetMapping(value = "/calcula", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Integer> calculaMedia(
			@RequestParam Integer variableX,
			@RequestParam Integer variableY){

		return new ResponseEntity<Integer>(service.calcularMedia(variableX, variableY), HttpStatus.OK);
	}

}
