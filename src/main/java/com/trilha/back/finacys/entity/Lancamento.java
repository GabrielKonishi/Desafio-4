package com.trilha.back.finacys.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "LANCAMENTO")
@Data
@EqualsAndHashCode
public class Lancamento {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "LANCAMENTO_ID")
	private Long id;

	@Column(name = "LANCAMENTO_NAME")
	private String name;

	@Column(name = "LANCAMENTO_DESCRIPTION")
	private String description;

	@Column(name = "TYPE")
	private String type;

	@Column(name = "AMOUNT")
	private String amount;

	@Column(name = "DATE")
	private LocalDate date;

	@Column(name = "PAID")
	private boolean paid;

	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
	@JoinColumn(name = "CATEGORIA_ID", referencedColumnName = "ID")
	private Categoria categoria;



	public Lancamento(Long id, String name, String description, String type, String amount, LocalDate date,
			boolean paid, Categoria categoria) {
		super();
		this.id = id;
		this.name = name;
		this.description = description;
		this.type = type;
		this.amount = amount;
		this.date = date;
		this.paid = paid;
		this.categoria = categoria;
	}

	public Lancamento() {
		super();
	}
}
