package br.com.consultorio.modelo;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class CondicaoPagamento implements Serializable{

	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long con_codigo;
	
	@Column
	private String con_descricao;
	
	@Column
	private String con_status = "A";
	
	@Column 
	private Integer con_numeroParcela = 1;

	public Long getCon_codigo() {
		return con_codigo;
	}

	public void setCon_codigo(Long con_codigo) {
		this.con_codigo = con_codigo;
	}

	public String getCon_descricao() {
		return con_descricao;
	}

	public void setCon_descricao(String con_descricao) {
		this.con_descricao = con_descricao;
	}

	public String getCon_status() {
		return con_status;
	}

	public void setCon_status(String con_status) {
		this.con_status = con_status;
	}

	public Integer getCon_numeroParcela() {
		return con_numeroParcela;
	}

	public void setCon_numeroParcela(Integer con_numeroParcela) {
		this.con_numeroParcela = con_numeroParcela;
	}

	@Override
	public String toString() {
		return "CondicaoPagamento [con_codigo=" + con_codigo + ", con_descricao=" + con_descricao + ", con_status="
				+ con_status + ", con_numeroParcela=" + con_numeroParcela + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((con_codigo == null) ? 0 : con_codigo.hashCode());
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
		CondicaoPagamento other = (CondicaoPagamento) obj;
		if (con_codigo == null) {
			if (other.con_codigo != null)
				return false;
		} else if (!con_codigo.equals(other.con_codigo))
			return false;
		return true;
	}
	
}
