package com.trilha.back.finacys.response;

import com.trilha.back.finacys.entity.Categoria;
import lombok.Data;

@Data
public class CategoriaResponse {

	private Long id;

	private String name;

	private String description;

	public CategoriaResponse(Long id, String name, String description) {
		this.id = id;
		this.name = name;
		this.description = description;
	}

	public CategoriaResponse(Categoria categoria){
		this.id = categoria.getId();
		this.name = categoria.getName();
		this.description = categoria.getDescription();

	}

	public CategoriaResponse() {
	}
}
