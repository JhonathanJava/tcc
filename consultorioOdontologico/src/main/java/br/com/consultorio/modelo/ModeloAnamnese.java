package br.com.consultorio.modelo;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class ModeloAnamnese implements Serializable{

	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long moa_codigo;
	
	@Column
	private String moa_descricao;
	
	@Column
	private String moa_status = "A";

	@Column
	private Date moa_dataInativacao;
	
	@Column 
	private Date moa_data = new Date();
	
	public Long getMoa_codigo() {
		return moa_codigo;
	}

	public void setMoa_codigo(Long moa_codigo) {
		this.moa_codigo = moa_codigo;
	}

	public String getMoa_descricao() {
		return moa_descricao;
	}

	public void setMoa_descricao(String moa_descricao) {
		this.moa_descricao = moa_descricao;
	}

	public String getMoa_status() {
		return moa_status;
	}

	public void setMoa_status(String moa_status) {
		this.moa_status = moa_status;
	}

	public Date getMoa_dataInativacao() {
		return moa_dataInativacao;
	}

	public void setMoa_dataInativacao(Date moa_dataInativacao) {
		this.moa_dataInativacao = moa_dataInativacao;
	}

	public Date getMoa_data() {
		return moa_data;
	}

	public void setMoa_data(Date moa_data) {
		this.moa_data = moa_data;
	}

	@Override
	public String toString() {
		return "ModeloAnamnese [moa_codigo=" + moa_codigo + ", moa_descricao=" + moa_descricao + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((moa_codigo == null) ? 0 : moa_codigo.hashCode());
		result = prime * result + ((moa_descricao == null) ? 0 : moa_descricao.hashCode());
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
		ModeloAnamnese other = (ModeloAnamnese) obj;
		if (moa_codigo == null) {
			if (other.moa_codigo != null)
				return false;
		} else if (!moa_codigo.equals(other.moa_codigo))
			return false;
		if (moa_descricao == null) {
			if (other.moa_descricao != null)
				return false;
		} else if (!moa_descricao.equals(other.moa_descricao))
			return false;
		return true;
	}
	
}
