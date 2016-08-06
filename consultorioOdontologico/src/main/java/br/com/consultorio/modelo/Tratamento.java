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
	private Character tra_status = 'A';
	
	@Column
	@Min(value=0,message = "O Valor deve ser no mínimo 0.0, não podendo ser valor negativo")
	private BigDecimal tra_valor;
	
	@Column
	private String tra_denteRegiao;
	
	@ManyToOne
	@JoinColumn(name="plp_codigo")
	private PlanoPai planoPai;// = new PlanoPai();
	
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
	
	public Character getTra_status() {
		return tra_status;
	}

	public void setTra_status(Character tra_status) {
		this.tra_status = tra_status;
	}

	public BigDecimal getTra_valor() {
		return tra_valor;
	}

	public void setTra_valor(BigDecimal tra_valor) {
		this.tra_valor = tra_valor;
	}

	public String getTra_denteRegiao() {
		return tra_denteRegiao;
	}

	public void setTra_denteRegiao(String tra_denteRegiao) {
		this.tra_denteRegiao = tra_denteRegiao;
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
				+ ", tra_denteRegiao=" + tra_denteRegiao + ", planoPai=" + planoPai + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((planoPai == null) ? 0 : planoPai.hashCode());
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
		if (planoPai == null) {
			if (other.planoPai != null)
				return false;
		} else if (!planoPai.equals(other.planoPai))
			return false;
		return true;
	}
	
}
