package br.com.consultorio.modelo;

import java.io.Serializable;
import java.math.BigDecimal;
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
public class DestinoProdutoSaida implements Serializable{

	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long des_codigo;
	
	@Column
	private String des_descricao;
	
	@Column
	private String des_status = "A";// A - Aberto , F - Fechado

	public Long getDes_codigo() {
		return des_codigo;
	}

	public void setDes_codigo(Long des_codigo) {
		this.des_codigo = des_codigo;
	}

	public String getDes_descricao() {
		return des_descricao;
	}

	public void setDes_descricao(String des_descricao) {
		this.des_descricao = des_descricao;
	}

	public String getDes_status() {
		return des_status;
	}

	public void setDes_status(String des_status) {
		this.des_status = des_status;
	}

	@Override
	public String toString() {
		return "DestinoProdutoSaida [des_codigo=" + des_codigo + ", des_descricao=" + des_descricao + ", des_status="
				+ des_status + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((des_codigo == null) ? 0 : des_codigo.hashCode());
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
		DestinoProdutoSaida other = (DestinoProdutoSaida) obj;
		if (des_codigo == null) {
			if (other.des_codigo != null)
				return false;
		} else if (!des_codigo.equals(other.des_codigo))
			return false;
		return true;
	}
	
}
