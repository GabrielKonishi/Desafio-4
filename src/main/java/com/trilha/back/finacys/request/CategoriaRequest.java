package com.trilha.back.finacys.request;

import com.fasterxml.jackson.annotation.JsonProperty;

public class CategoriaRequest {
	
	@JsonProperty(value = "name_categoria")
	private String name;
	
	@JsonProperty(value = "description_categoria")
	private String description;

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

	public CategoriaRequest() {
	}

	public CategoriaRequest(String name, String description) {
		this.name = name;
		this.description = description;
	}
}
