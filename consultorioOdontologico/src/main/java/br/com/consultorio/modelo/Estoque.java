package br.com.consultorio.modelo;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.hibernate.annotations.Formula;

@Entity
public class Estoque implements Serializable{

	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long est_codigo;
	
	@Column
	private String est_produto;
	
	@Column
	private Integer est_quantidade;
	
	@Column
	private Integer est_quantidadeMinima;
	
	@Column
	private Date est_data = new Date();
	
	@Column
	private BigDecimal est_valor; // valor de Custo

	@Column
	private BigDecimal est_valorVenda;
	
	private Integer quantidadeAtualizada;
	
	private Integer quantidadeAtual;
	
	private Integer quantidadeNova;

	public Estoque() {
	}

	public Long getEst_codigo() {
		return est_codigo;
	}

	public void setEst_codigo(Long est_codigo) {
		this.est_codigo = est_codigo;
	}

	public String getEst_produto() {
		return est_produto;
	}

	public void setEst_produto(String est_produto) {
		this.est_produto = est_produto;
	}

	public Integer getEst_quantidade() {
		return est_quantidade;
	}

	public void setEst_quantidade(Integer est_quantidade) {
		this.est_quantidade = est_quantidade;
	}

	public Integer getEst_quantidadeMinima() {
		return est_quantidadeMinima;
	}

	public void setEst_quantidadeMinima(Integer est_quantidadeMinima) {
		this.est_quantidadeMinima = est_quantidadeMinima;
	}

	public Date getEst_data() {
		return est_data;
	}

	public void setEst_data(Date est_data) {
		this.est_data = est_data;
	}
	
	public Integer getQuantidadeAtual() {
		return quantidadeAtual;
	}
	
	public void setQuantidadeAtual(Integer quantidadeAtual) {
		this.quantidadeAtual = quantidadeAtual;
	}
	
	public Integer getQuantidadeAtualizada() {
		return quantidadeAtualizada;
	}
	
	public void setQuantidadeAtualizada(Integer quantidadeAtualizada) {
		this.quantidadeAtualizada = quantidadeAtualizada;
	}
	
	public Integer getQuantidadeNova() {
		return quantidadeNova;
	}
	
	public void setQuantidadeNova(Integer quantidadeNova) {
		this.quantidadeNova = quantidadeNova;
	}
	
	public BigDecimal getEst_valor() {
		return est_valor;
	}


	public void setEst_valor(BigDecimal est_valor) {
		this.est_valor = est_valor;
	}
	
	public BigDecimal getEst_valorVenda() {
		return est_valorVenda;
	}
	
	public void setEst_valorVenda(BigDecimal est_valorVenda) {
		this.est_valorVenda = est_valorVenda;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((est_codigo == null) ? 0 : est_codigo.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Estoque other = (Estoque) obj;
		if (est_codigo == null) {
			if (other.est_codigo != null)
				return false;
		} else if (!est_codigo.equals(other.est_codigo))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Estoque [est_codigo=" + est_codigo + ", est_produto=" + est_produto + ", est_quantidade="
				+ est_quantidade + ", est_quantidadeMinima=" + est_quantidadeMinima + ", est_data=" + est_data + "]";
	}
	
}
