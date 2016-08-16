package br.com.consultorio.modelo;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class OrcamentoItem implements Serializable{

	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long ori_codigo;
	
	@ManyToOne
	@JoinColumn(name="orc_codigo")
	private Orcamento orcamento;
	
	@ManyToOne
	@JoinColumn(name="tra_codigo")
	private Tratamento tratamento;
	
	@ManyToOne
	@JoinColumn(name="plp_codigo")
	private PlanoPai planoPai;

	public Long getOri_codigo() {
		return ori_codigo;
	}

	public void setOri_codigo(Long ori_codigo) {
		this.ori_codigo = ori_codigo;
	}

	public Orcamento getOrcamento() {
		return orcamento;
	}

	public void setOrcamento(Orcamento orcamento) {
		this.orcamento = orcamento;
	}

	public Tratamento getTratamento() {
		return tratamento;
	}

	public void setTratamento(Tratamento tratamento) {
		this.tratamento = tratamento;
	}

	public PlanoPai getPlanoPai() {
		return planoPai;
	}

	public void setPlanoPai(PlanoPai planoPai) {
		this.planoPai = planoPai;
	}

	@Override
	public String toString() {
		return "OrcamentoItem [ori_codigo=" + ori_codigo + ", orcamento=" + orcamento + ", tratamento=" + tratamento
				+ ", planoPai=" + planoPai + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((ori_codigo == null) ? 0 : ori_codigo.hashCode());
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
		OrcamentoItem other = (OrcamentoItem) obj;
		if (ori_codigo == null) {
			if (other.ori_codigo != null)
				return false;
		} else if (!ori_codigo.equals(other.ori_codigo))
			return false;
		return true;
	}
	
}
