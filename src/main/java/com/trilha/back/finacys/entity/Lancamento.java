package com.trilha.back.finacys.entity;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Objects;

@Entity
@Table(name = "LANCAMENTO")
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

	public Categoria getCategoria() {
		return categoria;
	}

	public void setCategoria(Categoria categoria) {
		this.categoria = categoria;
	}

	@Override
	public String toString() {
		return "Lancamento [id=" + id + ", name=" + name + ", description=" + description + ", type=" + type
				+ ", amount=" + amount + ", date=" + date + ", paid=" + paid + ", categoria=" + categoria + "]";
	}

	@Override
	public int hashCode() {
		return Objects.hash(id);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Lancamento other = (Lancamento) obj;
		return Objects.equals(id, other.id);
	}

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
