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
public class ProntuarioTratamento implements Serializable{

	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long prt_codigo;
	
	@ManyToOne
	@JoinColumn(name="tra_codigo")
	private Tratamento tratamento;
	
	@ManyToOne
	@JoinColumn(name="pro_codigo")
	private Prontuario prontuario;
	
	@ManyToOne
	@JoinColumn(name="plp_codigo")
	private PlanoPai planoPai;
	
	@Column
	private Integer prt_quantidade;
	
	@Column
	private BigDecimal prt_valor;
	
	@Column
	private BigDecimal prt_desconto;
	
	public ProntuarioTratamento() {
		
	}

	public Long getPrt_codigo() {
		return prt_codigo;
	}

	public void setPrt_codigo(Long prt_codigo) {
		this.prt_codigo = prt_codigo;
	}

	public Tratamento getTratamento() {
		return tratamento;
	}

	public void setTratamento(Tratamento tratamento) {
		this.tratamento = tratamento;
	}

	public Prontuario getProntuario() {
		return prontuario;
	}

	public void setProntuario(Prontuario prontuario) {
		this.prontuario = prontuario;
	}

	public PlanoPai getPlanoPai() {
		return planoPai;
	}

	public void setPlanoPai(PlanoPai planoPai) {
		this.planoPai = planoPai;
	}

	public Integer getPrt_quantidade() {
		return prt_quantidade;
	}

	public void setPrt_quantidade(Integer prt_quantidade) {
		this.prt_quantidade = prt_quantidade;
	}

	public BigDecimal getPrt_valor() {
		return prt_valor;
	}

	public void setPrt_valor(BigDecimal prt_valor) {
		this.prt_valor = prt_valor;
	}
	
	public BigDecimal getPrt_desconto() {
		return prt_desconto;
	}
	
	public void setPrt_desconto(BigDecimal prt_desconto) {
		this.prt_desconto = prt_desconto;
	}

	@Override
	public String toString() {
		return "ProntuarioTratamento [prt_codigo=" + prt_codigo + ", tratamento=" + tratamento + ", prontuario="
				+ prontuario + ", planoPai=" + planoPai + ", prt_quantidade=" + prt_quantidade + ", prt_valor="
				+ prt_valor + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((prt_codigo == null) ? 0 : prt_codigo.hashCode());
		result = prime * result + ((tratamento == null) ? 0 : tratamento.hashCode());
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
		ProntuarioTratamento other = (ProntuarioTratamento) obj;
		if (prt_codigo == null) {
			if (other.prt_codigo != null)
				return false;
		} else if (!prt_codigo.equals(other.prt_codigo))
			return false;
		if (tratamento == null) {
			if (other.tratamento != null)
				return false;
		} else if (!tratamento.equals(other.tratamento))
			return false;
		return true;
	}

}
