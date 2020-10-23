package lojaunit.entities;

import java.sql.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name="vendas")
public class Venda {
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Integer id;
	private Date datahora;
	@ManyToOne
	@JoinColumn(name="id_cliente")
	private Cliente cliente;
	@ManyToOne
	@JoinColumn(name="id_forma_pagamento")
	private FormaPagamento formaPagamento;
	private Double valorTotal;
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Date getDatahora() {
		return datahora;
	}
	public void setDatahora(Date datahora) {
		this.datahora = datahora;
	}
	public Double getValorTotal() {
		return valorTotal;
	}
	public void setValorTotal(Double valorTotal) {
		this.valorTotal = valorTotal;
	}
}

