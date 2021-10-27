package com.trilha.back.finacys.serviceImpl;

import com.trilha.back.finacys.bo.ValidacaoBo;
import com.trilha.back.finacys.entity.Lancamento;
import com.trilha.back.finacys.exception.ValidateException;
import com.trilha.back.finacys.repository.CategoriaRepository;
import com.trilha.back.finacys.repository.LancamentoRepository;
import com.trilha.back.finacys.request.LancamentoRequest;
import com.trilha.back.finacys.response.LancamentoResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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

    public List<LancamentoResponse> buscarTodosLancamentos(Optional<Boolean> paid) {

        if (!paid.isPresent()) {
            List<Lancamento> todosLancamentos = repository.findAll();
            return todosLancamentos.stream().map(this::converterEntityParaResponse).collect(Collectors.toList());
        }
        List<Lancamento> listarLancamentosPagos = repository.listarLancamentosPagos(paid);
        return listarLancamentosPagos.stream().map(this::converterEntityParaResponse).collect(Collectors.toList());
    }

    public LancamentoResponse inserirLancamento(LancamentoRequest request) {
        validarCamposLancamento(request);

        try {
            Lancamento lancamento = converterParaEntityInserir(request);
            repository.save(lancamento);
            return converterEntityParaResponse(lancamento);
        } catch (Exception e) {
            throw new ValidateException(
                    "Não há categorias cadastradas com o id informado: " + request.getCategoryId().getId(),
                    HttpStatus.NOT_FOUND);
        }


    }

    public LancamentoResponse alterarLancamento(Long id, LancamentoRequest request) {
        bo.validarObrigatoriedade(id, "lancamento_id");
        validarCamposLancamento(request);
        Lancamento lancamento = repository.findById(id)
                .orElseThrow(() -> new ValidateException("Lancamento não encontrado: " + id, HttpStatus.NOT_FOUND));

        repository.save(converterRequestParaEntity(lancamento, request));
        return converterEntityParaResponse(lancamento);
    }

    public void deletarLancamento(Long id) {
        bo.validarObrigatoriedade(id, "lancamento_id");
        Optional<Lancamento> entityOp = repository.findById(id);
        if (!entityOp.isPresent()) {
            throw new ValidateException("Lancamento não encontrado: " + id, HttpStatus.NOT_FOUND);
        }
        repository.deleteById(id);
    }

    public boolean validateCategoryById(long id) {
        if (!categoriaRepository.findById(id).isPresent()) {
            throw new ValidateException("Categoria não encontrada:" + id, HttpStatus.NOT_FOUND);
        }
        return true;
    }

    private Lancamento converterRequestParaEntity(Lancamento entity, LancamentoRequest lancamentoRequest) {
        entity.setName(lancamentoRequest.getName());
        entity.setDescription(lancamentoRequest.getDescription());
        entity.setType(lancamentoRequest.getType());
        entity.setAmount(lancamentoRequest.getAmount());
        entity.setDate(lancamentoRequest.getDate());
        entity.setPaid(lancamentoRequest.isPaid());
        entity.setCategoria(lancamentoRequest.getCategoryId());
        return entity;
    }

    private Lancamento converterParaEntityInserir(LancamentoRequest lancamentoRequest) {
        Lancamento lancamento = new Lancamento();
        lancamento.setName(lancamentoRequest.getName());
        lancamento.setDescription(lancamentoRequest.getDescription());
        lancamento.setType(lancamentoRequest.getType());
        lancamento.setAmount(lancamentoRequest.getAmount());
        lancamento.setDate(lancamentoRequest.getDate());
        lancamento.setPaid(lancamentoRequest.isPaid());
        lancamento.setCategoria(lancamentoRequest.getCategoryId());
        return lancamento;
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
