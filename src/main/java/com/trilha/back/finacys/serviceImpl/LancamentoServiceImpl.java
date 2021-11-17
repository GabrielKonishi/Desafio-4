package com.trilha.back.finacys.serviceImpl;

import com.trilha.back.finacys.bo.ValidacaoBo;
import com.trilha.back.finacys.entity.Lancamento;
import com.trilha.back.finacys.exception.ValidateException;
import com.trilha.back.finacys.repository.CategoriaRepository;
import com.trilha.back.finacys.repository.LancamentoRepository;
import com.trilha.back.finacys.request.LancamentoRequest;
import com.trilha.back.finacys.response.LancamentoResponse;
import com.trilha.back.finacys.service.LancamentoService;
import org.apache.tomcat.jni.Local;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional(rollbackFor = Exception.class)
public class LancamentoServiceImpl implements LancamentoService {

    @Autowired
    private LancamentoRepository repository;

    @Autowired
    private CategoriaRepository categoriaRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    ValidacaoBo bo;


    public LancamentoResponse buscarLancamentoPorId(Long id) throws ValidateException {
        bo.validarObrigatoriedade(id, "lancamento_id");
        Lancamento lancamento = repository.findById(id)
                .orElseThrow(() -> new ValidateException("Lancamento não encontrado: " + id, HttpStatus.NOT_FOUND));

        return converterEntityParaResponse(lancamento);

    }

    public List<LancamentoResponse> buscarTodosLancamentos(Optional<Boolean> paid, Optional<Long> categoriaId) {

        if(repository.findAll().isEmpty()){
            throw new ValidateException("Não há Lancamentos cadastrados", HttpStatus.NOT_FOUND);
        }
        if (categoriaId.isPresent()) {
            categoriaRepository.findById(categoriaId.get())
                    .orElseThrow(() -> new ValidateException("Categoria não encontrada: " + categoriaId.get().longValue(), HttpStatus.NOT_FOUND));
            return repository.findAll().stream().filter(e -> e.getCategoria().getId().equals(categoriaId.get()))
                    .map(this::converterEntityParaResponse).collect(Collectors.toList());
        }
        if (paid.isPresent()) {
            return repository.listarLancamentosPagos(paid).stream()
                    .map(this::converterEntityParaResponse).collect(Collectors.toList());
        }

        return repository.findAll().stream().map(this::converterEntityParaResponse).collect(Collectors.toList());
    }


    public LancamentoResponse inserirLancamento(LancamentoRequest request) {
        validarCamposLancamento(request);

        try {
            Lancamento lancamento = converterParaEntityInserir(request);
            repository.save(lancamento);
            return converterEntityParaResponse(lancamento);
        } catch (Exception e) {
            throw new ValidateException(
                    "Não há categorias cadastradas com o id informado: " + request.getCategoria(),
                    HttpStatus.NOT_FOUND);
        }
    }

    public LancamentoResponse alterarLancamento(Long id, LancamentoRequest request) {
        validateCategoryById(request.getCategoria().getId());
        bo.validarObrigatoriedade(id, "lancamento_id");
        validarCamposLancamento(request);
        Lancamento lancamento = repository.findById(id)
                .orElseThrow(() -> new ValidateException("Lancamento não encontrado: " + id, HttpStatus.NOT_FOUND));

        categoriaRepository.findById(request.getCategoria().getId())
                .orElseThrow(() -> new ValidateException("Categoria não encontrada " + request.getCategoria().getId(), HttpStatus.NOT_FOUND));

        Lancamento lancamentoEntity = converterRequestParaEntity(lancamento, request);

        repository.save(lancamentoEntity);
        return converterEntityParaResponse(lancamentoEntity);
    }

    public void deletarLancamento(Long id) {
        bo.validarObrigatoriedade(id, "lancamento_id");
        Optional<Lancamento> entityOp = repository.findById(id);
        if (!entityOp.isPresent()) {
            throw new ValidateException("Lancamento não encontrado: " + id, HttpStatus.NOT_FOUND);
        }
        repository.deleteById(id);
    }

    public List<LancamentoResponse> buscarLancamentoPorDependentes(String date,
                                                           String amount,
                                                           Optional<Boolean> paid){

        if(date == null || date.isEmpty()){
            throw new ValidateException("preencha o campo data", HttpStatus.BAD_REQUEST);
        }
        if(amount == null || amount.isEmpty()){
            throw new ValidateException("preencha o campo amount", HttpStatus.BAD_REQUEST);
        }
        if(!paid.isPresent() || paid.get() == null){
            throw new ValidateException("preencha o campo de pagamento", HttpStatus.BAD_REQUEST);
        }


        List<LancamentoResponse> response = repository.findAll()
                .stream()
                .filter(l -> date.equals(l.getDate().toString())
                        && amount.equals(l.getAmount())
                        && paid.get().equals(l.isPaid()))
                .map(this::converterEntityParaResponse)
                .collect(Collectors.toList());

        if(response.isEmpty()){
            throw new ValidateException("Não há lancamentos cadastrados com os valores fornecidos", HttpStatus.NOT_FOUND);
        }
        return response;
    }

    public boolean validateCategoryById(long id) {
        if (!categoriaRepository.findById(id).isPresent()) {
            throw new ValidateException("Categoria não existe: " + id, HttpStatus.NOT_FOUND);
        }
        return true;
    }

    private Lancamento converterRequestParaEntity(Lancamento lancamento, LancamentoRequest lancamentoRequest) {
        lancamento.setName(lancamentoRequest.getName());
        lancamento.setDescription(lancamentoRequest.getDescription());
        lancamento.setType(lancamentoRequest.getType());
        lancamento.setAmount(lancamentoRequest.getAmount());
        lancamento.setDate(lancamentoRequest.getDate());
        lancamento.setPaid(lancamentoRequest.isPaid());
        lancamento.setCategoria(lancamentoRequest.getCategoria());
        return lancamento;
    }

    private Lancamento converterParaEntityInserir(LancamentoRequest lancamentoRequest) {
        Lancamento lancamento = modelMapper.map(lancamentoRequest, Lancamento.class);
        return lancamento;
    }

    private LancamentoResponse converterEntityParaResponse(Lancamento lancamento) {
        return modelMapper.map(lancamento, LancamentoResponse.class);
    }


    private void validarCamposLancamento(LancamentoRequest lancamentoRequest) {
        bo.validarObrigatoriedade(lancamentoRequest.getName(), "lancamento_name");
        bo.validarObrigatoriedade(lancamentoRequest.getDescription(), "lancamento_description");
        bo.validarObrigatoriedade(lancamentoRequest.getType(), "lancamento_type");
        bo.validarObrigatoriedade(lancamentoRequest.getAmount(), "lancamento_amount");
        bo.validarObrigatoriedade(lancamentoRequest.getDate(), "lancamento_date");
        bo.validarObrigatoriedade(lancamentoRequest.getCategoria().getId(), "categoria_id");

    }



    public Integer calcularMedia(Integer x, Integer y) {

        try{
            return x/y;

        }catch (ArithmeticException e){

            throw new ValidateException("não é possivel fazer a divisão pelo numero informado: " + y, HttpStatus.BAD_REQUEST);

        }


    }
}
