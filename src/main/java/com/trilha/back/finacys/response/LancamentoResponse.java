package com.trilha.back.finacys.response;

import java.time.LocalDate;

import com.trilha.back.finacys.entity.Lancamento;

public class LancamentoResponse {

	private String name;

	private String description;

	private String type;

	private String amount;

	private LocalDate date;

	private boolean paid;
	
	private Long categoria;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getAmount() {
		return amount;
	}

	public void setAmount(String amount) {
		this.amount = amount;
	}

	public LocalDate getDate() {
		return date;
	}

	public void setDate(LocalDate date) {
		this.date = date;
	}

	public boolean isPaid() {
		return paid;
	}

	public void setPaid(boolean paid) {
		this.paid = paid;
	}
	
	

	public Long getCategoria() {
		return categoria;
	}

	public void setCategoria(Long categoria) {
		this.categoria = categoria;
	}

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
