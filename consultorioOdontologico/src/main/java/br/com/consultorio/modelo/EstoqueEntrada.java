package br.com.consultorio.modelo;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class EstoqueEntrada implements Serializable{

	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long ete_codigo;
	
	@Column
	private Integer ete_quantidade;
	
	@JoinColumn(name = "est_codigo")
	@ManyToOne
	private Estoque estoque = new Estoque();
	
	@JoinColumn(name = "com_codigo")
	@ManyToOne
	private Compra compra = new Compra();
	
	@Column
	private BigDecimal ete_valor; // valor de Custo

	@Column
	private BigDecimal ete_valorVenda;
	
	public EstoqueEntrada() {
		
	}

	public Long getEte_codigo() {
		return ete_codigo;
	}

	public void setEte_codigo(Long ete_codigo) {
		this.ete_codigo = ete_codigo;
	}

	public Integer getEte_quantidade() {
		return ete_quantidade;
	}

	public void setEte_quantidade(Integer ete_quantidade) {
		this.ete_quantidade = ete_quantidade;
	}

	public Estoque getEstoque() {
		return estoque;
	}

	public void setEstoque(Estoque estoque) {
		this.estoque = estoque;
	}
	
	public Compra getCompra() {
		return compra;
	}
	
	public void setCompra(Compra compra) {
		this.compra = compra;
	}
	
	public BigDecimal getEte_valor() {
		return ete_valor;
	}
	
	public void setEte_valor(BigDecimal ete_valor) {
		this.ete_valor = ete_valor;
	}
	
	public BigDecimal getEte_valorVenda() {
		return ete_valorVenda;
	}
	
	public void setEte_valorVenda(BigDecimal ete_valorVenda) {
		this.ete_valorVenda = ete_valorVenda;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((ete_codigo == null) ? 0 : ete_codigo.hashCode());
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
		EstoqueEntrada other = (EstoqueEntrada) obj;
		if (ete_codigo == null) {
			if (other.ete_codigo != null)
				return false;
		} else if (!ete_codigo.equals(other.ete_codigo))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "EstoqueEntrada [ete_codigo=" + ete_codigo + ", ete_quantidade=" + ete_quantidade + ", estoque="
				+ estoque + ", compra=" + compra + ", ete_valor=" + ete_valor + ", ete_valorVenda=" + ete_valorVenda
				+ "]";
	}

	

}
