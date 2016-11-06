package br.com.consultorio.modelo;

import java.io.Serializable;
import java.util.Calendar;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class CampanhaPaciente implements Serializable{

	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long cap_codigo;
	
	@ManyToOne
	@JoinColumn(name="cae_codigo")
	private Campanha campanha;
	
	@ManyToOne
	@JoinColumn(name="pac_codigo")
	private Paciente paciente;
	
	public CampanhaPaciente() {
	
	}

	public Long getCap_codigo() {
		return cap_codigo;
	}

	public void setCap_codigo(Long cap_codigo) {
		this.cap_codigo = cap_codigo;
	}

	public Campanha getCampanha() {
		return campanha;
	}

	public void setCampanha(Campanha campanha) {
		this.campanha = campanha;
	}

	public Paciente getPaciente() {
		return paciente;
	}

	public void setPaciente(Paciente paciente) {
		this.paciente = paciente;
	}

	@Override
	public String toString() {
		return "CampanhaPaciente [cap_codigo=" + cap_codigo + ", campanha=" + campanha + ", paciente=" + paciente + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((cap_codigo == null) ? 0 : cap_codigo.hashCode());
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
		CampanhaPaciente other = (CampanhaPaciente) obj;
		if (cap_codigo == null) {
			if (other.cap_codigo != null)
				return false;
		} else if (!cap_codigo.equals(other.cap_codigo))
			return false;
		return true;
	}

	
}
