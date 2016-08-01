package br.com.consultorio.modelo;

import java.io.Serializable;
import java.util.Calendar;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
public class Perfil implements Serializable{

	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long per_codigo;
	
	@Column
	private String per_descricao;
	
	@Column
	private String per_status;
	
	@Column
	@Temporal(TemporalType.DATE)
	private Calendar per_dataCadastro = Calendar.getInstance();
	
	public Perfil() {
		
	}

	public Long getPer_codigo() {
		return per_codigo;
	}

	public void setPer_codigo(Long per_codigo) {
		this.per_codigo = per_codigo;
	}

	public String getPer_descricao() {
		return per_descricao;
	}

	public void setPer_descricao(String per_descricao) {
		this.per_descricao = per_descricao;
	}

	public String getPer_status() {
		return per_status;
	}

	public void setPer_status(String per_status) {
		this.per_status = per_status;
	}

	public Calendar getPer_dataCadastro() {
		return per_dataCadastro;
	}

	public void setPer_dataCadastro(Calendar per_dataCadastro) {
		this.per_dataCadastro = per_dataCadastro;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((per_codigo == null) ? 0 : per_codigo.hashCode());
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
		Perfil other = (Perfil) obj;
		if (per_codigo == null) {
			if (other.per_codigo != null)
				return false;
		} else if (!per_codigo.equals(other.per_codigo))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Perfil [per_codigo=" + per_codigo + ", per_descricao=" + per_descricao + ", per_status=" + per_status
				+ ", per_dataCadastro=" + per_dataCadastro + "]";
	}
	
	
	
}
