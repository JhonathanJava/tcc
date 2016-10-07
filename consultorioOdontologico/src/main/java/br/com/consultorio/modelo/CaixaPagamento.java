package br.com.consultorio.modelo;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
public class CaixaPagamento implements Serializable{

	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long cap_codigo;
	
	@Column
	private BigDecimal cap_valor = BigDecimal.ZERO;
	
	@Column
	private BigDecimal cap_juro = BigDecimal.ZERO;
	
	@Column
	private BigDecimal cap_desconto = BigDecimal.ZERO;
	
	@ManyToOne
	@JoinColumn(name="tit_codigo")
	private Titulo titulo;
	
	@Column
	@Temporal(TemporalType.TIMESTAMP)
	private Date cap_data = new Date();
	
	@ManyToOne
	@JoinColumn(name="fop_codigo")
	private FormaPagamento formaPagamento;

	public Long getCap_codigo() {
		return cap_codigo;
	}

	public void setCap_codigo(Long cap_codigo) {
		this.cap_codigo = cap_codigo;
	}

	public BigDecimal getCap_valor() {
		return cap_valor;
	}

	public void setCap_valor(BigDecimal cap_valor) {
		this.cap_valor = cap_valor;
	}

	public Titulo getTitulo() {
		return titulo;
	}

	public void setTitulo(Titulo titulo) {
		this.titulo = titulo;
	}

	public Date getCap_data() {
		return cap_data;
	}

	public void setCap_data(Date cap_data) {
		this.cap_data = cap_data;
	}

	public FormaPagamento getFormaPagamento() {
		return formaPagamento;
	}

	public void setFormaPagamento(FormaPagamento formaPagamento) {
		this.formaPagamento = formaPagamento;
	}

	public BigDecimal getCap_juro() {
		return cap_juro;
	}

	public void setCap_juro(BigDecimal cap_juro) {
		this.cap_juro = cap_juro;
	}

	public BigDecimal getCap_desconto() {
		return cap_desconto;
	}

	public void setCap_desconto(BigDecimal cap_desconto) {
		this.cap_desconto = cap_desconto;
	}

	@Override
	public String toString() {
		return "CaixaPagamento [cap_codigo=" + cap_codigo + ", cap_valor=" + cap_valor + ", titulo=" + titulo
				+ ", cap_data=" + cap_data + ", formaPagamento=" + formaPagamento + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((cap_codigo == null) ? 0 : cap_codigo.hashCode());
		result = prime * result + ((cap_valor == null) ? 0 : cap_valor.hashCode());
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
		CaixaPagamento other = (CaixaPagamento) obj;
		if (cap_codigo == null) {
			if (other.cap_codigo != null)
				return false;
		} else if (!cap_codigo.equals(other.cap_codigo))
			return false;
		if (cap_valor == null) {
			if (other.cap_valor != null)
				return false;
		} else if (!cap_valor.equals(other.cap_valor))
			return false;
		return true;
	}
}
