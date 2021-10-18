package com.trilha.back.finacys.request;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.trilha.back.finacys.entity.Categoria;

public class LancamentoRequest {
	
	@JsonProperty(value = "lancamento_name")
	private String name;
	
	@JsonProperty(value = "lancamento_description")
	private String description;
	
	@JsonProperty(value = "lancamento_type")
	private String type;
	
	@JsonProperty(value = "lancamento_amount")
	private String amount;
	
	@JsonProperty(value = "lancamento_date")
	private LocalDate date;

	@JsonProperty(value = "lancamento_paid")
	private boolean paid;
	
	@JsonProperty(value = "categoria_id")
	private Categoria categoryId;

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

	public Categoria getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(Categoria categoryId) {
		this.categoryId = categoryId;
	}
	
}
