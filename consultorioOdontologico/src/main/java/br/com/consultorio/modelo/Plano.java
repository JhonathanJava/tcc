package br.com.consultorio.modelo;

import java.io.Serializable;
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
public class Plano implements Serializable{

	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long pla_codigo;
	
	@Column
	private String pla_status;
	
	@Column
	@Temporal(TemporalType.DATE)
	private Calendar pla_dataCadastro = Calendar.getInstance();
	
	@JoinColumn(name = "plp_codigo")
	@ManyToOne
	private PlanoPai planoPai;
	
	@Column
	private String pla_responsavel;

	@Column
	private String pla_cpfResponsavel;
	
	@Column
	private Integer pla_numeroCartera;
	
	public Plano() {
		
	}

	public Long getPla_codigo() {
		return pla_codigo;
	}

	public void setPla_codigo(Long pla_codigo) {
		this.pla_codigo = pla_codigo;
	}

	public String getPla_status() {
		return pla_status;
	}

	public void setPla_status(String pla_status) {
		this.pla_status = pla_status;
	}

	public Calendar getPla_dataCadastro() {
		return pla_dataCadastro;
	}

	public void setPla_dataCadastro(Calendar pla_dataCadastro) {
		this.pla_dataCadastro = pla_dataCadastro;
	}

	public PlanoPai getPlanoPai() {
		return planoPai;
	}
	
	public void setPlanoPai(PlanoPai planoPai) {
		this.planoPai = planoPai;
	}

	public String getPla_responsavel() {
		return pla_responsavel;
	}

	public void setPla_responsavel(String pla_responsavel) {
		this.pla_responsavel = pla_responsavel;
	}

	public String getPla_cpfResponsavel() {
		return pla_cpfResponsavel;
	}

	public void setPla_cpfResponsavel(String pla_cpfResponsavel) {
		this.pla_cpfResponsavel = pla_cpfResponsavel;
	}

	public Integer getPla_numeroCartera() {
		return pla_numeroCartera;
	}

	public void setPla_numeroCartera(Integer pla_numeroCartera) {
		this.pla_numeroCartera = pla_numeroCartera;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((pla_codigo == null) ? 0 : pla_codigo.hashCode());
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
		Plano other = (Plano) obj;
		if (pla_codigo == null) {
			if (other.pla_codigo != null)
				return false;
		} else if (!pla_codigo.equals(other.pla_codigo))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Plano [pla_codigo=" + pla_codigo + ", pla_status=" + pla_status + ", pla_dataCadastro="
				+ pla_dataCadastro + ", planoPai=" + planoPai + ", pla_responsavel=" + pla_responsavel
				+ ", pla_cpfResponsavel=" + pla_cpfResponsavel + ", pla_numeroCartera=" + pla_numeroCartera + "]";
	}

	
}
