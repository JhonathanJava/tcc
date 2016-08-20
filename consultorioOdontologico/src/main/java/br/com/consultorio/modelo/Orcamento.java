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

@Entity
public class Orcamento implements Serializable{

	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long orc_codigo;
	
	@Column
	private String orc_descricao;
	
	@Column
	private Date orc_data = new Date();
	
	@Column 
	private String orc_tipoPagamento;
	
	@Column
	private String orc_observacao;
	
	@Column
	private String orc_status;
	
	@Column
	private BigDecimal orc_desconto;
	
	@Column 
	private BigDecimal orc_total;
	
	@ManyToOne
	@JoinColumn(name="cax_codigo")
	private Caixa caixa;
	
	@ManyToOne
	@JoinColumn(name="usu_codigo")
	private Usuario usuario; // Profissional do Atendimento

	public Long getOrc_codigo() {
		return orc_codigo;
	}

	public void setOrc_codigo(Long orc_codigo) {
		this.orc_codigo = orc_codigo;
	}

	public String getOrc_descricao() {
		return orc_descricao;
	}

	public void setOrc_descricao(String orc_descricao) {
		this.orc_descricao = orc_descricao;
	}

	public Date getOrc_data() {
		return orc_data;
	}

	public void setOrc_data(Date orc_data) {
		this.orc_data = orc_data;
	}

	public String getOrc_tipoPagamento() {
		return orc_tipoPagamento;
	}

	public void setOrc_tipoPagamento(String orc_tipoPagamento) {
		this.orc_tipoPagamento = orc_tipoPagamento;
	}

	public String getOrc_observacao() {
		return orc_observacao;
	}

	public void setOrc_observacao(String orc_observacao) {
		this.orc_observacao = orc_observacao;
	}

	public String getOrc_status() {
		return orc_status;
	}

	public void setOrc_status(String orc_status) {
		this.orc_status = orc_status;
	}

	public BigDecimal getOrc_desconto() {
		return orc_desconto;
	}

	public void setOrc_desconto(BigDecimal orc_desconto) {
		this.orc_desconto = orc_desconto;
	}

	public BigDecimal getOrc_total() {
		return orc_total;
	}

	public void setOrc_total(BigDecimal orc_total) {
		this.orc_total = orc_total;
	}
	
	public Caixa getCaixa() {
		return caixa;
	}
	
	public void setCaixa(Caixa caixa) {
		this.caixa = caixa;
	}

	public Usuario getUsuario() {
		return usuario;
	}
	
	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}
	
	@Override
	public String toString() {
		return "Orcamento [orc_codigo=" + orc_codigo + ", orc_descricao=" + orc_descricao + ", orc_data=" + orc_data
				+ ", orc_tipoPagamento=" + orc_tipoPagamento + ", orc_observacao=" + orc_observacao + ", orc_status="
				+ orc_status + ", orc_desconto=" + orc_desconto + ", orc_total=" + orc_total + "]";
	}
	
}
