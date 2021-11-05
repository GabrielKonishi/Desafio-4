package com.trilha.back.finacys.response;

import com.trilha.back.finacys.entity.Lancamento;
import lombok.Data;

import java.time.LocalDate;

@Data
public class LancamentoResponse {

    private String name;

    private String description;

    private String type;

    private String amount;

    private LocalDate date;

    private boolean paid;

    private CategoriaResponse categoria;


    public LancamentoResponse(Lancamento lancamento) {
        super();
        this.name = lancamento.getName();
        this.amount = lancamento.getAmount();
        this.description = lancamento.getDescription();
        this.paid = lancamento.isPaid();
        this.type = lancamento.getType();
        this.date = lancamento.getDate();
    }

    public LancamentoResponse() {
        super();
    }


}
