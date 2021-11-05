package com.trilha.back.finacys.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.trilha.back.finacys.entity.Categoria;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
public class CategoriaRequest {

	@JsonProperty(value = "id_categoria")
	@NotNull(message = "o Id não deve ser nulo")
	private Long id;
	
	@JsonProperty(value = "name_categoria")
	@NotBlank(message = "O campo nome deve ser prenchido")
	private String name;
	
	@JsonProperty(value = "description_categoria")
	@NotBlank(message = "O campo descricao deve ser preenchido")
	private String description;

	public CategoriaRequest() {
	}

	public CategoriaRequest(String name, String description) {
		this.name = name;
		this.description = description;
	}

	public CategoriaRequest(Categoria categoria) {
		this.name = categoria.getName();
		this.description = categoria.getDescription();
	}



}
