package br.com.consultorio.modelo;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
public class PlanoPai implements Serializable{

	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long plp_codigo;
	
	@Column
	private String plp_descricao;
	
	@Column
	private String plp_status;
	
	@Column
	private BigDecimal pla_desconto;
	
	@Column
	private String plp_tipoDesconto;
	
	@Column
	private Date plp_dataCadastro = new Date();
	
	public PlanoPai() {
		
	}

	public Long getPlp_codigo() {
		return plp_codigo;
	}

	public void setPlp_codigo(Long plp_codigo) {
		this.plp_codigo = plp_codigo;
	}

	public String getPlp_descricao() {
		return plp_descricao;
	}

	public void setPlp_descricao(String plp_descricao) {
		this.plp_descricao = plp_descricao;
	}

	public String getPlp_status() {
		return plp_status;
	}

	public void setPlp_status(String plp_status) {
		this.plp_status = plp_status;
	}

	public Date getPlp_dataCadastro() {
		return plp_dataCadastro;
	}

	public void setPlp_dataCadastro(Date plp_dataCadastro) {
		this.plp_dataCadastro = plp_dataCadastro;
	}

	public BigDecimal getPla_desconto() {
		return pla_desconto;
	}
	
	public void setPla_desconto(BigDecimal pla_desconto) {
		this.pla_desconto = pla_desconto;
	}
	
	public String getPlp_tipoDesconto() {
		return plp_tipoDesconto;
	}
	
	public void setPlp_tipoDesconto(String plp_tipoDesconto) {
		this.plp_tipoDesconto = plp_tipoDesconto;
	}
	
	@Override
	public String toString() {
		return "PlanoPai [plp_codigo=" + plp_codigo + ", plp_descricao=" + plp_descricao + ", plp_status=" + plp_status
				+ ", pla_desconto=" + pla_desconto + ", plp_dataCadastro=" + plp_dataCadastro + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((plp_codigo == null) ? 0 : plp_codigo.hashCode());
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
		PlanoPai other = (PlanoPai) obj;
		if (plp_codigo == null) {
			if (other.plp_codigo != null)
				return false;
		} else if (!plp_codigo.equals(other.plp_codigo))
			return false;
		return true;
	}

	
}
