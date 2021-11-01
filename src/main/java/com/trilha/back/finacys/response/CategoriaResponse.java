package com.trilha.back.finacys.response;

import com.trilha.back.finacys.entity.Categoria;

public class CategoriaResponse {

	private Long id;

	private String name;

	private String description;


	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

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
	

	public CategoriaResponse(CategoriaResponse categoria) {
		super();
		this.id = categoria.getId();
		this.name = categoria.getName();
		this.description = categoria.getDescription();
	}

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
