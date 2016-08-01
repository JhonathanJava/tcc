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
public class Programa implements Serializable{

	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long pro_codigo;
	
	@Column
	@Temporal(TemporalType.DATE)
	private Calendar pro_dataCadastro = Calendar.getInstance();
	
	@Column
	private String pro_tela;
	
	@Column
	private String pro_status;
	
	@Column
	private String pro_descricao;
	
	public Programa() {
	}

	public Long getPro_codigo() {
		return pro_codigo;
	}

	public void setPro_codigo(Long pro_codigo) {
		this.pro_codigo = pro_codigo;
	}

	public Calendar getPro_dataCadastro() {
		return pro_dataCadastro;
	}
	
	public void setPro_dataCadastro(Calendar pro_dataCadastro) {
		this.pro_dataCadastro = pro_dataCadastro;
	}
	
	public String getPro_tela() {
		return pro_tela;
	}

	public void setPro_tela(String pro_tela) {
		this.pro_tela = pro_tela;
	}

	public String getPro_status() {
		return pro_status;
	}

	public void setPro_status(String pro_status) {
		this.pro_status = pro_status;
	}

	public String getPro_descricao() {
		return pro_descricao;
	}
	
	public void setPro_descricao(String pro_descricao) {
		this.pro_descricao = pro_descricao;
	}
	
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((pro_codigo == null) ? 0 : pro_codigo.hashCode());
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
		Programa other = (Programa) obj;
		if (pro_codigo == null) {
			if (other.pro_codigo != null)
				return false;
		} else if (!pro_codigo.equals(other.pro_codigo))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Programa [pro_codigo=" + pro_codigo + ", pro_dataCadastro=" + pro_dataCadastro + ", pro_tela="
				+ pro_tela + ", pro_status=" + pro_status + ", pro_descricao=" + pro_descricao + "]";
	}

}
