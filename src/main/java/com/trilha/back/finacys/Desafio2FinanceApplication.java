package com.trilha.back.finacys;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;

@EntityScan(basePackages = "com.trilha.back.finacys.entity")
@SpringBootApplication
public class Desafio2FinanceApplication {
	public static void main(String[] args) {
		SpringApplication.run(Desafio2FinanceApplication.class, args);
	}

}
