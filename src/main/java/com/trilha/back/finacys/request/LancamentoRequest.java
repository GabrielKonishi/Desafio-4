package com.trilha.back.finacys.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PastOrPresent;
import java.time.LocalDate;

@Data
public class LancamentoRequest {
	
	@JsonProperty(value = "lancamento_name")
	@NotBlank(message = "O campo nome deve ser preenchido\n")
	private String name;
	
	@JsonProperty(value = "lancamento_description")
	@NotBlank(message = "O campo descricao deve ser preenchido\n")
	private String description;

	@JsonProperty(value = "lancamento_type")
	@NotBlank(message = "O campo type deve ser prenchido")
	private String type;
	
	@JsonProperty(value = "lancamento_amount")
	@NotBlank(message = "O campo amount deve ser preenchido")
	private String amount;
	
	@JsonProperty(value = "lancamento_date")
	@PastOrPresent(message = "A data deve ser anterior ao dia do cadastro")
	private LocalDate date;

	@JsonProperty(value = "lancamento_paid")
	@NotNull(message = "O campo de pagamento deve ser preenchido")
	private boolean paid;
	
	@JsonProperty(value = "categoria_id")
	private CategoriaRequest categoria;

	public LancamentoRequest(String name, String description, String type, String amount, LocalDate date, boolean paid, CategoriaRequest categoria) {
		this.name = name;
		this.description = description;
		this.type = type;
		this.amount = amount;
		this.date = date;
		this.paid = paid;
		this.categoria = categoria;
	}

	public LancamentoRequest() {
	}


}
