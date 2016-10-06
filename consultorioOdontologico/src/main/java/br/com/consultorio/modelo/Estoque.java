package br.com.consultorio.modelo;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Estoque implements Serializable{

	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long est_codigo;
	
	@Column
	private String est_produto;
	
	private Integer est_quantidade = 0;
	
	@Column
	private Integer est_quantidadeMinima = 0;
	
	@Column
	private Date est_data = new Date();
	
	@Column
	private String est_status = "A";
	
	@Column
	private Date est_dataInativado;
	
	public Estoque() {
	}

	public Long getEst_codigo() {
		return est_codigo;
	}

	public void setEst_codigo(Long est_codigo) {
		this.est_codigo = est_codigo;
	}

	public String getEst_produto() {
		return est_produto;
	}

	public void setEst_produto(String est_produto) {
		this.est_produto = est_produto;
	}

	public Integer getEst_quantidadeMinima() {
		return est_quantidadeMinima;
	}

	public void setEst_quantidadeMinima(Integer est_quantidadeMinima) {
		this.est_quantidadeMinima = est_quantidadeMinima;
	}

	public Date getEst_data() {
		return est_data;
	}

	public void setEst_data(Date est_data) {
		this.est_data = est_data;
	}
	
	public String getEst_status() {
		return est_status;
	}
	
	public void setEst_status(String est_status) {
		this.est_status = est_status;
	}
	
	public Integer getEst_quantidade() {
		return est_quantidade;
	}
	
	public void setEst_quantidade(Integer est_quantidade) {
		this.est_quantidade = est_quantidade;
	}
	
	public Date getEst_dataInativado() {
		return est_dataInativado;
	}
	
	public void setEst_dataInativado(Date est_dataInativado) {
		this.est_dataInativado = est_dataInativado;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((est_codigo == null) ? 0 : est_codigo.hashCode());
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
		Estoque other = (Estoque) obj;
		if (est_codigo == null) {
			if (other.est_codigo != null)
				return false;
		} else if (!est_codigo.equals(other.est_codigo))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Estoque [est_codigo=" + est_codigo + ", est_produto=" + est_produto + ", est_quantidadeMinima="
				+ est_quantidadeMinima + ", est_data=" + est_data + ", est_status=" + est_status + "]";
	}
	
}
