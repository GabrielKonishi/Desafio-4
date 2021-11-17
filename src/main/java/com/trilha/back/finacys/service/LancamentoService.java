package com.trilha.back.finacys.service;

import com.trilha.back.finacys.request.LancamentoRequest;
import com.trilha.back.finacys.response.LancamentoResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;
import java.util.Optional;

public interface LancamentoService {

    public LancamentoResponse buscarLancamentoPorId(Long id);

    public List<LancamentoResponse> buscarTodosLancamentos(Optional<Boolean> paid, Optional<Long> categoriaId);

    public LancamentoResponse inserirLancamento(LancamentoRequest request);

    public LancamentoResponse alterarLancamento(Long id, LancamentoRequest request);

    public void deletarLancamento(Long id);

    public List<LancamentoResponse> buscarLancamentoPorDependentes(String date,
                                                                   String amount,
                                                                   Optional<Boolean> paid);

    public Integer calcularMedia(Integer x, Integer y);

}
