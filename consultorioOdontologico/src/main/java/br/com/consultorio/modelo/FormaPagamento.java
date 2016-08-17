package br.com.consultorio.modelo;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class FormaPagamento implements Serializable{

	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long for_codigo;
	
	@Column
	private String for_descricao;
	
	public Long getFor_codigo() {
		return for_codigo;
	}
	
	public void setFor_codigo(Long for_codigo) {
		this.for_codigo = for_codigo;
	}
	
	public String getFor_descricao() {
		return for_descricao;
	}
	
	public void setFor_descricao(String for_descricao) {
		this.for_descricao = for_descricao;
	}

	@Override
	public String toString() {
		return "FormaPagamento [for_codigo=" + for_codigo + ", for_descricao=" + for_descricao + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((for_codigo == null) ? 0 : for_codigo.hashCode());
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
		FormaPagamento other = (FormaPagamento) obj;
		if (for_codigo == null) {
			if (other.for_codigo != null)
				return false;
		} else if (!for_codigo.equals(other.for_codigo))
			return false;
		return true;
	}
	
}
