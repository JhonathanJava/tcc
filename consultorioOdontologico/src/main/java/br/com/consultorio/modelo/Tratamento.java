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
import javax.validation.constraints.Min;

@Entity
public class Tratamento implements Serializable{

	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long tra_codigo;
	
	@Column
	private String tra_descricao;
	
	@Column
	private String tra_status = "A";
	
	@Column
	@Min(value=0,message = "O Valor deve ser no mínimo 0.0, não podendo ser valor negativo")
	private BigDecimal tra_valor;
	
	@ManyToOne
	@JoinColumn(name="plp_codigo")
	private PlanoPai planoPai = new PlanoPai();
	
	public Tratamento() {
		
	}

	public Long getTra_codigo() {
		return tra_codigo;
	}

	public void setTra_codigo(Long tra_codigo) {
		this.tra_codigo = tra_codigo;
	}

	public String getTra_descricao() {
		return tra_descricao;
	}
	
	public void setTra_descricao(String tra_descricao) {
		this.tra_descricao = tra_descricao;
	}
	
	public String getTra_status() {
		return tra_status;
	}

	public void setTra_status(String tra_status) {
		this.tra_status = tra_status;
	}

	public BigDecimal getTra_valor() {
		return tra_valor;
	}

	public void setTra_valor(BigDecimal tra_valor) {
		this.tra_valor = tra_valor;
	}

	public PlanoPai getPlanoPai() {
		return planoPai;
	}

	public void setPlanoPai(PlanoPai planoPai) {
		this.planoPai = planoPai;
	}

	@Override
	public String toString() {
		return "Tratamento [tra_codigo=" + tra_codigo + ", tra_status=" + tra_status + ", tra_valor=" + tra_valor
				+ ", tra_denteRegiao=" + ", planoPai=" + planoPai + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((tra_codigo == null) ? 0 : tra_codigo.hashCode());
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
		Tratamento other = (Tratamento) obj;
		if (tra_codigo == null) {
			if (other.tra_codigo != null)
				return false;
		} else if (!tra_codigo.equals(other.tra_codigo))
			return false;
		return true;
	}

	
	
}
