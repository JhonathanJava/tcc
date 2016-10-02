package br.com.consultorio.modelo;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class EstoqueSaida implements Serializable{

	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long ets_codigo;
	
	@Column
	private Integer ets_quantidade;
	
	@Column
	private String ets_destinoSaida;
	
	@Column
	private Date ets_data = new Date();
	
	@Column
	private BigDecimal ets_valorVenda;

	@JoinColumn(name = "usu_codigo")
	@ManyToOne
	private Usuario usuario = new Usuario();
	
	@JoinColumn(name = "est_codigo")
	@ManyToOne
	private Estoque estoque = new Estoque();
	
	
	public EstoqueSaida() {
		
	}


	public Long getEts_codigo() {
		return ets_codigo;
	}


	public void setEts_codigo(Long ets_codigo) {
		this.ets_codigo = ets_codigo;
	}


	public Integer getEts_quantidade() {
		return ets_quantidade;
	}


	public void setEts_quantidade(Integer ets_quantidade) {
		this.ets_quantidade = ets_quantidade;
	}


	public Date getEts_data() {
		return ets_data;
	}


	public void setEts_data(Date ets_data) {
		this.ets_data = ets_data;
	}


	public Usuario getUsuario() {
		return usuario;
	}


	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}


	public Estoque getEstoque() {
		return estoque;
	}


	public void setEstoque(Estoque estoque) {
		this.estoque = estoque;
	}

	public String getEts_destinoSaida() {
		return ets_destinoSaida;
	}
	
	public void setEts_destinoSaida(String ets_destinoSaida) {
		this.ets_destinoSaida = ets_destinoSaida;
	}

	public BigDecimal getEts_valorVenda() {
		return ets_valorVenda;
	}
	
	public void setEts_valorVenda(BigDecimal ets_valorVenda) {
		this.ets_valorVenda = ets_valorVenda;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((ets_codigo == null) ? 0 : ets_codigo.hashCode());
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
		EstoqueSaida other = (EstoqueSaida) obj;
		if (ets_codigo == null) {
			if (other.ets_codigo != null)
				return false;
		} else if (!ets_codigo.equals(other.ets_codigo))
			return false;
		return true;
	}


	@Override
	public String toString() {
		return "EstoqueSaida [ets_codigo=" + ets_codigo + ", ets_quantidade=" + ets_quantidade + ", ets_data="
				+ ets_data + ", usuario=" + usuario + ", estoque=" + estoque + "]";
	}

}
