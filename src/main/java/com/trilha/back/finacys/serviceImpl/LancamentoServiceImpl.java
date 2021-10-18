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
import com.trilha.back.finacys.entity.Lancamento;
import com.trilha.back.finacys.exception.ValidateException;
import com.trilha.back.finacys.repository.CategoriaRepository;
import com.trilha.back.finacys.repository.LancamentoRepository;
import com.trilha.back.finacys.request.LancamentoRequest;
import com.trilha.back.finacys.response.LancamentoResponse;

@Service
@Transactional(rollbackFor = Exception.class)
public class LancamentoServiceImpl {

	@Autowired
	LancamentoRepository repository;

	@Autowired
	CategoriaRepository categoriaRepository;

	@Autowired
	ValidacaoBo bo;

	public LancamentoResponse buscarLancamentoPorId(Long id) throws ValidateException {
		bo.validarObrigatoriedade(id, "lancamento_id");
		Optional<Lancamento> lancamentoOp = repository.findById(id);
		if (!lancamentoOp.isPresent()) {
			throw new ValidateException("Lancamento não encontrado: " + id, HttpStatus.NOT_FOUND);
		}
		return converterEntityParaResponse(lancamentoOp.get());

	}

	public List<LancamentoResponse> buscarTodosLancamentos() {
		List<Lancamento> todosLancamentos = repository.findAll();
		if (todosLancamentos.isEmpty()) {
			throw new ValidateException("Não há lancamentos cadastrados ", HttpStatus.NOT_FOUND);
		}
		return todosLancamentos.stream().map(this::converterEntityParaResponse).collect(Collectors.toList());
	}

	public LancamentoResponse inserirLancamento(LancamentoRequest request) {
		validarCamposLancamento(request);

		List<Categoria> todasCategorias = categoriaRepository.findAll();
		for (Categoria categoria : todasCategorias) {
			if (request.getCategoryId().getId() == categoria.getId()) {
				Lancamento lancamento = converterRequestParaEntity(request);
				repository.save(lancamento);
				return converterEntityParaResponse(lancamento);
			}

		}
		throw new ValidateException(
				"Não há categorias cadastradas com o id informado: " + request.getCategoryId().getId(),
				HttpStatus.NOT_FOUND);

	}

	public LancamentoResponse alterarLancamento(Long id, LancamentoRequest request) {
		bo.validarObrigatoriedade(id, "lancamento_id");
		validarCamposLancamento(request);
		Optional<Lancamento> entityOp = repository.findById(id);
		if (!entityOp.isPresent()) {
			throw new ValidateException("Lancamento não encontrado: " + id, HttpStatus.NOT_FOUND);
		}
		repository.deleteById(id);
		Lancamento lancamento = converterRequestParaEntity(request);
		repository.save(lancamento);
		return converterEntityParaResponse(lancamento);

	}

	public void deletarLancamento(Long id) {
		bo.validarObrigatoriedade(id, "lancamento_id");
		Optional<Lancamento> entityOp = repository.findById(id);
		if (!entityOp.isPresent()) {
			throw new ValidateException("Lancamento não encontrado: " + id, HttpStatus.NOT_FOUND);
		}
		repository.delete(entityOp.get());
	}

	public List<LancamentoResponse> listarLancamentosPagos(boolean paid) {

		List<Lancamento> listarLancamentosPagos = repository.listarLancamentosPagos(paid);
		return listarLancamentosPagos.stream().map(this::converterEntityParaResponse).collect(Collectors.toList());

	}

	private Lancamento converterRequestParaEntity(LancamentoRequest lancamentoRequest) {
		Lancamento lancamentoEntity = new Lancamento();
		lancamentoEntity.setName(lancamentoRequest.getName());
		lancamentoEntity.setDescription(lancamentoRequest.getDescription());
		lancamentoEntity.setType(lancamentoRequest.getType());
		lancamentoEntity.setAmount(lancamentoRequest.getAmount());
		lancamentoEntity.setDate(lancamentoRequest.getDate());
		lancamentoEntity.setPaid(lancamentoRequest.isPaid());
		lancamentoEntity.setCategoria(lancamentoRequest.getCategoryId());
		return lancamentoEntity;
	}

	private LancamentoResponse converterEntityParaResponse(Lancamento lancamento) {
		LancamentoResponse lancamentoResponse = new LancamentoResponse();
		lancamentoResponse.setName(lancamento.getName());
		lancamentoResponse.setDescription(lancamento.getDescription());
		lancamentoResponse.setType(lancamento.getType());
		lancamentoResponse.setAmount(lancamento.getAmount());
		lancamentoResponse.setDate(lancamento.getDate());
		lancamentoResponse.setPaid(lancamento.isPaid());
		lancamentoResponse.setCategoria(lancamento.getCategoria().getId());
		return lancamentoResponse;
	}

	private void validarCamposLancamento(LancamentoRequest lancamentoRequest) {
		bo.validarObrigatoriedade(lancamentoRequest.getName(), "lancamento_name");
		bo.validarObrigatoriedade(lancamentoRequest.getDescription(), "lancamento_description");
		bo.validarObrigatoriedade(lancamentoRequest.getType(), "lancamento_type");
		bo.validarObrigatoriedade(lancamentoRequest.getAmount(), "lancamento_amount");
		bo.validarObrigatoriedade(lancamentoRequest.getDate(), "lancamento_date");
		bo.validarObrigatoriedade(lancamentoRequest.getCategoryId().getId(), "categoria_id");

	}

}
