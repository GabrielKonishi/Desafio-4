package com.trilha.back.finacys.validacao;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;

import com.trilha.back.finacys.entity.Categoria;
import com.trilha.back.finacys.exception.ValidateException;

@Configuration
public class ValidarObrigatoriedade {

	public void validarObrigatoriedadeId(Long id) throws ValidateException {
		Categoria categoria = new Categoria();
		System.out.print("-------------validar obrigatoriedade-------------------");

		if (id == null) {
			System.out.print("-------------validar obrigatoriedade-------------------");
			throw new ValidateException("campo inexistente", HttpStatus.BAD_REQUEST);
		}

	}

}
