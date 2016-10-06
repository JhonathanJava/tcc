package br.com.consultorio.modelo;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Calendar;
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
public class Titulo implements Serializable{

	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long tit_codigo;
	
	@Column
	private String tit_status = "A";// A - Ativo , I - Inativo
	
	@Column
	private BigDecimal tit_valor;
	
	@Column
	private Integer tit_parcela;
	
	@Column
	private String tit_pago = "N";
	
	@Column
	private Date tit_vencimento;
	
	@Column
	private Date tit_pagamento;
	
	@Column
	@Temporal(TemporalType.TIMESTAMP)
	private Calendar tit_data = Calendar.getInstance();
	
	@ManyToOne
	@JoinColumn(name="usu_codigo")
	private Usuario usuario;
	
	@ManyToOne
	@JoinColumn(name="for_codigo", nullable = true)
	private Fornecedor fornecedor;
	
	@ManyToOne
	@JoinColumn(name="pac_codigo", nullable = true)
	private Paciente paciente;
	
	@ManyToOne
	@JoinColumn(name="con_codigo", nullable = true)
	private CondicaoPagamento condicaoPagamento;
	
	@ManyToOne
	@JoinColumn(name="com_codigo", nullable = true)
	private Compra compra;
	
	@ManyToOne
	@JoinColumn(name="fop_codigo", nullable = true)
	private FormaPagamento formaPagamento;
	
	@Column
	private String tit_tipo; // C - Credito - D - Debito

	public Long getTit_codigo() {
		return tit_codigo;
	}

	public void setTit_codigo(Long tit_codigo) {
		this.tit_codigo = tit_codigo;
	}

	public String getTit_status() {
		return tit_status;
	}

	public void setTit_status(String tit_status) {
		this.tit_status = tit_status;
	}

	public BigDecimal getTit_valor() {
		return tit_valor;
	}

	public void setTit_valor(BigDecimal tit_valor) {
		this.tit_valor = tit_valor;
	}

	public Integer getTit_parcela() {
		return tit_parcela;
	}

	public void setTit_parcela(Integer tit_parcela) {
		this.tit_parcela = tit_parcela;
	}

	public String getTit_pago() {
		return tit_pago;
	}

	public void setTit_pago(String tit_pago) {
		this.tit_pago = tit_pago;
	}

	public Calendar getTit_data() {
		return tit_data;
	}

	public void setTit_data(Calendar tit_data) {
		this.tit_data = tit_data;
	}
	
	public Compra getCompra() {
		return compra;
	}
	
	public void setCompra(Compra compra) {
		this.compra = compra;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public Fornecedor getFornecedor() {
		return fornecedor;
	}

	public void setFornecedor(Fornecedor fornecedor) {
		this.fornecedor = fornecedor;
	}

	public Paciente getPaciente() {
		return paciente;
	}

	public void setPaciente(Paciente paciente) {
		this.paciente = paciente;
	}

	public String getTit_tipo() {
		return tit_tipo;
	}

	public void setTit_tipo(String tit_tipo) {
		this.tit_tipo = tit_tipo;
	}
	
	public CondicaoPagamento getCondicaoPagamento() {
		return condicaoPagamento;
	}

	public void setCondicaoPagamento(CondicaoPagamento condicaoPagamento) {
		this.condicaoPagamento = condicaoPagamento;
	}

	public FormaPagamento getFormaPagamento() {
		return formaPagamento;
	}

	public void setFormaPagamento(FormaPagamento formaPagamento) {
		this.formaPagamento = formaPagamento;
	}

	public Date getTit_vencimento() {
		return tit_vencimento;
	}

	public void setTit_vencimento(Date tit_vencimento) {
		this.tit_vencimento = tit_vencimento;
	}

	public Date getTit_pagamento() {
		return tit_pagamento;
	}

	public void setTit_pagamento(Date tit_pagamento) {
		this.tit_pagamento = tit_pagamento;
	}

	@Override
	public String toString() {
		return "Titulo [tit_codigo=" + tit_codigo + ", tit_status=" + tit_status + ", tit_valor=" + tit_valor
				+ ", tit_parcela=" + tit_parcela + ", tit_pago=" + tit_pago + ", tit_data=" + tit_data + ", usuario="
				+ usuario + ", fornecedor=" + fornecedor + ", paciente=" + paciente + ", condicaoPagamento="
				+ condicaoPagamento + ", formaPagamento=" + formaPagamento + ", tit_tipo=" + tit_tipo + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((tit_codigo == null) ? 0 : tit_codigo.hashCode());
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
		Titulo other = (Titulo) obj;
		if (tit_codigo == null) {
			if (other.tit_codigo != null)
				return false;
		} else if (!tit_codigo.equals(other.tit_codigo))
			return false;
		return true;
	}

}
