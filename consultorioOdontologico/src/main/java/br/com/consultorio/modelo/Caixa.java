package br.com.consultorio.modelo;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Calendar;

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
public class Caixa implements Serializable{

	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long cai_codigo;
	
	@Column
	private String cai_descricao;
	
	@Column
	private String cai_status = "A";// A - Aberto , F - Fechado
	
	@Column
	private BigDecimal cai_valorAbertura;
	
	@Column
	private BigDecimal cai_valorFechamento;
	
	@Column
	@Temporal(TemporalType.TIMESTAMP)
	private Calendar cai_dtAbertura = Calendar.getInstance();
	
	@Column(nullable = true)
	@Temporal(TemporalType.TIMESTAMP)
	private Calendar cai_dtFechamento;
	
	@ManyToOne
	@JoinColumn(name="usu_codigo")
	private Usuario usuario;

	public Caixa() {
	
	}

	public Long getCai_codigo() {
		return cai_codigo;
	}

	public void setCai_codigo(Long cai_codigo) {
		this.cai_codigo = cai_codigo;
	}

	public String getCai_descricao() {
		return cai_descricao;
	}

	public void setCai_descricao(String cai_descricao) {
		this.cai_descricao = cai_descricao;
	}

	public String getCai_status() {
		return cai_status;
	}

	public void setCai_status(String cai_status) {
		this.cai_status = cai_status;
	}

	public Calendar getCai_dtAbertura() {
		return cai_dtAbertura;
	}

	public void setCai_dtAbertura(Calendar cai_dtAbertura) {
		this.cai_dtAbertura = cai_dtAbertura;
	}

	public Calendar getCai_dtFechamento() {
		return cai_dtFechamento;
	}

	public void setCai_dtFechamento(Calendar cai_dtFechamento) {
		this.cai_dtFechamento = cai_dtFechamento;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public BigDecimal getCai_valorAbertura() {
		return cai_valorAbertura;
	}
	
	public void setCai_valorAbertura(BigDecimal cai_valorAbertura) {
		this.cai_valorAbertura = cai_valorAbertura;
	}
	
	public BigDecimal getCai_valorFechamento() {
		return cai_valorFechamento;
	}
	
	public void setCai_valorFechamento(BigDecimal cai_valorFechamento) {
		this.cai_valorFechamento = cai_valorFechamento;
	}
	
	@Override
	public String toString() {
		return "Caixa [cai_codigo=" + cai_codigo + ", cai_descricao=" + cai_descricao + ", cai_status=" + cai_status
				+ ", cai_dtAbertura=" + cai_dtAbertura + ", cai_dtFechamento=" + cai_dtFechamento + ", usuario="
				+ usuario + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((cai_codigo == null) ? 0 : cai_codigo.hashCode());
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
		Caixa other = (Caixa) obj;
		if (cai_codigo == null) {
			if (other.cai_codigo != null)
				return false;
		} else if (!cai_codigo.equals(other.cai_codigo))
			return false;
		return true;
	}
	
}
