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
public class ProntuarioAnamnese implements Serializable{

	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long pra_codigo;
	
	@ManyToOne
	@JoinColumn(name="anm_codigo")
	private Anamnese anamnese;
	
	@ManyToOne
	@JoinColumn(name="pro_codigo")
	private Prontuario prontuario;
	
	@Column
	private String pra_resp1;
	
	@Column
	private String pra_resp2;

	public Long getPra_codigo() {
		return pra_codigo;
	}

	public void setPra_codigo(Long pra_codigo) {
		this.pra_codigo = pra_codigo;
	}

	public Anamnese getAnamnese() {
		return anamnese;
	}

	public void setAnamnese(Anamnese anamnese) {
		this.anamnese = anamnese;
	}

	public Prontuario getProntuario() {
		return prontuario;
	}

	public void setProntuario(Prontuario prontuario) {
		this.prontuario = prontuario;
	}

	public String getPra_resp1() {
		return pra_resp1;
	}

	public void setPra_resp1(String pra_resp1) {
		this.pra_resp1 = pra_resp1;
	}

	public String getPra_resp2() {
		return pra_resp2;
	}

	public void setPra_resp2(String pra_resp2) {
		this.pra_resp2 = pra_resp2;
	}

	@Override
	public String toString() {
		return "ProntuarioAnamnese [pra_codigo=" + pra_codigo + ", anamnese=" + anamnese + ", prontuario=" + prontuario
				+ ", pra_resp1=" + pra_resp1 + ", pra_resp2=" + pra_resp2 + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((pra_codigo == null) ? 0 : pra_codigo.hashCode());
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
		ProntuarioAnamnese other = (ProntuarioAnamnese) obj;
		if (pra_codigo == null) {
			if (other.pra_codigo != null)
				return false;
		} else if (!pra_codigo.equals(other.pra_codigo))
			return false;
		return true;
	}
	
}
