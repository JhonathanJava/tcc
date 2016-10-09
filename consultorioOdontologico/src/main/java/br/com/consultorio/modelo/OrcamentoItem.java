package br.com.consultorio.modelo;

import java.io.Serializable;

import javax.persistence.Column;
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
	
	@Column
	private String ori_denteRegiao;
	
	@Column
	private Integer ori_quantidade = 0;
	
	@ManyToOne
	@JoinColumn(name="orc_codigo")
	private Orcamento orcamento;
	
	@ManyToOne
	@JoinColumn(name="tra_codigo")
	private Tratamento tratamento;
	
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

	public String getOri_denteRegiao() {
		return ori_denteRegiao;
	}
	
	public void setOri_denteRegiao(String ori_denteRegiao) {
		this.ori_denteRegiao = ori_denteRegiao;
	}
	
	public Integer getOri_quantidade() {
		return ori_quantidade;
	}	
	
	public void setOri_quantidade(Integer ori_quantidade) {
		this.ori_quantidade = ori_quantidade;
	}
	
	@Override
	public String toString() {
		return "OrcamentoItem [ori_codigo=" + ori_codigo + ", orcamento=" + orcamento + ", tratamento=" + tratamento+ ", planoPai= ]";
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
