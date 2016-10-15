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
public class Prontuario implements Serializable{

	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long pro_codigo;
	
	@ManyToOne
	@JoinColumn(name="pac_codigo")
	private Paciente pac_codigo;
	
	@Column
	@Temporal(TemporalType.DATE)
	private Calendar pro_data = Calendar.getInstance();
	
	@Column
	private String pro_observacao;
	
	@Column
	private Character pro_status = 'A';
	
	public Prontuario() {
		
	}

	public Long getPro_codigo() {
		return pro_codigo;
	}

	public void setPro_codigo(Long pro_codigo) {
		this.pro_codigo = pro_codigo;
	}

	public Paciente getPac_codigo() {
		return pac_codigo;
	}
	
	public void setPac_codigo(Paciente pac_codigo) {
		this.pac_codigo = pac_codigo;
	}
	
	public Calendar getPro_data() {
		return pro_data;
	}

	public void setPro_data(Calendar pro_data) {
		this.pro_data = pro_data;
	}

	public String getPro_observacao() {
		return pro_observacao;
	}

	public void setPro_observacao(String pro_observacao) {
		this.pro_observacao = pro_observacao;
	}

	public Character getPro_status() {
		return pro_status;
	}

	public void setPro_status(Character pro_status) {
		this.pro_status = pro_status;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((pac_codigo == null) ? 0 : pac_codigo.hashCode());
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
		Prontuario other = (Prontuario) obj;
		if (pac_codigo == null) {
			if (other.pac_codigo != null)
				return false;
		} else if (!pac_codigo.equals(other.pac_codigo))
			return false;
		return true;
	}

}
